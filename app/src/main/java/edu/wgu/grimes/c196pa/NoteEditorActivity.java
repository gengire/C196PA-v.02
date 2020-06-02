package edu.wgu.grimes.c196pa;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.muddzdev.styleabletoast.StyleableToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.viewmodels.NoteEditorViewModel;
import edu.wgu.grimes.c196pa.viewmodels.TermEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.NOTE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.TERM_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class NoteEditorActivity extends AppCompatActivity {

    private NoteEditorViewModel mViewModel;

    @BindView(R.id.edit_text_note_editor_title)
    EditText mTitle;

    @BindView(R.id.edit_text_note_editor_description)
    EditText mDescription;

    private boolean mNewNote;
    private boolean mEditing;

    private int mNoteId;
    private int mCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViewModel();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = mTitle.getText().toString();
        String description = mDescription.getText().toString();
        int courseId = mCourseId;

        if (title.trim().isEmpty()) {
            StyleableToast.makeText(NoteEditorActivity.this, "Please enter a title", R.style.toast_validation_failure).show();
            return;
        }
        mViewModel.saveNote(courseId, title, description);
        StyleableToast.makeText(NoteEditorActivity.this, "course: " + courseId + ": " + title + " saved", R.style.toast_message).show();
        finish();
    }

    private void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
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
        mCourseId = extras.getInt(COURSE_ID_KEY);

        if (extras.getInt(NOTE_ID_KEY) == 0) {
            setTitle("New Note");
            mNewNote = true;
        } else {
            setTitle("Edit Note");
            mNoteId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadNote(mNoteId);
        }
    }

}
