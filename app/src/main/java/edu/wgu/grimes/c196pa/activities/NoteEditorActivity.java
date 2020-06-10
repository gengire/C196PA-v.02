package edu.wgu.grimes.c196pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.muddzdev.styleabletoast.StyleableToast;

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

    @OnClick(R.id.btn_open_send_email)
    void handleOpenSendEmailClick() {
        Intent intent = new Intent(this, SendEmailActivity.class);
        intent.putExtra(Constants.EMAIL_SUBJECT_KEY, "Course notes: " + mTitle.getText().toString());
        intent.putExtra(Constants.EMAIL_MESSAGE_KEY, mDescription.getText().toString());
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

    protected void save() {
        String title = mTitle.getText().toString();
        String description = mDescription.getText().toString();
        int courseId = mParentId;

        if (title.trim().isEmpty()) {
            StyleableToast.makeText(NoteEditorActivity.this, "Please enter a title", R.style.toast_validation_failure).show();
            return;
        }
        mViewModel.saveNote(courseId, title, description);
        StyleableToast.makeText(NoteEditorActivity.this, title + " saved", R.style.toast_message).show();
        closeActivity();
    }

    protected void delete() {
        NoteEntity course = mViewModel.mLiveNote.getValue();
        String title = course.getTitle();
        mViewModel.deleteNote();
        String text = title + " Deleted";
        StyleableToast.makeText(NoteEditorActivity.this, text, R.style.toast_message).show();
        closeActivity();
    }

    protected void initRecyclerView() {
        // noop
    }

    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(NoteEditorViewModel.class);
        mViewModel.mLiveNote.observe(this, (note) -> {
            if (note != null) {
                if (!mEditing) {
                    mTitle.setText(note.getTitle());
                    mDescription.setText(note.getDescription());
                }
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
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    @Override
    protected void onSwipeCancel(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return null; // noop
    }
}
