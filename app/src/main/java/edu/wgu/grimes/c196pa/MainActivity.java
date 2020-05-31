package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

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

    @OnClick(R.id.btn_terms_list)
    void termsClickHandler() {
        Intent intent = new Intent(MainActivity.this, TermsListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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


    private void populateStatistics() {

        TextView assessmentsPending = findViewById(R.id.text_view_assessments_pending_value);
        assessmentsPending.setText(mViewModel.getAssessmentsPending());

        TextView assessmentsPassed = findViewById(R.id.text_view_assessments_passed_value);
        assessmentsPassed.setText(mViewModel.getAssessmentsPassed());

        TextView assessmentsFailed = findViewById(R.id.text_view_assessments_failed_value);
        assessmentsFailed.setText(mViewModel.getAssessmentsFailed());

        Toast.makeText(this, "Stats loaded", Toast.LENGTH_SHORT).show();
    }

}
