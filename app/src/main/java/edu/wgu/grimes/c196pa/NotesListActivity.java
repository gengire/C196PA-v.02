package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.muddzdev.styleabletoast.StyleableToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.NotesListViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.NoteAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.NOTE_ID_KEY;

public class NotesListActivity extends AppCompatActivity {

    private NotesListViewModel mViewModel;
    private int mCourseId;

    @BindView(R.id.recycler_view_notes_list)
    RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Course Notes");

        ButterKnife.bind(this);

        initRecyclerView();
        initViewModel();

        FloatingActionButton mFab = findViewById(R.id.fab_add_note);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesListActivity.this, NoteEditorActivity.class);
                intent.putExtra(Constants.COURSE_ID_KEY, mCourseId);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NoteAdapter();

        mAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(NotesListActivity.this, NoteEditorActivity.class);
            intent.putExtra(NOTE_ID_KEY, note.getId());
            intent.putExtra(COURSE_ID_KEY, mCourseId);
            startActivity(intent);
//            StyleableToast.makeText(NotesListActivity.this, "note: " + note.getTitle() + " clicked", R.style.toast_message).show();
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    private void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(NotesListViewModel.class);

        Bundle extras = getIntent().getExtras();
        mCourseId = extras.getInt(COURSE_ID_KEY);
        mViewModel.loadCoursesNotes(mCourseId);
        mViewModel.getCourseNotes().observe(this, notes -> {
           mAdapter.submitList(notes);
//           StyleableToast.makeText(NotesListActivity.this, "notes changed", R.style.toast_message).show();
        });
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
                NoteEntity note = mAdapter.getNoteAt(viewHolder.getAdapterPosition());
                String noteTitle = note.getTitle();

                mViewModel.deleteNote(note);
                String text = noteTitle + " Deleted";
                StyleableToast.makeText(NotesListActivity.this, text, R.style.toast_message).show();

            }
        }).attachToRecyclerView(mRecyclerView);
    }

}
