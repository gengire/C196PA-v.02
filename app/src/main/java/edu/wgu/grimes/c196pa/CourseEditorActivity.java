package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;

import com.muddzdev.styleabletoast.StyleableToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.CourseEditorViewModel;

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

    @BindView(R.id.btn_course_notes)
    Button courseNotes;

    private Date startDate;
    private Date endDate;

    private boolean mNewCourse;
    private boolean mEditing;
    private int mTermId;
    private int courseId;

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

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.btn_course_notes)
    void courseNotesClickHandler() {
        Intent intent = new Intent(CourseEditorActivity.this, NotesListActivity.class);
        intent.putExtra(Constants.COURSE_ID_KEY, courseId);
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

    private void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(CourseEditorViewModel.class);

        mViewModel.mLiveCourse.observe(this, (course) -> {
            if (course != null) {
                if (!mEditing) {
                    mTitle.setText(course.getTitle());
                    mCode.setText(course.getCode());
                    mCompetencyUnits.setSelection(course.getCompetencyUnits());
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
        } else {
            setTitle(getString(R.string.edit_course));
            courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadCourse(courseId);
//            mViewModel.loadTermCourses(courseId);
//            mViewModel.getTermCourses().observe(this, (courses) -> {
//                mAdapter.setCourses(courses);
//            });
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

}
