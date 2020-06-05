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
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.MentorsListViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.MentorAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.MENTOR_ID_KEY;

public class MentorsListActivity extends AppCompatActivity {

    private MentorsListViewModel mViewModel;
    private int mCourseId;

    @BindView(R.id.recycler_view_mentors_list)
    RecyclerView mRecyclerView;
    private MentorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Course Notes");

        ButterKnife.bind(this);

        initRecyclerView();
        initViewModel();

        FloatingActionButton fab = findViewById(R.id.fab_add_mentor);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MentorsListActivity.this, MentorEditorActivity.class);
                intent.putExtra(Constants.COURSE_ID_KEY, mCourseId);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MentorAdapter();

        mAdapter.setOnItemClickListener(mentor -> {
            Intent intent = new Intent(MentorsListActivity.this, MentorEditorActivity.class);
            intent.putExtra(MENTOR_ID_KEY, mentor.getId());
            intent.putExtra(COURSE_ID_KEY, mCourseId);
            startActivity(intent);
//            StyleableToast.makeText(NotesListActivity.this, "note: " + note.getTitle() + " clicked", R.style.toast_message).show();
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    private void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(MentorsListViewModel.class);

        Bundle extras = getIntent().getExtras();
        mCourseId = extras.getInt(COURSE_ID_KEY);
        mViewModel.loadCourseMentors(mCourseId);
        mViewModel.getCourseMentors().observe(this, mentors -> {
            mAdapter.submitList(mentors);
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
                MentorEntity mentor = mAdapter.getMentorAt(viewHolder.getAdapterPosition());
                String mentorName = mentor.getFirstName() + " " + mentor.getLastName();

                mViewModel.deleteMentor(mentor);
                String text = mentorName + " Deleted";
                StyleableToast.makeText(MentorsListActivity.this, text, R.style.toast_message).show();
            }
        }).attachToRecyclerView(mRecyclerView);
    }

}
