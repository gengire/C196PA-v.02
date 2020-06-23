//*********************************************************************************
//  File:             AssessmentEditorActivity.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************

package edu.wgu.grimes.c196pa.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

/**
 * Assessment Editor Activity, responsible for controlling both new and edit modes for assessments
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AssessmentEditorActivity extends AbstractEditorActivity {

    /**
     * Local View Model for the assessment editor
     */
    AssessmentEditorViewModel mViewModel;

    /**
     * Used with the Date Picker Fragment
     */
    private Date completionDate;
    /**
     * Used with the Date Picker Fragment
     */
    private Date completionDateAlarm;
    /**
     * Local internal state for this activity
     */
    private State state = new State();

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

    /**
     * Loads the data from the internal state to the screen
     */
    private void loadState() {
        mTitle.setText(state.title);
        mAssessmentType.setSelection(state.assessmentTypePosition);
        mStatus.setSelection(state.statusPosition);
        mCompletionDate.setText(state.completionDate);
        if (state.completionDate != null) {
            completionDate = getDate(state.completionDate);
        }
        mCompletionDateAlarm.setText(state.completionDateAlarm);
        if (state.completionDateAlarm != null) {
            completionDateAlarm = getDate(state.completionDateAlarm);
        }
    }

    /**
     * Init code for spinners
     */
    private void initSpinners() {
        String[] assessmentTypes = getResources().getStringArray(R.array.assessment_types);
        String[] assessmentStatuses = getResources().getStringArray(R.array.assessment_values);

        ArrayAdapter<String> assessmentTypeItemAdapter = new ArrayAdapter<>(
                this, R.layout.item_spinner_right, assessmentTypes);
        assessmentTypeItemAdapter.setDropDownViewResource(R.layout.item_spinner_right);
        mAssessmentType.setAdapter(assessmentTypeItemAdapter);

        ArrayAdapter<String> assessmentStatusItemAdapter = new ArrayAdapter<>(
                this, R.layout.item_spinner_right, assessmentStatuses);
        assessmentStatusItemAdapter.setDropDownViewResource(R.layout.item_spinner_right);
        mStatus.setAdapter(assessmentStatusItemAdapter);

    }

    /**
     * Handles rendering the alarm icon as either empty or active
     *
     * @param alarmDate The alarm alarmDate
     */
    private void renderAlarm(Date alarmDate) {
        ImageView imageView = imageViewCompletionDateAlert;
        int dr = alarmDate == null ? R.drawable.ic_add_alert : R.drawable.ic_alarm_active;
        float scaleX = alarmDate == null ? 1F : 1.2F;
        float scaleY = alarmDate == null ? 1F : 1.1F;

        imageView.setImageResource(dr);
        imageView.setScaleX(scaleX);
        imageView.setScaleY(scaleY);
    }

    /**
     * Handles the completion date alarm notification scheduling.
     */
    private void handleAlarmNotification() {
        AlarmNotificationManager alm = AlarmNotificationManager.getInstance();
        String title = "WGU Scheduler Assessment Alert";
        String message = mTitle.getText() + " is ";

        if (!"".equals(String.valueOf(mCompletionDate.getText())) && completionDateAlarm != null) {
            String cEnding = "scheduled to be completed on " + mCompletionDate.getText();
            alm.registerAlarmNotification(this, completionDateAlarm, mId, "end",
                    title, message + cEnding);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSpinners();

    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(AssessmentEditorViewModel.class);
        mViewModel.mLiveAssessment.observe(this, (assessment) -> {
            if (assessment != null) {
                mTitle.setText(state.title == null ? assessment.getTitle() : state.title);

                SpinnerAdapter assessmentTypeSpinnerAdapter = mAssessmentType.getAdapter();
                ArrayAdapter<String> assessmentTypeAdapter = (ArrayAdapter<String>)assessmentTypeSpinnerAdapter;
                mAssessmentType.setSelection(state.assessmentTypePosition == null ?
                        assessmentTypeAdapter.getPosition(assessment.getType()) :
                        state.assessmentTypePosition);
                SpinnerAdapter statusSpinnerAdapter = mStatus.getAdapter();
                ArrayAdapter<String> statusAdapter = (ArrayAdapter<String>) statusSpinnerAdapter;
                mStatus.setSelection(state.statusPosition == null ?
                        statusAdapter.getPosition(assessment.getStatus()) :
                        state.statusPosition);
                completionDate = state.completionDate == null ?
                        assessment.getCompletionDate() :
                        getDate(state.completionDate);
                completionDateAlarm = state.completionDateAlarm == null ?
                        assessment.getCompletionDateAlarm() :
                        getDate(state.completionDateAlarm);
                renderAlarm(completionDateAlarm);
                if (completionDate != null) {
                    mCompletionDate.setText(getFormattedDate(completionDate));
                }
                if (completionDateAlarm != null) {
                    mCompletionDateAlarm.setText(getFormattedDate(completionDateAlarm));
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

    @Override
    protected void save() {
        String title = String.valueOf(mTitle.getText());
        String assessmentType = String.valueOf(mAssessmentType.getSelectedItem());
        String status = String.valueOf(mStatus.getSelectedItem());
        String completionDate = String.valueOf(mCompletionDate.getText());
        Date cdAlarm = completionDateAlarm;

        if (title.trim().isEmpty()) {
            showValidationError("Missing title", "Please enter a title");
            return;
        }
        mViewModel.saveAssessment(mParentId, assessmentType, title, status, completionDate, cdAlarm);
        handleAlarmNotification();
        Toast.makeText(AssessmentEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @Override
    protected void delete() {
        AssessmentEntity course = mViewModel.mLiveAssessment.getValue();
        String title = course.getTitle();
        mViewModel.deleteAssessment();
        String text = title + " Deleted";
        Toast.makeText(AssessmentEditorActivity.this, text, Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @Override
    protected void saveState(Bundle outState) {
        outState.putString(getString(R.string.ASSESSMENT_TITLE_KEY), String.valueOf(mTitle.getText()));
        outState.putInt(getString(R.string.ASSESSMENT_TYPE_KEY), mAssessmentType.getSelectedItemPosition());
        outState.putInt(getString(R.string.ASSESSMENT_STATUS_KEY), mStatus.getSelectedItemPosition());
        outState.putString(getString(R.string.ASSESSMENT_END_DATE_KEY), String.valueOf(mCompletionDate.getText()));
        outState.putString(getString(R.string.ASSESSMENT_END_DATE_ALARM_KEY), String.valueOf(mCompletionDateAlarm.getText()));
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            state.title = savedInstanceState.getString(getString(R.string.ASSESSMENT_TITLE_KEY));
            state.assessmentTypePosition = savedInstanceState.getInt(getString(R.string.ASSESSMENT_TYPE_KEY));
            state.statusPosition = savedInstanceState.getInt(getString(R.string.ASSESSMENT_STATUS_KEY));
            state.completionDate = savedInstanceState.getString(getString(R.string.ASSESSMENT_END_DATE_KEY));
            state.completionDateAlarm = savedInstanceState.getString(getString(R.string.ASSESSMENT_END_DATE_ALARM_KEY));
            loadState();
            renderAlarm(completionDate);
        }
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

    @OnClick(R.id.text_view_assessment_editor_end_date)
    void completionDateLabelClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mCompletionDate, completionDate);
        dateDialog.show(getSupportFragmentManager(), "assessmentCompletionDatePicker");
    }

    @OnClick(R.id.text_view_assessment_editor_end_date_value)
    void completionDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mCompletionDate, completionDate);
        dateDialog.show(getSupportFragmentManager(), "assessmentCompletionDatePicker");
    }

    @OnClick(R.id.image_view_assessment_end_date_alert)
    void completionDateAlertClickHandler() {
        if ("".equals(String.valueOf(mCompletionDate.getText()))) {
            String text = "Please select a end date before adding an end date alarm";
            showValidationError("Missing end date", text);
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
                        mCompletionDateAlarm.setText(getFormattedDate(date));
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

    @OnClick(R.id.text_view_assessment_editor_assessment_type)
    void assessmentTypeLabelClickHandler() {
        mAssessmentType.performClick();
    }

    @OnClick(R.id.text_view_assessment_editor_status)
    void statusLabelClickHandler() {
        mStatus.performClick();
    }

    private static class State {
        String title;
        Integer assessmentTypePosition;
        Integer statusPosition;
        String completionDate;
        String completionDateAlarm;
    }
}
