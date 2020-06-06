package edu.wgu.grimes.c196pa.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.AlertReceiver;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.CourseEditorViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.AssessmentAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class CourseEditorActivity extends AbstractEditorActivity {

    private NotificationManagerCompat notificationManager;

    @BindView(R.id.edit_text_course_editor_title)
    EditText mTitle;
    @BindView(R.id.edit_text_course_editor_code)
    EditText mCode;
    @BindView(R.id.spinner_course_editor_cus)
    Spinner mCompetencyUnits;
    @BindView(R.id.spinner_course_editor_status)
    Spinner mStatus;
    @BindView(R.id.text_view_course_editor_start_date_value)
    TextView mStartDate;
    @BindView(R.id.text_view_course_editor_end_date_value)
    TextView mEndDate;
    @BindView(R.id.recycler_view_course_editor_assessment_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_add_assessment)
    FloatingActionButton mFab;
    @BindView(R.id.btn_course_notes)
    Button mCourseNotes;

    AssessmentAdapter mAdapter;
    private CourseEditorViewModel mViewModel;
    private Date startDate;
    private Date startDateAlarm;
    private Date endDate;
    private Date endDateAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationManager = NotificationManagerCompat.from(this);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
                intent.putExtra(Constants.COURSE_ID_KEY, mId);
                openActivity(intent);
            }
        });
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

    @OnClick(R.id.text_view_course_editor_start_date_value)
    void startDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mStartDate, startDate);
        dateDialog.show(getSupportFragmentManager(), "courseStartDatePicker");
    }

    @OnClick(R.id.text_view_course_editor_end_date_value)
    void endDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mEndDate, endDate);
        dateDialog.show(getSupportFragmentManager(), "courseEndDatePicker");
    }

    @OnClick(R.id.image_view_course_start_date_alert)
    void startDateAlertClickHandler() {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra(Constants.COURSE_ALARM_TITLE_ID_KEY, "alarm title");
        intent.putExtra(Constants.COURSE_ALARM_MESSAGE_ID_KEY, "alarm message");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        Calendar now = Calendar.getInstance();
        Date dNow = now.getTime();
        boolean startDateIsBlankOrAlreadyPassed = startDateAlarm == null || dNow.after(startDateAlarm);

        if (startDateIsBlankOrAlreadyPassed) {
            now.add(Calendar.SECOND, 5);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), pendingIntent);
            startDateAlarm = now.getTime();
            save(false);
            StyleableToast.makeText(CourseEditorActivity.this, "start date alarm set", R.style.toast_message).show();
        } else {
            // cancel the alarm
            alarmManager.cancel(pendingIntent);
            startDateAlarm = null;
            save(false);
            StyleableToast.makeText(CourseEditorActivity.this, "start date alarm cancelled", R.style.toast_message).show();
        }

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
//            StyleableToast.makeText(CourseEditorActivity.this, assessment.getTitle() + " clicked", R.style.toast_message).show();
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(CourseEditorViewModel.class);
        mViewModel.mLiveCourse.observe(this, (course) -> {
            if (course != null) {
                if (!mEditing) {
                    mTitle.setText(course.getTitle());
                    mCode.setText(course.getCode());
                    mCompetencyUnits.setSelection(((ArrayAdapter) mCompetencyUnits.getAdapter())
                            .getPosition(String.valueOf(course.getCompetencyUnits())));
                    mStatus.setSelection(((ArrayAdapter) mStatus.getAdapter())
                            .getPosition(course.getStatus()));
                }
                startDate = course.getStartDate();
                startDateAlarm = course.getStartDateAlarm();
                endDate = course.getEndDate();
                endDateAlarm = course.getEndDateAlarm();
                if (startDate != null) {
                    mStartDate.setText(getFormattedDate(startDate));
                }
                if (endDate != null) {
                    mEndDate.setText(getFormattedDate(endDate));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        mParentId = extras.getInt(TERM_ID_KEY);

        if (extras.getInt(COURSE_ID_KEY) == 0) {
            setTitle(getString(R.string.new_course));
            mNew = true;
            mCourseNotes.setVisibility(View.GONE);
        } else {
            setTitle(getString(R.string.edit_course));
            mId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadCourse(mId);
            mViewModel.loadCourseAssessments(mId);
            mViewModel.getCourseAssessments().observe(this, (assessments) -> {
                mAdapter.submitList(assessments);
            });
        }
    }

    protected void save() {
        save(true);
    }

    private void save(boolean finishActivity) {
        String title = mTitle.getText().toString();
        String code = mCode.getText().toString();
        String cus = String.valueOf(mCompetencyUnits.getSelectedItemId());
        String status = String.valueOf(mStatus.getSelectedItem());
        String termId = String.valueOf(mParentId);
        String startDate = mStartDate.getText().toString();
        String endDate = mEndDate.getText().toString();

        if (title.trim().isEmpty()) {
            StyleableToast.makeText(CourseEditorActivity.this, "Please enter a title", R.style.toast_validation_failure).show();
            return;
        }
        mViewModel.saveCourse(title, code, termId, cus, status, startDate, startDateAlarm, endDate, endDateAlarm);
        if (finishActivity) {
            StyleableToast.makeText(CourseEditorActivity.this, title + " saved", R.style.toast_message).show();
            closeActivity();
        }
    }

    protected void delete() {
        CourseEntity course = mViewModel.mLiveCourse.getValue();
        String title = course.getTitle();
        mViewModel.validateDeleteCourse(course,
                () -> { // success
                    mViewModel.deleteCourse();
                    String text = title + " Deleted";
                    StyleableToast.makeText(CourseEditorActivity.this, text, R.style.toast_message).show();
                    closeActivity();
                }, () -> { // failure
                    String text = title + " can't be deleted because it has at least one course associated with it";
                    StyleableToast.makeText(CourseEditorActivity.this, text, R.style.toast_validation_failure).show();
                });
    }

    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        AssessmentEntity assessment = mAdapter.getAssessmentAt(viewHolder.getAdapterPosition());
        String courseTitle = assessment.getTitle();

        mViewModel.deleteAssessment(assessment);
        String text = courseTitle + " Deleted";
        StyleableToast.makeText(CourseEditorActivity.this, text, R.style.toast_message).show();
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
