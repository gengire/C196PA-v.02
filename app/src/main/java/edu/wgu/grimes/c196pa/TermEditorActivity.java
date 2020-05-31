package edu.wgu.grimes.c196pa;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.TermEditorViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.CourseAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class TermEditorActivity extends AppCompatActivity {

    private TermEditorViewModel mViewModel;

    @BindView(R.id.edit_text_title)
    EditText mTitle;

    @BindView(R.id.text_view_term_editor_start_date_value)
    TextView mStartDate;

    @BindView(R.id.text_view_term_editor_end_date_value)
    TextView mEndDate;

    @BindView(R.id.recycler_view_course_list)
    RecyclerView mRecyclerViewCourseList;

    private boolean mNewTerm;
    private boolean mEditing;

    private Date startDate;
    private Date endDate;
    private CourseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Term");

        ButterKnife.bind(this);

        FloatingActionButton fab = findViewById(R.id.fab_term_editor);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
//            Intent intent = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
//            intent.putExtra(Constants.COURSE_ID_KEY, course.getId());
//            startActivity(intent);
            StyleableToast.makeText(TermEditorActivity.this, course.getTitle() + " clicked", R.style.toast_message).show();
        });
        mRecyclerViewCourseList.setAdapter(mAdapter);
        initSwipeDelete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.term_editor_menu, menu);
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

//        mViewModel.mCourses.observe(this, coursesObserver);

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
        } else {
            setTitle(getString(R.string.edit_term));
            int termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadTerm(termId);
            mViewModel.loadTermCourses(termId);
            mViewModel.getTermCourses().observe(this, (courses) -> {
                StyleableToast.makeText(TermEditorActivity.this, "courses updated", R.style.toast_message).show();
                mAdapter.setCourses(courses);


//            coursesData.clear();
//            coursesData.addAll(courseEntities);
//
//            if (mAdapter == null) {
//                mAdapter = new CoursesAdapter(coursesData, TermEditorActivity.this);
//                mRecyclerViewCourseList.setAdapter(mAdapter);
//            } else {
//                mAdapter.notifyDataSetChanged();
//            }

            });

        }

    }

    private void saveTerm() {
        String title = mTitle.getText().toString();
        String startDate = mStartDate.getText().toString();
        String endDate = mEndDate.getText().toString();
        if (title.trim().isEmpty()) {
            Toast toast = Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 8);
            toast.show();
            return;
        }
        mViewModel.saveTerm(title, startDate, endDate);
        Toast.makeText(this, title + " Saved", Toast.LENGTH_SHORT).show();
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
