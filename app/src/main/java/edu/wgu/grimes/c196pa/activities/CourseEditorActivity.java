package edu.wgu.grimes.c196pa.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.adapters.AssessmentAdapter;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.utilities.AlarmNotificationManager;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.utilities.HasDate;
import edu.wgu.grimes.c196pa.viewmodels.CourseEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class CourseEditorActivity extends AbstractEditorActivity implements NumberPicker.OnValueChangeListener {

    @BindView(R.id.edit_text_course_editor_title)
    EditText mTitle;
    @BindView(R.id.edit_text_course_editor_code)
    EditText mCode;
    @BindView(R.id.text_view_course_editor_cus_value)
    TextView mCus;
    @BindView(R.id.spinner_course_editor_status)
    Spinner mStatus;
    @BindView(R.id.text_view_course_editor_start_date_value)
    TextView mStartDate;
    @BindView(R.id.text_view_course_editor_end_date_value)
    TextView mEndDate;
    @BindView(R.id.text_view_course_editor_alarm_start_date_value)
    TextView mStartDateAlarm;
    @BindView(R.id.text_view_course_editor_alarm_end_date_value)
    TextView mEndDateAlarm;
    @BindView(R.id.recycler_view_course_editor_assessment_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_add_assessment)
    FloatingActionButton mFab;
    @BindView(R.id.btn_course_notes)
    Button mCourseNotes;
    @BindView(R.id.btn_course_mentors)
    Button mCourseMentors;
    @BindView(R.id.image_view_course_start_date_alert)
    ImageView imageViewStartDateAlert;
    @BindView(R.id.image_view_course_end_date_alert)
    ImageView imageViewEndDateAlert;

    AssessmentAdapter mAdapter;

    private CourseEditorViewModel mViewModel;
    private Date startDate;
    private Date startDateAlarm;
    private Date endDate;
    private Date endDateAlarm;
    private State state = new State();

    private static class State {
        String title;
        String code;
        String cus;
        Integer status;
        String startDate;
        String endDate;
        String startDateAlarm;
        String endDateAlarm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSpinners();

        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
            intent.putExtra(Constants.COURSE_ID_KEY, mId);
            openActivity(intent);
        });
    }

    @Override
    protected void saveState(Bundle outState) {
        outState.putString(getString(R.string.COURSE_TITLE_KEY), String.valueOf(mTitle.getText()));
        outState.putString(getString(R.string.COURSE_CODE_KEY), String.valueOf(mCode.getText()));
        outState.putString(getString(R.string.COURSE_COMPETENCY_UNITS_KEY), String.valueOf(mCus.getText()));
        outState.putInt(getString(R.string.COURSE_STATUS_KEY), mStatus.getSelectedItemPosition());
        outState.putString(getString(R.string.COURSE_START_DATE_KEY), String.valueOf(mStartDate.getText()));
        outState.putString(getString(R.string.COURSE_END_DATE_KEY), String.valueOf(mEndDate.getText()));
        outState.putString(getString(R.string.COURSE_START_DATE_ALARM_KEY), String.valueOf(mStartDateAlarm.getText()));
        outState.putString(getString(R.string.COURSE_END_DATE_ALARM_KEY), String.valueOf(mEndDateAlarm.getText()));
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            state.title = savedInstanceState.getString(getString(R.string.COURSE_TITLE_KEY));
            state.code = savedInstanceState.getString(getString(R.string.COURSE_CODE_KEY));
            state.cus = savedInstanceState.getString(getString(R.string.COURSE_COMPETENCY_UNITS_KEY));
            state.status = savedInstanceState.getInt(getString(R.string.COURSE_STATUS_KEY));
            state.startDate = savedInstanceState.getString(getString(R.string.COURSE_START_DATE_KEY));
            state.endDate = savedInstanceState.getString(getString(R.string.COURSE_END_DATE_KEY));
            state.startDateAlarm = savedInstanceState.getString(getString(R.string.COURSE_START_DATE_ALARM_KEY));
            state.endDateAlarm = savedInstanceState.getString(getString(R.string.COURSE_END_DATE_ALARM_KEY));
            loadState();
            renderAlarm(startDateAlarm, START);
            renderAlarm(endDateAlarm, END);
        }
    }

    private void loadState() {
        mTitle.setText(state.title);
        mCode.setText(state.code);
        mCus.setText(state.cus);
        mStatus.setSelection(state.status);
        mStartDate.setText(state.startDate);
        if (state.startDate != null) {
            startDate = getDate(state.startDate);
        }
        mStartDateAlarm.setText(state.startDateAlarm);
        if (state.startDateAlarm != null) {
            startDateAlarm = getDate(state.startDateAlarm);
        }
        mEndDate.setText(state.endDate);
        if (state.endDate != null) {
            endDate = getDate(state.endDate);
        }
        mEndDateAlarm.setText(state.endDateAlarm);
        if (state.endDateAlarm != null) {
            endDateAlarm = getDate(state.endDateAlarm);
        }
    }

    private void initSpinners() {
        String[] courseStatuses = getResources().getStringArray(R.array.status_values);

        ArrayAdapter<String> courseStatusItemAdapter = new ArrayAdapter<>(
                this, R.layout.item_spinner_right, courseStatuses);
        courseStatusItemAdapter.setDropDownViewResource(R.layout.item_spinner_right);
        mStatus.setAdapter(courseStatusItemAdapter);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_course_editor;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public int getMenu() {
        return R.menu.menu_course_editor;
    }

    @Override
    protected int getDeleteMenuItem() {
        return R.id.delete_course;
    }

    @Override
    protected int getSaveMenuItem() {
        return R.id.save_course;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        mCus.setText(String.valueOf(newVal));
    }

    @OnClick(R.id.btn_course_notes)
    void courseNotesClickHandler() {
        Intent intent = new Intent(CourseEditorActivity.this, NotesListActivity.class);
        intent.putExtra(Constants.COURSE_ID_KEY, mId);
        openActivity(intent);
    }

    @OnClick(R.id.btn_course_mentors)
    void courseMentorsClickHandler() {
        Intent intent = new Intent(CourseEditorActivity.this, MentorsListActivity.class);
        intent.putExtra(Constants.COURSE_ID_KEY, mId);
        openActivity(intent);
    }

    @OnClick(R.id.text_view_course_editor_start_date)
    void startDateLabelClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mStartDate, startDate);
        dateDialog.show(getSupportFragmentManager(), "courseStartDatePicker");
    }

    @OnClick(R.id.text_view_course_editor_start_date_value)
    void startDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mStartDate, startDate);
        dateDialog.show(getSupportFragmentManager(), "courseStartDatePicker");
    }

    @OnClick(R.id.text_view_course_editor_end_date)
    void endDateLabelClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mEndDate, endDate);
        dateDialog.show(getSupportFragmentManager(), "courseEndDatePicker");
    }

    @OnClick(R.id.text_view_course_editor_end_date_value)
    void endDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mEndDate, endDate);
        dateDialog.show(getSupportFragmentManager(), "courseEndDatePicker");
    }

    @OnClick(R.id.text_view_course_editor_cus)
    void cusLabelClickHandler() {
        openCompetencyUnitsPickerDialog();
    }

    @OnClick(R.id.text_view_course_editor_cus_value)
    void cusClickHandler() {
        openCompetencyUnitsPickerDialog();
    }

    private void openCompetencyUnitsPickerDialog() {
        final Dialog dialog = new Dialog(CourseEditorActivity.this);
        dialog.setTitle("Competency Units");
        dialog.setContentView(R.layout.number_picker);

        String strCus = String.valueOf(mCus.getText());
        int cusValue = "".equals(strCus) ? 0 : Integer.parseInt(strCus);

        NumberPicker mCuPicker = dialog.findViewById(R.id.number_picker);
        mCuPicker.setWrapSelectorWheel(false);
        mCuPicker.setMaxValue(10);
        mCuPicker.setMinValue(0);
        mCuPicker.setValue(cusValue); // set value needs to be set after set min and max :/
        mCuPicker.setOnValueChangedListener(this);

        Button btnSetCus = dialog.findViewById(R.id.btn_set_cus);
        btnSetCus.setOnClickListener(v -> {
            mCus.setText(String.valueOf(mCuPicker.getValue()));
            dialog.dismiss();
        });
        Button btnCancelCus = dialog.findViewById(R.id.btn_cancel_cus);
        btnCancelCus.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @OnClick(R.id.image_view_course_start_date_alert)
    void startDateAlertClickHandler() {
        if ("".equals(String.valueOf(mStartDate.getText()))) {
            String text = "Please select a start date before adding a start date alarm";
            showValidationError("Missing start date", text);
        } else {
            if (startDateAlarm == null) {
                DialogFragment dateDialog = new DatePickerFragment(new HasDate() {
                    @Override
                    public Date getDate() {
                        return startDateAlarm;
                    }

                    @Override
                    public void setDate(Date date) {
                        startDateAlarm = date;
                        mStartDateAlarm.setText(getFormattedDate(date));
                        renderAlarm(startDateAlarm, START);
                    }
                }, startDate);
                dateDialog.show(getSupportFragmentManager(), "courseStartAlarmDatePicker");
            } else {
                startDateAlarm = null;
                mStartDateAlarm.setText(null);
                renderAlarm(null, START);
            }
        }
    }

    @OnClick(R.id.image_view_course_end_date_alert)
    void endDateAlertClickHandler() {
        if ("".equals(String.valueOf(mEndDate.getText()))) {
            String text = "Please select an end date before adding an end date alarm";
            showValidationError("Missing end date", text);
        } else {
            if (endDateAlarm == null) {
                DialogFragment dateDialog = new DatePickerFragment(new HasDate() {
                    @Override
                    public Date getDate() {
                        return endDateAlarm;
                    }

                    @Override
                    public void setDate(Date date) {
                        endDateAlarm = date;
                        mEndDateAlarm.setText(getFormattedDate(date));
                        renderAlarm(endDateAlarm, END);
                    }
                }, endDate);
                dateDialog.show(getSupportFragmentManager(), "courseEndAlarmDatePicker");
            } else {
                endDateAlarm = null;
                mEndDateAlarm.setText(null);
                renderAlarm(null, END);
            }
        }
    }

    @OnClick(R.id.text_view_course_editor_status)
    void statusLabelClickHandler() {
        mStatus.performClick();
    }
    private static final int START = 1;
    private static final int END = 2;

    private void renderAlarm(Date date, int which) {
        ImageView iv = which == 1 ? imageViewStartDateAlert : imageViewEndDateAlert;
        int dr = date == null ? R.drawable.ic_add_alert : R.drawable.ic_alarm_active;
        float x = date == null ? 1F : 1.2F;
        float y = date == null ? 1F : 1.1F;
        setAlarmActive(iv, dr, x, y);
    }

    private void setAlarmActive(ImageView imageView, int p, float scaleX, float scaleY) {
        imageView.setImageResource(p);
        imageView.setScaleX(scaleX);
        imageView.setScaleY(scaleY);
    }

    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new AssessmentAdapter();

        mAdapter.setOnItemClickListener(assessment -> {
            Intent intent = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
            intent.putExtra(Constants.COURSE_ID_KEY, mId);
            intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessment.getId());
            openActivity(intent);
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        initSwipeDelete();

    }

    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(CourseEditorViewModel.class);
        mViewModel.mLiveCourse.observe(this, (course) -> {
            if (course != null) {
                mTitle.setText(state.title == null ? course.getTitle() : state.title);
                mCode.setText(state.code == null ? course.getCode() : state.code);
                mCus.setText(state.cus == null ? String.valueOf(course.getCompetencyUnits()) : state.cus);

                SpinnerAdapter ssa = mStatus.getAdapter();
                ArrayAdapter<String> statusAdapter = (ArrayAdapter<String>) ssa;
                mStatus.setSelection(state.status == null ? statusAdapter.getPosition(course.getStatus()) : state.status);
                startDate = state.startDate == null ? course.getStartDate() : getDate(state.startDate);
                startDateAlarm = state.startDateAlarm == null ? course.getStartDateAlarm() : getDate(state.startDateAlarm);
                renderAlarm(startDateAlarm, START);
                endDate = state.endDate == null ? course.getEndDate() : getDate(state.endDate);
                endDateAlarm = state.endDateAlarm == null ? course.getEndDateAlarm() : getDate(state.endDateAlarm);
                renderAlarm(endDateAlarm, END);
                if (startDate != null) {
                    mStartDate.setText(getFormattedDate(startDate));
                }
                if (endDate != null) {
                    mEndDate.setText(getFormattedDate(endDate));
                }
                if (startDateAlarm != null) {
                    mStartDateAlarm.setText(getFormattedDate(startDateAlarm));
                }
                if (endDateAlarm != null) {
                    mEndDateAlarm.setText(getFormattedDate(endDateAlarm));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mParentId = extras.getInt(TERM_ID_KEY);

            if (extras.getInt(COURSE_ID_KEY) == 0) {
                setTitle(getString(R.string.new_course));
                mNew = true;
                mCourseNotes.setVisibility(View.GONE);
                mCourseMentors.setVisibility(View.GONE);
                mFab.setVisibility(View.GONE);
            } else {
                setTitle(getString(R.string.edit_course));
                mId = extras.getInt(COURSE_ID_KEY);
                mViewModel.loadCourse(mId);
                mViewModel.loadCourseAssessments(mId);
                mViewModel.getCourseAssessments().observe(this, (assessments) ->
                        mAdapter.submitList(assessments));
            }
        }
    }

    protected void save() {
        String title = String.valueOf(mTitle.getText());
        String code = String.valueOf(mCode.getText());
        String cus = String.valueOf(mCus.getText());
        String status = String.valueOf(mStatus.getSelectedItem());
        String termId = String.valueOf(mParentId);
        String startDate = String.valueOf(mStartDate.getText());
        Date sdAlarm = startDateAlarm;
        String endDate = String.valueOf(mEndDate.getText());
        Date edAlarm = endDateAlarm;

        if (title.trim().isEmpty()) {
            showValidationError("Missing title", "Please enter a title");
            return;
        }
        mViewModel.saveCourse(title, code, termId, cus, status, startDate, sdAlarm, endDate, edAlarm);
        handleAlarmNotifications();

        Toast.makeText(CourseEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    private void handleAlarmNotifications() {
        AlarmNotificationManager alm = AlarmNotificationManager.getInstance();
        String title = "WGU Scheduler Course Alert";
        String message = mTitle.getText() + " is ";

        if (!"".equals(String.valueOf(mStartDate.getText())) && startDateAlarm != null) {
            String sdEnding = "starting on " + mStartDate.getText();
            alm.registerAlarmNotification(this, startDateAlarm, mId, "start",
                    title, message + sdEnding);
        }
        if (!"".equals(String.valueOf(mEndDate.getText())) && endDateAlarm != null) {
            String edEnding = "ending on " + mEndDate.getText();
            alm.registerAlarmNotification(this, endDateAlarm, mId, "end",
                    title, message + edEnding);
        }
    }

    protected void delete() {
        CourseEntity course = mViewModel.mLiveCourse.getValue();
        if (course != null) {
            String title = course.getTitle();
            mViewModel.validateDeleteCourse(course,
                    () -> { // success
                        mViewModel.deleteCourse();
                        String text = title + " Deleted";
                        Toast.makeText(CourseEditorActivity.this, text, Toast.LENGTH_SHORT).show();
                        closeActivity();
                    }, () -> { // failure
                        String text = title + " can't be deleted because it has at least one assessment associated with it";
                        showValidationError("Can't delete", text);
                    });
        }
    }

    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        AssessmentEntity assessment = mAdapter.getAssessmentAt(viewHolder.getAdapterPosition());
        String assessmentTitle = assessment.getTitle();

        mViewModel.deleteAssessment(assessment);
        String text = assessmentTitle + " Deleted";
        Toast.makeText(CourseEditorActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSwipeCancel(RecyclerView.ViewHolder viewHolder) {
        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

}
