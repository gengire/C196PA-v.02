package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.TermEditorViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.CourseAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class TermEditorActivity extends AppCompatActivity {

    private TermEditorViewModel mViewModel;

    @BindView(R.id.edit_text_term_editor_title)
    EditText mTitle;

    @BindView(R.id.text_view_term_editor_start_date_value)
    TextView mStartDate;

    @BindView(R.id.text_view_term_editor_end_date_value)
    TextView mEndDate;

    @BindView(R.id.recycler_view_term_editor_course_list)
    RecyclerView mRecyclerViewCourseList;

    @BindView(R.id.fab_add_course)
    FloatingActionButton mFab;

    private boolean mNewTerm;
    private boolean mEditing;

    private Date startDate;
    private Date endDate;
    private CourseAdapter mAdapter;
    private int mTermId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
                intent.putExtra(Constants.TERM_ID_KEY, mTermId);
                startActivity(intent);
            }
        });

        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        mRecyclerViewCourseList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCourseList.setHasFixedSize(true);
        mAdapter = new CourseAdapter();

        mAdapter.setOnItemClickListener(course -> {
            Intent intent = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
            intent.putExtra(Constants.TERM_ID_KEY, mTermId);
            intent.putExtra(Constants.COURSE_ID_KEY, course.getId());
            startActivity(intent);
//            StyleableToast.makeText(TermEditorActivity.this, course.getTitle() + " clicked", R.style.toast_message).show();
        });
        mRecyclerViewCourseList.setAdapter(mAdapter);
        initSwipeDelete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_term_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                saveTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.text_view_term_editor_start_date_value)
    void startDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mStartDate, startDate);
        dateDialog.show(getSupportFragmentManager(), "startDatePicker");
    }

    @OnClick(R.id.text_view_term_editor_end_date_value)
    void endDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mEndDate, endDate);
        dateDialog.show(getSupportFragmentManager(), "endDatePicker");
    }

    private void initViewModel() {

        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(TermEditorViewModel.class);

        // update the view when the model is changed
        mViewModel.mLiveTerm.observe(this, (term) -> {
            if (term != null) {
                if (!mEditing) {
                    mTitle.setText(term.getTitle());
                }
                startDate = term.getStartDate();
                endDate = term.getEndDate();
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
            setTitle(getString(R.string.new_term));
            mNewTerm = true;
            mFab.setVisibility(View.GONE);
        } else {
            setTitle(getString(R.string.edit_term));
            mTermId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadTerm(mTermId);
            mViewModel.loadTermCourses(mTermId);
            mViewModel.getTermCourses().observe(this, (courses) -> {
                mAdapter.submitList(courses);
            });
            mFab.setVisibility(View.VISIBLE);
        }

    }

    private void saveTerm() {
        String title = mTitle.getText().toString();
        String startDate = mStartDate.getText().toString();
        String endDate = mEndDate.getText().toString();
        if (title.trim().isEmpty()) {
            StyleableToast.makeText(TermEditorActivity.this, "Please enter a title", R.style.toast_validation_failure).show();
            return;
        }
        mViewModel.saveTerm(title, startDate, endDate);
        StyleableToast.makeText(TermEditorActivity.this, title + " saved", R.style.toast_message).show();
        finish();
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
                CourseEntity course = mAdapter.getCourseAt(viewHolder.getAdapterPosition());
                String courseTitle = course.getTitle();

                mViewModel.validateDeleteCourse(course,
                        () -> { // success
                            mViewModel.deleteCourse(course);
                            String text = courseTitle + " Deleted";
                            StyleableToast.makeText(TermEditorActivity.this, text, R.style.toast_message).show();
                        }, () -> { // failure
                            mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            String text = courseTitle + " can't be deleted because it has courses associated with it";
                            StyleableToast.makeText(TermEditorActivity.this, text, R.style.toast_validation_failure).show();
                        });
            }
        }).attachToRecyclerView(mRecyclerViewCourseList);
    }

}
