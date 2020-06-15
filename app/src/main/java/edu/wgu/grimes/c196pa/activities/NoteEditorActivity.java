package edu.wgu.grimes.c196pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.NoteEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.NOTE_ID_KEY;

public class NoteEditorActivity extends AbstractEditorActivity {

    @BindView(R.id.edit_text_note_editor_title)
    EditText mTitle;
    @BindView(R.id.edit_text_note_editor_description)
    EditText mDescription;
    @BindView(R.id.btn_open_send_email)
    Button mSendEmail;

    private NoteEditorViewModel mViewModel;

    private State state = new State();
    private static class State {
        String title;
        String description;
    }

    @OnClick(R.id.btn_open_send_email)
    void handleOpenSendEmailClick() {
        Intent intent = new Intent(this, SendEmailActivity.class);
        intent.putExtra(Constants.EMAIL_SUBJECT_KEY, "Course notes: " + mTitle.getText());
        intent.putExtra(Constants.EMAIL_MESSAGE_KEY, String.valueOf(mDescription.getText()));
        openActivity(intent);
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

    private void loadState(String title, String description) {
        mTitle.setText(title);
        mDescription.setText(description);
    }

    @Override
    protected void saveState(Bundle outState) {
        outState.putString(getString(R.string.NOTE_TITLE_KEY), String.valueOf(mTitle.getText()));
        outState.putString(getString(R.string.NOTE_DESCRIPTION_KEY), String.valueOf(mDescription.getText()));
    }

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

    protected void delete() {
        NoteEntity course = mViewModel.mLiveNote.getValue();
        String title = course.getTitle();
        mViewModel.deleteNote();
        String text = title + " Deleted";
        Toast.makeText(NoteEditorActivity.this, text, Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    protected void initRecyclerView() {
        // noop
    }

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

    @Override
    protected RecyclerView getRecyclerView() {
        return null; // noop
    }
}
