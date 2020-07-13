//*********************************************************************************
//  File:             MainActivity.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************

package edu.wgu.grimes.c868pa.activities;

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
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.viewmodels.MainViewModel;

/**
 * Main Activity, responsible for controlling the main stats screen
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Local View Model for the main stats screen
     */
    private MainViewModel mViewModel;
    /**
     * Courses count
     */
    private int iCourseCount = 0;
    /**
     * Assessments count
     */
    private int iAssessmentCount = 0;
    /**
     * Courses completed count
     */
    private int iCourseCompleted = 0;
    /**
     * Courses in progress count
     */
    private int iCourseInProgress = 0;
    /**
     * Courses dropped count
     */
    private int iCourseDropped = 0;
    /**
     * Courses failed count
     */
    private int iCourseFailed = 0;
    /**
     * Assessments passed count
     */
    private int iAssessmentPassed = 0;
    /**
     * Assessments pending count
     */
    private int iAssessmentPending = 0;
    /**
     * Assessments failed count
     */
    private int iAssessmentFailed = 0;

    @BindView(R.id.text_view_courses_completed_value)
    protected TextView mCoursesCompleted;
    @BindView(R.id.text_view_courses_in_progress_value)
    protected TextView mCoursesInProgress;
    @BindView(R.id.text_view_courses_dropped_value)
    protected TextView mCoursesDropped;
    @BindView(R.id.text_view_courses_failed_value)
    protected TextView mCoursesFailed;
    @BindView(R.id.text_view_assessments_passed_value)
    protected TextView mAssessmentsPassed;
    @BindView(R.id.text_view_assessments_pending_value)
    protected TextView mAssessmentsPending;
    @BindView(R.id.text_view_assessments_failed_value)
    protected TextView mAssessmentsFailed;

    /**
     * Sets up all the observers of the live data coming from the database and updates the view
     * when the data changes.
     */
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

    /**
     * Helper to handle when multiple stats need to be updated due to a single change in the data
     *
     * @param numerator The numerator of the ratio
     * @param denominator The denominator of the ratio
     * @param textView The text view to update
     */
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

    @OnClick(R.id.btn_terms_list)
    protected void onTermsClick() {
        Intent intent = new Intent(MainActivity.this, TermsListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_add_sample_terms)
    protected void onAddSamplesClick() {
        mViewModel.addSampleData();
    }

    @OnClick(R.id.btn_delete_all_terms)
    protected void onDeleteAllClick() {
        mViewModel.deleteAllData();
    }

}
