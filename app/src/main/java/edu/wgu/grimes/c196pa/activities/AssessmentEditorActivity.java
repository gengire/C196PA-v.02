package edu.wgu.grimes.c196pa.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.AssessmentEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.ASSESSMENT_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class AssessmentEditorActivity extends AbstractEditorActivity {

    AssessmentEditorViewModel mViewModel;

    @BindView(R.id.edit_text_assessment_editor_title)
    EditText mTitle;

    @BindView(R.id.spinner_assessment_editor_assessment_type)
    Spinner mAssessmentType;

    @BindView(R.id.spinner_assessment_editor_status)
    Spinner mStatus;

    @BindView(R.id.text_view_assessment_editor_completion_date_value)
    TextView mCompletionDate;

    private Date completionDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSpinners();

    }

    private void initSpinners() {
//        TypedArray assessmentTypes = getResources().obtainTypedArray(R.array.assessment_types);
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

    @OnClick(R.id.text_view_assessment_editor_completion_date_value)
    void startDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mCompletionDate, completionDate);
        dateDialog.show(getSupportFragmentManager(), "assessmentCompletionDatePicker");
    }

    @Override
    protected void initRecyclerView() {
        // noop
    }

    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(AssessmentEditorViewModel.class);
        mViewModel.mLiveAssessment.observe(this, (assessment) -> {
            if (assessment != null) {
                if (!mEditing) {
                    mTitle.setText(assessment.getTitle());
                    mAssessmentType.setSelection(((ArrayAdapter) mAssessmentType.getAdapter())
                            .getPosition(String.valueOf(assessment.getType())));
                    mStatus.setSelection(((ArrayAdapter) mStatus.getAdapter())
                            .getPosition(String.valueOf(assessment.getStatus())));
                }
                completionDate = assessment.getCompletionDate();
                if (completionDate != null) {
                    mCompletionDate.setText(getFormattedDate(completionDate));
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
//            mViewModel.loadCourseAssessments(mCourseId);
//            mViewModel.getCourseAssessments().observe(this, (assessments) -> {
//                mAdapter.submitList(assessments);
//            });
        }
    }

    protected void save() {
        String title = mTitle.getText().toString();
        String assessmentType = String.valueOf(mAssessmentType.getSelectedItem());
        String status = String.valueOf(mStatus.getSelectedItem());
        String completionDate = mCompletionDate.getText().toString();

        if (title.trim().isEmpty()) {
            StyleableToast.makeText(AssessmentEditorActivity.this, "Please enter a title", R.style.toast_validation_failure).show();
            return;
        }
        mViewModel.saveAssessment(mParentId, assessmentType, title, status, completionDate);
        StyleableToast.makeText(AssessmentEditorActivity.this, title + " saved", R.style.toast_message).show();
        closeActivity();
    }

    protected void delete() {
        AssessmentEntity course = mViewModel.mLiveAssessment.getValue();
        String title = course.getTitle();
        mViewModel.deleteAssessment();
        String text = title + " Deleted";
        StyleableToast.makeText(AssessmentEditorActivity.this, text, R.style.toast_message).show();
        closeActivity();
    }

    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    @Override
    protected void onSwipeCancel(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return null; // noop
    }
}
