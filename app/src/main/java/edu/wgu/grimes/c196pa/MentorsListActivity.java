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
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.MentorsListViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.MentorAdapter;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.MENTOR_ID_KEY;

public class MentorsListActivity extends AbstractListActivity {

    @BindView(R.id.recycler_view_mentors_list)
    RecyclerView mRecyclerView;
    private MentorsListViewModel mViewModel;
    private int mCourseId;
    private MentorAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_mentors_list;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Course Mentors");
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

    protected void initRecyclerView() {
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

    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(MentorsListViewModel.class);
        Bundle extras = getIntent().getExtras();
        mCourseId = extras.getInt(COURSE_ID_KEY);
        mViewModel.loadCourseMentors(mCourseId);
        mViewModel.getCourseMentors().observe(this, mentors -> {
            mAdapter.submitList(mentors);
//           StyleableToast.makeText(NotesListActivity.this, "notes changed", R.style.toast_message).show();
        });
    }

    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        MentorEntity mentor = mAdapter.getMentorAt(viewHolder.getAdapterPosition());
        String mentorName = mentor.getFirstName() + " " + mentor.getLastName();

        mViewModel.deleteMentor(mentor);
        String text = mentorName + " Deleted";
        StyleableToast.makeText(MentorsListActivity.this, text, R.style.toast_message).show();
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
