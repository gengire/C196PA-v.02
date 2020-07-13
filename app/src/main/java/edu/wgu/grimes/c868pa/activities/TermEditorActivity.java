//*********************************************************************************
//  File:             TermEditorActivity.java
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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.adapters.CourseAdapter;
import edu.wgu.grimes.c868pa.database.entities.CourseEntity;
import edu.wgu.grimes.c868pa.database.entities.TermEntity;
import edu.wgu.grimes.c868pa.utilities.Constants;
import edu.wgu.grimes.c868pa.utilities.DatePickerFragment;
import edu.wgu.grimes.c868pa.viewmodels.TermEditorViewModel;

import static edu.wgu.grimes.c868pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c868pa.utilities.StringUtils.getDate;
import static edu.wgu.grimes.c868pa.utilities.StringUtils.getFormattedDate;

/**
 * Term Editor Activity, responsible for controlling both new and edit modes for terms
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class TermEditorActivity extends AbstractEditorActivity {
    /**
     * Local View Model for the term editor
     */
    private TermEditorViewModel mViewModel;
    /**
     *
     */
    private CourseAdapter mAdapter;
    /**
     * Used with the Date Picker Fragment
     */
    private Date startDate;
    /**
     * Used with the Date Picker Fragment
     */
    private Date endDate;
    /**
     * Local internal state for this activity
     */
    private final State state = new State();

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

    /**
     * Loads the data from the internal state to the screen
     */
    private void loadState() {
        mTitle.setText(state.title);
        mStartDate.setText(state.startDate);
        mEndDate.setText(state.endDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
            intent.putExtra(Constants.TERM_ID_KEY, mId);
            openActivity(intent);
        });
    }

    @Override
    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CourseAdapter();

        mAdapter.setOnItemClickListener(course -> {
            Intent intent = new Intent(TermEditorActivity.this, CourseEditorActivity.class);
            intent.putExtra(Constants.TERM_ID_KEY, mId);
            intent.putExtra(Constants.COURSE_ID_KEY, course.getId());
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void onSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        CourseEntity course = mAdapter.getCourseAt(viewHolder.getAdapterPosition());
        String courseTitle = course.getCourseTitle();

        mViewModel.validateDeleteCourse(course,
                () -> { // success
                    mViewModel.deleteCourse(course);
                    String text = courseTitle + " Deleted";
                    Toast.makeText(TermEditorActivity.this, text, Toast.LENGTH_SHORT).show();
                }, () -> { // failure
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    String text = courseTitle + " can't be deleted because it has at least one assessment associated with it";
                    showValidationError("Can't delete", text);
                });
    }

    @Override
    protected void onSwipeDeleteCancel(RecyclerView.ViewHolder viewHolder) {
        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(TermEditorViewModel.class);

        // update the view when the model is changed
        mViewModel.mLiveData.observe(this, (term) -> {
            if (term != null) {
                mTitle.setText(state.title == null ? term.getTitle() : state.title);
                startDate = state.startDate == null ? term.getStartDate() : getDate(state.startDate);
                endDate = state.endDate == null ? term.getEndDate() : getDate(state.endDate);
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
            mViewModel.getTermCourses().observe(this, (courses) -> mAdapter.submitList(courses));
        }

    }

    @Override
    protected void save() {
        String title = String.valueOf(mTitle.getText());
        String startDate = String.valueOf(mStartDate.getText());
        String endDate = String.valueOf(mEndDate.getText());
        if (title.trim().isEmpty()) {
            showValidationError("Missing title", "Please enter a title");
            return;
        }
        mViewModel.saveTerm(title, startDate, endDate);
        Toast.makeText(TermEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @Override
    protected void delete() {
        TermEntity term = mViewModel.mLiveData.getValue();
        String termTitle = term == null ? "<NA>" : term.getTitle();
        mViewModel.validateDeleteTerm(term,
                () -> { // success
                    mViewModel.deleteTerm();
                    String text = termTitle + " Deleted";
                    Toast.makeText(TermEditorActivity.this, text, Toast.LENGTH_SHORT).show();
                    closeActivity();
                }, () -> { // failure
                    String text = termTitle + " can't be deleted because it has at least one course associated with it";
                    showValidationError("Can't delete", text);
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_term_editor;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            state.title = savedInstanceState.getString(getString(R.string.TERM_TITLE_KEY));
            state.startDate = savedInstanceState.getString(getString(R.string.TERM_START_DATE_KEY));
            state.endDate = savedInstanceState.getString(getString(R.string.TERM_END_DATE_KEY));
            loadState();
        }
    }

    @Override
    protected void saveState(@NonNull Bundle outState) {
        outState.putString(getString(R.string.TERM_TITLE_KEY), String.valueOf(mTitle.getText()));
        outState.putString(getString(R.string.TERM_START_DATE_KEY), String.valueOf(mStartDate.getText()));
        outState.putString(getString(R.string.TERM_END_DATE_KEY), String.valueOf(mEndDate.getText()));
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

    @OnClick(R.id.text_view_term_editor_start_date_value)
    void startDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mStartDate, startDate);
        dateDialog.show(getSupportFragmentManager(), "startDatePicker");
    }

    @OnClick(R.id.text_view_term_editor_start_date)
    void startDateLabelClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mStartDate, startDate);
        dateDialog.show(getSupportFragmentManager(), "startDatePicker");
    }

    @OnClick(R.id.text_view_term_editor_end_date_value)
    void endDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mEndDate, endDate);
        dateDialog.show(getSupportFragmentManager(), "endDatePicker");
    }

    @OnClick(R.id.text_view_term_editor_end_date)
    void endDateLabelClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mEndDate, endDate);
        dateDialog.show(getSupportFragmentManager(), "endDatePicker");
    }

    private static class State {
        String title;
        String startDate;
        String endDate;
    }

}
