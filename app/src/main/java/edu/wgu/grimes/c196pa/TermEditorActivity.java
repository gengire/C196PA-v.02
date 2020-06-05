package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c196pa.viewmodels.TermEditorViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.CourseAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class TermEditorActivity extends AbstractEditorActivity {

    private TermEditorViewModel mViewModel;

    @BindView(R.id.edit_text_term_editor_title)
    EditText mTitle;

    @BindView(R.id.text_view_term_editor_start_date_value)
    TextView mStartDate;

    @BindView(R.id.text_view_term_editor_end_date_value)
    TextView mEndDate;

    @BindView(R.id.recycler_view_term_editor_course_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab_add_course)
    FloatingActionButton mFab;

    private boolean mNew;
    private boolean mEditing;
    private int mId;

    private Date startDate;
    private Date endDate;
    private CourseAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_term_editor;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
                intent.putExtra(Constants.TERM_ID_KEY, mId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getDeleteMenuItem() {
        return R.id.delete_term;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_term_editor;
    }

    @Override
    protected int getSaveMenuItem() {
        return R.id.save_term;
    }

    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CourseAdapter();

        mAdapter.setOnItemClickListener(course -> {
            Intent intent = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
            intent.putExtra(Constants.TERM_ID_KEY, mId);
            intent.putExtra(Constants.COURSE_ID_KEY, course.getId());
            startActivity(intent);
//            StyleableToast.makeText(TermEditorActivity.this, course.getTitle() + " clicked", R.style.toast_message).show();
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
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

    protected void initViewModel() {

        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(TermEditorViewModel.class);

        // update the view when the model is changed
        mViewModel.mLiveData.observe(this, (term) -> {
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
            mNew = true;
            mFab.setVisibility(View.GONE);
        } else {
            setTitle(getString(R.string.edit_term));
            mId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadTerm(mId);
            mViewModel.loadTermCourses(mId);
            mViewModel.getTermCourses().observe(this, (courses) -> {
                mAdapter.submitList(courses);
            });
            mFab.setVisibility(View.VISIBLE);
        }

    }

    protected void save() {
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

    protected void delete() {
        TermEntity term = mViewModel.mLiveData.getValue();
        String termTitle = term.getTitle();
        mViewModel.validateDeleteTerm(term,
                () -> { // success
                    mViewModel.deleteTerm();
                    String text = termTitle + " Deleted";
                    StyleableToast.makeText(TermEditorActivity.this, text, R.style.toast_message).show();
                    finish();
                }, () -> { // failure
                    String text = termTitle + " can't be deleted because it has courses associated with it";
                    StyleableToast.makeText(TermEditorActivity.this, text, R.style.toast_validation_failure).show();
                });
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
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
}
