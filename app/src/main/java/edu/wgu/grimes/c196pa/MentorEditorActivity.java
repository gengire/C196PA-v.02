package edu.wgu.grimes.c196pa;

import android.os.Bundle;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.muddzdev.styleabletoast.StyleableToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;
import edu.wgu.grimes.c196pa.viewmodels.MentorEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.MENTOR_ID_KEY;

public class MentorEditorActivity extends AbstractActivity {

    @BindView(R.id.edit_text_mentor_editor_first_name)
    EditText mFirstName;
    @BindView(R.id.edit_text_mentor_editor_last_name)
    EditText mLastName;
    @BindView(R.id.edit_text_mentor_editor_phone)
    EditText mPhone;
    @BindView(R.id.edit_text_mentor_editor_email)
    EditText mEmail;
    private MentorEditorViewModel mViewModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_mentor_editor;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_mentor_editor;
    }

    @Override
    protected int getSaveMenuItem() {
        return R.id.save_mentor;
    }

    @Override
    protected int getDeleteMenuItem() {
        return R.id.delete_mentor;
    }

    protected void save() {
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String phone = mPhone.getText().toString();
        String email = mEmail.getText().toString();
        int courseId = mParentId;

        if (firstName.trim().isEmpty()) {
            StyleableToast.makeText(MentorEditorActivity.this, "Please enter a first name", R.style.toast_validation_failure).show();
            return;
        }
        mViewModel.saveMentor(courseId, firstName, lastName, phone, email);
        StyleableToast.makeText(MentorEditorActivity.this, firstName + " " + lastName + " saved", R.style.toast_message).show();
        finish();
    }

    protected void delete() {
        MentorEntity mentor = mViewModel.mLiveMentor.getValue();
        String mentorName = mentor.getFirstName() + " " + mentor.getLastName();
        mViewModel.deleteMentor();
        String text = mentorName + " Deleted";
        StyleableToast.makeText(MentorEditorActivity.this, text, R.style.toast_message).show();
        finish();
    }

    @Override
    protected void initRecyclerView() {
        // noop
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(MentorEditorViewModel.class);
        mViewModel.mLiveMentor.observe(this, (mentor) -> {
            if (mentor != null) {
                if (!mEditing) {
                    mFirstName.setText(mentor.getFirstName());
                    mLastName.setText(mentor.getLastName());
                    mPhone.setText(mentor.getPhoneNumber());
                    mEmail.setText(mentor.getEmail());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        mParentId = extras.getInt(COURSE_ID_KEY);

        if (extras.getInt(MENTOR_ID_KEY) == 0) {
            setTitle("New Note");
            mNew = true;
        } else {
            setTitle("Edit Note");
            mId = extras.getInt(MENTOR_ID_KEY);
            mViewModel.loadMentor(mId);
        }
    }

    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return null; // noop
    }
}
