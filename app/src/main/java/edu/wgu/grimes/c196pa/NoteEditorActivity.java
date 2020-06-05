package edu.wgu.grimes.c196pa;

import android.os.Bundle;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.muddzdev.styleabletoast.StyleableToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;
import edu.wgu.grimes.c196pa.viewmodels.NoteEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.NOTE_ID_KEY;

public class NoteEditorActivity extends AbstractActivity {

    @BindView(R.id.edit_text_note_editor_title)
    EditText mTitle;
    @BindView(R.id.edit_text_note_editor_description)
    EditText mDescription;
    private NoteEditorViewModel mViewModel;

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
        finish();
    }

    protected void delete() {
        NoteEntity course = mViewModel.mLiveNote.getValue();
        String title = course.getTitle();
        mViewModel.deleteNote();
        String text = title + " Deleted";
        StyleableToast.makeText(NoteEditorActivity.this, text, R.style.toast_message).show();
        finish();
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
