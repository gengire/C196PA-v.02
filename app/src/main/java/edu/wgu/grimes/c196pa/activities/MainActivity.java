package edu.wgu.grimes.c196pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

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

        mViewModel.mCoursesCompleted.observe(MainActivity.this, courses -> {
            mCoursesCompleted.setText(String.valueOf(courses));
        });
        mViewModel.mCoursesInProgress.observe(MainActivity.this, courses -> {
            mCoursesInProgress.setText(String.valueOf(courses));
        });
        mViewModel.mCoursesDropped.observe(MainActivity.this, courses -> {
            mCoursesDropped.setText(String.valueOf(courses));
        });
        mViewModel.mCoursesFailed.observe(MainActivity.this, courses -> {
            mCoursesFailed.setText(String.valueOf(courses));
        });
        mViewModel.mAssessmentsPassed.observe(MainActivity.this, assessments -> {
            mAssessmentsPassed.setText(String.valueOf(assessments));
        });
        mViewModel.mAssessmentsPending.observe(MainActivity.this, assessments -> {
            mAssessmentsPending.setText(String.valueOf(assessments));
        });
        mViewModel.mAssessmentsFailed.observe(MainActivity.this, assessments -> {
            mAssessmentsFailed.setText(String.valueOf(assessments));
        });

    }

}
