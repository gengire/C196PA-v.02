//*********************************************************************************
//  File:             NotesListActivity.java
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
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.adapters.NoteAdapter;
import edu.wgu.grimes.c868pa.database.entities.NoteEntity;
import edu.wgu.grimes.c868pa.utilities.Constants;
import edu.wgu.grimes.c868pa.viewmodels.NotesListViewModel;

import static edu.wgu.grimes.c868pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c868pa.utilities.Constants.NOTE_ID_KEY;

/**
 * Notes Activity, responsible for controlling the notes list
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class NotesListActivity extends AbstractListActivity {

    /**
     * Local View Model for the notes list
     */
    private NotesListViewModel mViewModel;
    /**
     * Adapter for the notes in the recycler view
     */
    private NoteAdapter mAdapter;

    @BindView(R.id.recycler_view_notes_list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setTitle("Course Notes");
        FloatingActionButton mFab = findViewById(R.id.fab_add_note);
        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(NotesListActivity.this, NoteEditorActivity.class);
            intent.putExtra(Constants.COURSE_ID_KEY, mParentId);
            openActivity(intent);
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_notes_list;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NoteAdapter();

        mAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(NotesListActivity.this, NoteEditorActivity.class);
            intent.putExtra(NOTE_ID_KEY, note.getId());
            intent.putExtra(COURSE_ID_KEY, mParentId);
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(NotesListViewModel.class);
        Bundle extras = getIntent().getExtras();
        mParentId = extras.getInt(COURSE_ID_KEY);
        mViewModel.loadCoursesNotes(mParentId);
        mViewModel.getCourseNotes().observe(this, notes -> mAdapter.submitList(notes));
    }

    @Override
    protected void onSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        NoteEntity note = mAdapter.getNoteAt(viewHolder.getAdapterPosition());
        String noteTitle = note.getTitle();

        mViewModel.deleteNote(note);
        String toastMessage = noteTitle + " Deleted";
        showToast(toastMessage);
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
