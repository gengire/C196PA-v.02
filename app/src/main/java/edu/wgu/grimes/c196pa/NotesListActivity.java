package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.NotesListViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.NoteAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.NOTE_ID_KEY;

public class NotesListActivity extends AbstractListActivity {

    @BindView(R.id.recycler_view_notes_list)
    RecyclerView mRecyclerView;
    private NotesListViewModel mViewModel;
    private int mCourseId;
    private NoteAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_notes_list;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Course Notes");
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

    protected void initRecyclerView() {
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

    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(NotesListViewModel.class);
        Bundle extras = getIntent().getExtras();
        mCourseId = extras.getInt(COURSE_ID_KEY);
        mViewModel.loadCoursesNotes(mCourseId);
        mViewModel.getCourseNotes().observe(this, notes -> {
            mAdapter.submitList(notes);
//           StyleableToast.makeText(NotesListActivity.this, "notes changed", R.style.toast_message).show();
        });
    }

    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        NoteEntity note = mAdapter.getNoteAt(viewHolder.getAdapterPosition());
        String noteTitle = note.getTitle();

        mViewModel.deleteNote(note);
        String text = noteTitle + " Deleted";
        StyleableToast.makeText(NotesListActivity.this, text, R.style.toast_message).show();
    }

    @Override
    protected void onSwipeCancel(RecyclerView.ViewHolder viewHolder) {
        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
