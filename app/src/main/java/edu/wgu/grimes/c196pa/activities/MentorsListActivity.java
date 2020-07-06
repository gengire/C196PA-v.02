//*********************************************************************************
//  File:             MentorsListActivity.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************

package edu.wgu.grimes.c196pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.adapters.MentorAdapter;
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.MentorsListViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.MENTOR_ID_KEY;

/**
 * Mentors List Activity, responsible for controlling the mentors list
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class MentorsListActivity extends AbstractListActivity {

    /**
     * Local View Model for the mentors list
     */
    private MentorsListViewModel mViewModel;

    /**
     * Adapter for the mentors in the recycler view
     */
    private MentorAdapter mAdapter;

    @BindView(R.id.recycler_view_mentors_list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Course Mentors");
        FloatingActionButton fab = findViewById(R.id.fab_add_mentor);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MentorsListActivity.this, MentorEditorActivity.class);
            intent.putExtra(Constants.COURSE_ID_KEY, mParentId);
            openActivity(intent);
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mentors_list;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MentorAdapter();

        mAdapter.setOnItemClickListener(mentor -> {
            Intent intent = new Intent(MentorsListActivity.this, MentorEditorActivity.class);
            intent.putExtra(MENTOR_ID_KEY, mentor.getId());
            intent.putExtra(COURSE_ID_KEY, mParentId);
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(MentorsListViewModel.class);
        Bundle extras = getIntent().getExtras();
        mParentId = extras.getInt(COURSE_ID_KEY);
        mViewModel.loadCourseMentors(mParentId);
        mViewModel.getCourseMentors().observe(this, mentors -> mAdapter.submitList(mentors));
    }

    @Override
    protected void onSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        MentorEntity mentor = mAdapter.getMentorAt(viewHolder.getAdapterPosition());
        String mentorName = mentor.getFirstName() + " " + mentor.getLastName();

        mViewModel.deleteMentor(mentor);
        String text = mentorName + " Deleted";
        Toast.makeText(MentorsListActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSwipeDeleteCancel(RecyclerView.ViewHolder viewHolder) {
        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
