//*********************************************************************************
//  File:             MentorEditorActivity.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************

package edu.wgu.grimes.c196pa.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;
import edu.wgu.grimes.c196pa.viewmodels.MentorEditorViewModel;

import static edu.wgu.grimes.c196pa.utilities.Constants.COURSE_ID_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.MENTOR_ID_KEY;

public class MentorEditorActivity extends AbstractEditorActivity {

    @BindView(R.id.edit_text_mentor_editor_first_name)
    EditText mFirstName;
    @BindView(R.id.edit_text_mentor_editor_last_name)
    EditText mLastName;
    @BindView(R.id.edit_text_mentor_editor_phone)
    EditText mPhone;
    @BindView(R.id.edit_text_mentor_editor_email)
    EditText mEmail;
    private MentorEditorViewModel mViewModel;

    private State state = new State();
    private static class State {
        String firstName;
        String lastName;
        String phoneNumber;
        String email;
    }

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

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            state.firstName = savedInstanceState.getString(getString(R.string.MENTOR_FIRST_NAME_KEY));
            state.lastName = savedInstanceState.getString(getString(R.string.MENTOR_LAST_NAME_KEY));
            state.phoneNumber = savedInstanceState.getString(getString(R.string.MENTOR_PHONE_NUMBER_KEY));
            state.email = savedInstanceState.getString(getString(R.string.MENTOR_EMAIL_KEY));
            loadState();
        }
    }

    private void loadState() {
        mFirstName.setText(state.firstName);
        mLastName.setText(state.lastName);
        mPhone.setText(state.phoneNumber);
        mEmail.setText(state.email);
    }

    @Override
    protected void saveState(Bundle outState) {
        outState.putString(getString(R.string.MENTOR_FIRST_NAME_KEY), String.valueOf(mFirstName.getText()));
        outState.putString(getString(R.string.MENTOR_LAST_NAME_KEY), String.valueOf(mLastName.getText()));
        outState.putString(getString(R.string.MENTOR_PHONE_NUMBER_KEY), String.valueOf(mPhone.getText()));
        outState.putString(getString(R.string.MENTOR_EMAIL_KEY), String.valueOf(mEmail.getText()));
    }

    protected void save() {
        String firstName = String.valueOf(mFirstName.getText());
        String lastName = String.valueOf(mLastName.getText());
        String phone = String.valueOf(mPhone.getText());
        String email = String.valueOf(mEmail.getText());
        int courseId = mParentId;

        if (firstName.trim().isEmpty()) {
            showValidationError("Missing first name", "Please enter a first name");
            return;
        }
        mViewModel.saveMentor(courseId, firstName, lastName, phone, email);
        Toast.makeText(MentorEditorActivity.this, firstName + " " + lastName + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    protected void delete() {
        MentorEntity mentor = mViewModel.mLiveMentor.getValue();
        String mentorName = mentor.getFirstName() + " " + mentor.getLastName();
        mViewModel.deleteMentor();
        String text = mentorName + " Deleted";
        Toast.makeText(MentorEditorActivity.this, text, Toast.LENGTH_SHORT).show();
        closeActivity();
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
                mFirstName.setText(state.firstName == null ? mentor.getFirstName() : state.firstName);
                mLastName.setText(state.lastName == null ? mentor.getLastName() : state.lastName);
                mPhone.setText(state.phoneNumber == null ? mentor.getPhoneNumber() : state.phoneNumber);
                mEmail.setText(state.email == null ? mentor.getEmail() : state.email);
            }
        });

        Bundle extras = getIntent().getExtras();
        mParentId = extras.getInt(COURSE_ID_KEY);

        if (extras.getInt(MENTOR_ID_KEY) == 0) {
            setTitle("New Course Mentor");
            mNew = true;
        } else {
            setTitle("Edit Course Mentor");
            mId = extras.getInt(MENTOR_ID_KEY);
            mViewModel.loadMentor(mId);
        }
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return null; // noop
    }
}
