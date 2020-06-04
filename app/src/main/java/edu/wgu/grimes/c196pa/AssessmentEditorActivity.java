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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.AssessmentEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.ASSESSMENT_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class AssessmentEditorActivity extends AppCompatActivity {

    AssessmentEditorViewModel mViewModel;

    @BindView(R.id.edit_text_assessment_editor_title)
    EditText mTitle;

    @BindView(R.id.spinner_assessment_editor_assessment_type)
    Spinner mAssessmentType;

    @BindView(R.id.spinner_assessment_editor_status)
    Spinner mStatus;

    @BindView(R.id.text_view_assessment_editor_completion_date_value)
    TextView mCompletionDate;

    private boolean mNewAssessment;
    private boolean mEditing;

    private Date completionDate;
    private int mCourseId;
    private int mAssessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
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

        initViewModel();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_assessment_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem deleteAssessment = menu.findItem(R.id.delete_assessment);
        deleteAssessment.setVisible(!mNewAssessment);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_assessment:
                saveAssessment();
                return true;
            case R.id.delete_assessment:
                deleteAssessment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.text_view_assessment_editor_completion_date_value)
    void startDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mCompletionDate, completionDate);
        dateDialog.show(getSupportFragmentManager(), "assessmentCompletionDatePicker");
    }

    private void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(AssessmentEditorViewModel.class);

        mViewModel.mLiveAssessment.observe(this, (assessment) -> {
            if (assessment != null) {
                if (!mEditing) {
                    mTitle.setText(assessment.getTitle());
                    mAssessmentType.setSelection(((ArrayAdapter) mAssessmentType.getAdapter())
                            .getPosition(String.valueOf(assessment.getType())));
                    mStatus.setSelection(((ArrayAdapter) mStatus.getAdapter())
                            .getPosition(String.valueOf(assessment.getStatus())));
                }
                completionDate = assessment.getCompletionDate();
                if (completionDate != null) {
                    mCompletionDate.setText(getFormattedDate(completionDate));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        mCourseId = extras.getInt(COURSE_ID_KEY);

        if (extras.getInt(ASSESSMENT_ID_KEY) == 0) {
            setTitle("New Assessment");
            mNewAssessment = true;
        } else {
            setTitle("Edit Assessment");
            mAssessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            mViewModel.loadAssessment(mAssessmentId);
//            mViewModel.loadCourseAssessments(mCourseId);
//            mViewModel.getCourseAssessments().observe(this, (assessments) -> {
//                mAdapter.submitList(assessments);
//            });
        }
    }

    private void saveAssessment() {
        String title = mTitle.getText().toString();
        String assessmentType = String.valueOf(mAssessmentType.getSelectedItem());
        String status = String.valueOf(mStatus.getSelectedItem());
        String completionDate = mCompletionDate.getText().toString();

        if (title.trim().isEmpty()) {
            StyleableToast.makeText(AssessmentEditorActivity.this, "Please enter a title", R.style.toast_validation_failure).show();
            return;
        }
        mViewModel.saveAssessment(mCourseId, assessmentType, title, status, completionDate);
        StyleableToast.makeText(AssessmentEditorActivity.this, title + " saved", R.style.toast_message).show();
        finish();
    }

    private void deleteAssessment() {
        AssessmentEntity course = mViewModel.mLiveAssessment.getValue();
        String title = course.getTitle();
        mViewModel.deleteAssessment();
        String text = title + " Deleted";
        StyleableToast.makeText(AssessmentEditorActivity.this, text, R.style.toast_message).show();
        finish();
    }


}
