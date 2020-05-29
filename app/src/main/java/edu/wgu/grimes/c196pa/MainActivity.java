package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

    @OnClick(R.id.btn_terms_list)
    void termsClickHandler() {
        Intent intent = new Intent(MainActivity.this, TermsListActivity.class);
        startActivity(intent);
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

//        mViewModel.mTerms.observe(this, terms -> {
            populateStatistics();
//        });
    }


    private void populateStatistics() {
        TextView coursesCompleted = findViewById(R.id.text_view_courses_completed_value);
        coursesCompleted.setText(mViewModel.getCoursesCompleted());

        TextView coursesInProgress = findViewById(R.id.text_view_courses_in_progress_value);
        coursesInProgress.setText(mViewModel.getCoursesInProgress());

        TextView coursesDropped = findViewById(R.id.text_view_courses_dropped_value);
        coursesDropped.setText(mViewModel.getCoursesDropped());

        TextView coursesFailed = findViewById(R.id.text_view_courses_failed_value);
        coursesFailed.setText(mViewModel.getCoursesFailed());

        TextView assessmentsPending = findViewById(R.id.text_view_assessments_pending_value);
        assessmentsPending.setText(mViewModel.getAssessmentsPending());

        TextView assessmentsPassed = findViewById(R.id.text_view_assessments_passed_value);
        assessmentsPassed.setText(mViewModel.getAssessmentsPassed());

        TextView assessmentsFailed = findViewById(R.id.text_view_assessments_failed_value);
        assessmentsFailed.setText(mViewModel.getAssessmentsFailed());

        Toast.makeText(this, "Stats loaded", Toast.LENGTH_SHORT).show();
    }

}
