package edu.wgu.grimes.c196pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_view_courses_completed_value)
    TextView mCoursesCompleted;
    @BindView(R.id.text_view_courses_in_progress_value)
    TextView mCoursesInProgress;
    @BindView(R.id.text_view_courses_dropped_value)
    TextView mCoursesDropped;
    @BindView(R.id.text_view_courses_failed_value)
    TextView mCoursesFailed;
    @BindView(R.id.text_view_assessments_passed_value)
    TextView mAssessmentsPassed;
    @BindView(R.id.text_view_assessments_pending_value)
    TextView mAssessmentsPending;
    @BindView(R.id.text_view_assessments_failed_value)
    TextView mAssessmentsFailed;
    private MainViewModel mViewModel;

    private int iCourseCount = 0;
    private int iAssessmentCount = 0;
    private int iCourseCompleted = 0;
    private int iCourseInProgress = 0;
    private int iCourseDropped = 0;
    private int iCourseFailed = 0;
    private int iAssessmentPassed = 0;
    private int iAssessmentPending = 0;
    private int iAssessmentFailed = 0;

    @OnClick(R.id.btn_terms_list)
    void termsClickHandler() {
        Intent intent = new Intent(MainActivity.this, TermsListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_add_sample_terms)
    void addSamplesClickHandler() {
        mViewModel.addSampleData();
    }

    @OnClick(R.id.btn_delete_all_terms)
    void deleteAllClickHandler() {
        mViewModel.deleteAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initViewModel();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initViewModel() {

        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        mViewModel.mTotalCourses.observe(MainActivity.this, courseCount -> {
            iCourseCount = courseCount;
            updateStats(iCourseCompleted, iCourseCount, mCoursesCompleted);
            updateStats(iCourseInProgress, iCourseCount, mCoursesInProgress);
            updateStats(iCourseDropped, iCourseCount, mCoursesDropped);
            updateStats(iCourseFailed, iCourseCount, mCoursesFailed);
        });
        mViewModel.mTotalAssessments.observe(MainActivity.this, assessmentCount -> {
            iAssessmentCount = assessmentCount;
            updateStats(iAssessmentPassed, iAssessmentCount, mAssessmentsPassed);
            updateStats(iAssessmentPending, iAssessmentCount, mAssessmentsPending);
            updateStats(iAssessmentFailed, iAssessmentCount, mAssessmentsFailed);
        });
        mViewModel.mCoursesCompleted.observe(MainActivity.this, courses -> {
            iCourseCompleted = courses;
            updateStats(iCourseCompleted, iCourseCount, mCoursesCompleted);
        });
        mViewModel.mCoursesInProgress.observe(MainActivity.this, courses -> {
            iCourseInProgress = courses;
            updateStats(iCourseInProgress, iCourseCount, mCoursesInProgress);
        });
        mViewModel.mCoursesDropped.observe(MainActivity.this, courses -> {
            iCourseDropped = courses;
            updateStats(iCourseDropped, iCourseCount, mCoursesDropped);
        });
        mViewModel.mCoursesFailed.observe(MainActivity.this, courses -> {
            iCourseFailed = courses;
            updateStats(iCourseFailed, iCourseCount, mCoursesFailed);
        });
        mViewModel.mAssessmentsPassed.observe(MainActivity.this, assessments -> {
            iAssessmentPassed = assessments;
            updateStats(iAssessmentPassed, iAssessmentCount, mAssessmentsPassed);
        });
        mViewModel.mAssessmentsPending.observe(MainActivity.this, assessments -> {
            iAssessmentPending = assessments;
            updateStats(iAssessmentPending, iAssessmentCount, mAssessmentsPending);
        });
        mViewModel.mAssessmentsFailed.observe(MainActivity.this, assessments -> {
            iAssessmentFailed = assessments;
            updateStats(iAssessmentFailed, iAssessmentCount, mAssessmentsFailed);
        });

    }

    private void updateStats(int numerator, int denominator, TextView textView) {
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if (numerator < 10) {
            sb.append("  ");
        }
        sb.append(numerator)
                .append(" / ");
        if (denominator < 10) {
            sb.append("  ");
        }
        sb.append(denominator)
                .append("   ")
                .append("(");
        if (numerator > 0) {
            sb.append(decimalFormat.format((double) (numerator * 100) / denominator));
        } else {
            sb.append(0);
        }
        sb.append("%)");
        textView.setText(sb.toString());
    }

}
