package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.muddzdev.styleabletoast.StyleableToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

public class CourseEditorActivity extends AppCompatActivity {

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

    private boolean mNewCourse;
    private boolean mEditing;

    private Date startDate;
    private Date endDate;

    AssessmentAdapter mAdapter;
    private int mTermId;
    private int mCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ButterKnife.bind(this);


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
                intent.putExtra(Constants.COURSE_ID_KEY, mCourseId);
                startActivity(intent);
            }
        });

//        mCompetencyUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                compUnitsPosition = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                statusPosition = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
        initRecyclerView();
        initViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_course_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem deleteCourse = menu.findItem(R.id.delete_course);
        deleteCourse.setVisible(!mNewCourse);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveCourse();
                return true;
            case R.id.delete_course:
                deleteCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.btn_course_notes)
    void courseNotesClickHandler() {
        Intent intent = new Intent(CourseEditorActivity.this, NotesListActivity.class);
        intent.putExtra(Constants.COURSE_ID_KEY, mCourseId);
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

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new AssessmentAdapter();

        mAdapter.setOnItemClickListener(assessment -> {
            Intent intent = new Intent(CourseEditorActivity.this, AssessmentEditorActivity.class);
            intent.putExtra(Constants.COURSE_ID_KEY, mCourseId);
            intent.putExtra(Constants.ASSESSMENT_ID_KEY, assessment.getId());
            startActivity(intent);
//            StyleableToast.makeText(CourseEditorActivity.this, assessment.getTitle() + " clicked", R.style.toast_message).show();
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    private void initViewModel() {
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
        mTermId = extras.getInt(TERM_ID_KEY);

        if (extras.getInt(COURSE_ID_KEY) == 0) {
            setTitle(getString(R.string.new_course));
            mNewCourse = true;
            mCourseNotes.setVisibility(View.GONE);
        } else {
            setTitle(getString(R.string.edit_course));
            mCourseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadCourse(mCourseId);
            mViewModel.loadCourseAssessments(mCourseId);
            mViewModel.getCourseAssessments().observe(this, (assessments) -> {
                mAdapter.submitList(assessments);
            });
        }



    }

    private void saveCourse() {
        String title = mTitle.getText().toString();
        String code = mCode.getText().toString();
        String cus = String.valueOf(mCompetencyUnits.getSelectedItemId());
        String status = String.valueOf(mStatus.getSelectedItem());
        String termId = String.valueOf(mTermId);
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

    private void deleteCourse() {
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

    private void initSwipeDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AssessmentEntity assessment = mAdapter.getAssessmentAt(viewHolder.getAdapterPosition());
                String courseTitle = assessment.getTitle();

                mViewModel.deleteAssessment(assessment);
                String text = courseTitle + " Deleted";
                StyleableToast.makeText(CourseEditorActivity.this, text, R.style.toast_message).show();
            }
        }).attachToRecyclerView(mRecyclerView);
    }

}
