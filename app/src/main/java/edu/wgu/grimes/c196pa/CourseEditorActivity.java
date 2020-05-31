package edu.wgu.grimes.c196pa;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.muddzdev.styleabletoast.StyleableToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.CourseEditorViewModel;
import edu.wgu.grimes.c196pa.viewmodels.TermEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class CourseEditorActivity extends AppCompatActivity {

    private CourseEditorViewModel mViewModel;

    @BindView(R.id.edit_text_course_editor_title)
    EditText mTitle;

    @BindView(R.id.edit_text_course_editor_code)
    EditText mCode;

    @BindView(R.id.edit_text_course_editor_cus)
    EditText mCompetencyUnits;

    @BindView(R.id.edit_text_course_editor_term_id)
    EditText mTermId;

    @BindView(R.id.edit_text_course_editor_status)
    EditText mStatus;

    @BindView(R.id.text_view_course_editor_start_date_value)
    TextView mStartDate;

    @BindView(R.id.text_view_course_editor_end_date_value)
    TextView mEndDate;


    private Date startDate;
    private Date endDate;

    private boolean mNewTerm;
    private boolean mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        initViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_course_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    mCompetencyUnits.setText(String.valueOf(course.getCompetencyUnits()));
                    mStatus.setText(course.getStatus());
                    mTermId.setText(String.valueOf(course.getTermId()));
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
        if (extras == null) {
            setTitle(getString(R.string.new_course));
            mNewTerm = true;
        } else {
            setTitle(getString(R.string.edit_course));
            int courseId = extras.getInt(COURSE_ID_KEY);
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
        String cus = mCompetencyUnits.getText().toString();
        String status = mStatus.getText().toString();
        String termId = mTermId.getText().toString();
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


}
