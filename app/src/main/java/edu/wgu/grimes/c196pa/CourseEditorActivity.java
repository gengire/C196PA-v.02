package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.CourseEditorViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.AssessmentAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class CourseEditorActivity extends AbstractActivity {

    private CourseEditorViewModel mViewModel;

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

    private Date startDate;
    private Date endDate;

    AssessmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
                intent.putExtra(Constants.COURSE_ID_KEY, mId);
                startActivity(intent);
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
        startActivity(intent);
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

    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new AssessmentAdapter();

        mAdapter.setOnItemClickListener(assessment -> {
            Intent intent = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
            intent.putExtra(Constants.COURSE_ID_KEY, mId);
            intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessment.getId());
            startActivity(intent);
//            StyleableToast.makeText(CourseEditorActivity.this, assessment.getTitle() + " clicked", R.style.toast_message).show();
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    protected void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(CourseEditorViewModel.class);

        mViewModel.mLiveCourse.observe(this, (course) -> {
            if (course != null) {
                if (!mEditing) {
                    mTitle.setText(course.getTitle());
                    mCode.setText(course.getCode());
                    mCompetencyUnits.setSelection(((ArrayAdapter)mCompetencyUnits.getAdapter())
                            .getPosition(String.valueOf(course.getCompetencyUnits())));
                    mStatus.setSelection(((ArrayAdapter)mStatus.getAdapter())
                            .getPosition(course.getStatus()));
                }
                startDate = course.getStartDate();
                endDate = course.getEndDate();
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
        mViewModel.saveCourse(title, code, termId, cus, status, startDate, endDate);
        StyleableToast.makeText(CourseEditorActivity.this, title + " saved", R.style.toast_message).show();
        finish();
    }

    protected void delete() {
        CourseEntity course = mViewModel.mLiveCourse.getValue();
        String title = course.getTitle();
        mViewModel.validateDeleteCourse(course,
                () -> { // success
                    mViewModel.deleteCourse();
                    String text = title + " Deleted";
                    StyleableToast.makeText(CourseEditorActivity.this, text, R.style.toast_message).show();
                    finish();
                }, () -> { // failure
                    String text = title + " can't be deleted because it has courses associated with it";
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
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
