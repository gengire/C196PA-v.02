//*********************************************************************************
//  File:             NoteEditorActivity.java
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.database.entities.NoteEntity;
import edu.wgu.grimes.c868pa.utilities.Constants;
import edu.wgu.grimes.c868pa.viewmodels.NoteEditorViewModel;

import static edu.wgu.grimes.c868pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c868pa.utilities.Constants.NOTE_ID_KEY;

/**
 * Note Editor Activity, responsible for controlling both new and edit modes for course notes
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class NoteEditorActivity extends AbstractEditorActivity {

    /**
     * Local View Model for the note editor
     */
    private NoteEditorViewModel mViewModel;
    /**
     * Local internal state for this activity
     */
    private final State state = new State();

    @BindView(R.id.edit_text_note_editor_title)
    EditText mTitle;
    @BindView(R.id.edit_text_note_editor_description)
    EditText mDescription;
    @BindView(R.id.btn_open_send_email)
    Button mSendEmail;

    /**
     * Loads the data from the internal state to the screen
     * @param title The title of the note
     * @param description The description of the note
     */
    private void loadState(String title, String description) {
        mTitle.setText(title);
        mDescription.setText(description);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_note_editor;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_note_editor;
    }

    @Override
    protected int getSaveMenuItem() {
        return R.id.save_note;
    }

    @Override
    protected int getDeleteMenuItem() {
        return R.id.delete_note;
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            state.title = savedInstanceState.getString(getString(R.string.NOTE_TITLE_KEY));
            state.description = savedInstanceState.getString(getString(R.string.NOTE_DESCRIPTION_KEY));
            loadState(state.title, state.description);
        }
    }

    @Override
    protected void saveState(Bundle outState) {
        outState.putString(getString(R.string.NOTE_TITLE_KEY), String.valueOf(mTitle.getText()));
        outState.putString(getString(R.string.NOTE_DESCRIPTION_KEY), String.valueOf(mDescription.getText()));
    }

    @Override
    protected void save() {
        String title = String.valueOf(mTitle.getText());
        String description = String.valueOf( mDescription.getText());
        int courseId = mParentId;

        if (title.trim().isEmpty()) {
            showValidationError("Missing title", "Please enter a title");
            return;
        }
        mViewModel.saveNote(courseId, title, description);
        Toast.makeText(NoteEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @Override
    protected void delete() {
        NoteEntity course = mViewModel.mLiveNote.getValue();
        String title = course.getTitle();
        mViewModel.deleteNote();
        String text = title + " Deleted";
        Toast.makeText(NoteEditorActivity.this, text, Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(NoteEditorViewModel.class);
        mViewModel.mLiveNote.observe(this, (note) -> {
            if (note != null) {
                mTitle.setText(state.title == null ? note.getTitle() : state.title);
                mDescription.setText(state.description == null ? note.getDescription() : state.description);
            }
        });

        Bundle extras = getIntent().getExtras();
        mParentId = extras.getInt(COURSE_ID_KEY);

        if (extras.getInt(NOTE_ID_KEY) == 0) {
            setTitle("New Course Note");
            mNew = true;
            mSendEmail.setVisibility(View.GONE);
        } else {
            setTitle("Edit Course Note");
            mId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadNote(mId);
        }
    }

    @OnClick(R.id.btn_open_send_email)
    protected void handleOpenSendEmailClick() {
        Intent intent = new Intent(this, SendEmailActivity.class);
        intent.putExtra(Constants.EMAIL_SUBJECT_KEY, "Course notes: " + mTitle.getText());
        intent.putExtra(Constants.EMAIL_MESSAGE_KEY, String.valueOf(mDescription.getText()));
        openActivity(intent);
    }

    private static class State {
        String title;
        String description;
    }
}
