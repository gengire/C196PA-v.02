package edu.wgu.grimes.c196pa.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.utilities.AlarmNotificationManager;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.utilities.HasDate;
import edu.wgu.grimes.c196pa.viewmodels.AssessmentEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.ASSESSMENT_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.SHORT_DATE_PATTERN;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class AssessmentEditorActivity extends AbstractEditorActivity {

    AssessmentEditorViewModel mViewModel;

    @BindView(R.id.edit_text_assessment_editor_title)
    EditText mTitle;

    @BindView(R.id.spinner_assessment_editor_assessment_type)
    Spinner mAssessmentType;

    @BindView(R.id.spinner_assessment_editor_status)
    Spinner mStatus;

    @BindView(R.id.text_view_assessment_editor_end_date_value)
    TextView mCompletionDate;

    @BindView(R.id.text_view_assessment_editor_alarm_end_date_value)
    TextView mCompletionDateAlarm;

    @BindView(R.id.image_view_assessment_end_date_alert)
    ImageView imageViewCompletionDateAlert;


    private Date completionDate;
    private Date completionDateAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSpinners();

    }

    private void initSpinners() {
        String[] assessmentTypes = getResources().getStringArray(R.array.assessment_types);
        String[] assessmentStatuses = getResources().getStringArray(R.array.assessment_values);

        ArrayAdapter<String> assessmentTypeItemAdapter = new ArrayAdapter<String>(
                this, R.layout.item_spinner_right, assessmentTypes);
        assessmentTypeItemAdapter.setDropDownViewResource(R.layout.item_spinner_right);
        mAssessmentType.setAdapter(assessmentTypeItemAdapter);

        ArrayAdapter<String> assessmentStatusItemAdapter = new ArrayAdapter<String>(
                this, R.layout.item_spinner_right, assessmentStatuses);
        assessmentStatusItemAdapter.setDropDownViewResource(R.layout.item_spinner_right);
        mStatus.setAdapter(assessmentStatusItemAdapter);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_assessment_editor;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_assessment_editor;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getSaveMenuItem() {
        return R.id.save_assessment;
    }

    @Override
    protected int getDeleteMenuItem() {
        return R.id.delete_assessment;
    }

    @OnClick(R.id.text_view_assessment_editor_end_date_value)
    void completionDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mCompletionDate, completionDate);
        dateDialog.show(getSupportFragmentManager(), "assessmentCompletionDatePicker");
    }

    @OnClick(R.id.image_view_assessment_end_date_alert)
    void completionDateAlertClickHandler() {
        if ("".equals(mCompletionDate.getText())) {
            String text = "Please select a completion date before adding a completion date alarm";
            Toast.makeText(AssessmentEditorActivity.this, text, Toast.LENGTH_LONG).show();
        } else {
            if (completionDateAlarm == null) {
                DialogFragment dateDialog = new DatePickerFragment(new HasDate() {
                    @Override
                    public Date getDate() {
                        return completionDateAlarm;
                    }

                    @Override
                    public void setDate(Date date) {
                        completionDateAlarm = date;
                        mCompletionDateAlarm.setText(getFormattedDate(SHORT_DATE_PATTERN, date));
                        renderAlarm(completionDateAlarm);
                    }
                }, completionDate);
                dateDialog.show(getSupportFragmentManager(), "assessmentCompletionAlarmDatePicker");
            } else {
                completionDateAlarm = null;
                mCompletionDateAlarm.setText(null);
                renderAlarm(null);
            }
        }
    }

    private void renderAlarm(Date date) {
        ImageView imageView = imageViewCompletionDateAlert;
        int dr = date == null ? R.drawable.ic_add_alert : R.drawable.ic_alarm_active;
        float scaleX = date == null ? 1F : 1.2F;
        float scaleY = date == null ? 1F : 1.1F;

        imageView.setImageResource(dr);
        imageView.setScaleX(scaleX);
        imageView.setScaleY(scaleY);
    }

    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(AssessmentEditorViewModel.class);
        mViewModel.mLiveAssessment.observe(this, (assessment) -> {
            if (assessment != null) {
                mTitle.setText(assessment.getTitle());
                mAssessmentType.setSelection(((ArrayAdapter) mAssessmentType.getAdapter())
                        .getPosition(String.valueOf(assessment.getType())));
                mStatus.setSelection(((ArrayAdapter) mStatus.getAdapter())
                        .getPosition(String.valueOf(assessment.getStatus())));
                completionDate = assessment.getCompletionDate();
                completionDateAlarm = assessment.getCompletionDateAlarm();
                renderAlarm(completionDateAlarm);
                if (completionDate != null) {
                    mCompletionDate.setText(getFormattedDate(completionDate));
                }
                if (completionDateAlarm != null) {
                    mCompletionDateAlarm.setText(getFormattedDate(SHORT_DATE_PATTERN, completionDateAlarm));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        mParentId = extras.getInt(COURSE_ID_KEY);

        if (extras.getInt(ASSESSMENT_ID_KEY) == 0) {
            setTitle("New Assessment");
            mNew = true;
        } else {
            setTitle("Edit Assessment");
            mId = extras.getInt(ASSESSMENT_ID_KEY);
            mViewModel.loadAssessment(mId);
        }
    }

    protected void save() {
        String title = mTitle.getText().toString();
        String assessmentType = String.valueOf(mAssessmentType.getSelectedItem());
        String status = String.valueOf(mStatus.getSelectedItem());
        String completionDate = mCompletionDate.getText().toString();
        Date cdAlarm = completionDateAlarm;

        if (title.trim().isEmpty()) {
            Toast.makeText(AssessmentEditorActivity.this, "Please enter a title", Toast.LENGTH_LONG).show();
            return;
        }
        mViewModel.saveAssessment(mParentId, assessmentType, title, status, completionDate, cdAlarm);
        handleAlarmNotification();
        Toast.makeText(AssessmentEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    private void handleAlarmNotification() {
        AlarmNotificationManager alm = AlarmNotificationManager.getInstance();
        String title = "WGU Scheduler Assessment Alert";
        String message = mTitle.getText() + " is ";

        if (!"".equals(mCompletionDate.getText()) && completionDateAlarm != null) {
            String cEnding = completionDateAlarm == null ? "" :
                    "scheduled to be completed on " + mCompletionDate.getText();
            alm.registerAlarmNotification(this, completionDateAlarm, mId, "end",
                    title, message + cEnding);
        }
    }

    protected void delete() {
        AssessmentEntity course = mViewModel.mLiveAssessment.getValue();
        String title = course.getTitle();
        mViewModel.deleteAssessment();
        String text = title + " Deleted";
        Toast.makeText(AssessmentEditorActivity.this, text, Toast.LENGTH_SHORT).show();
        closeActivity();
    }


}
