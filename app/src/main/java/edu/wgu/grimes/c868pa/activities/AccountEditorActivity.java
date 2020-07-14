//*********************************************************************************
//  File:             AccountEditorActivity.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.database.entities.AccountEntity;
import edu.wgu.grimes.c868pa.viewmodels.AccountEditorViewModel;

import static edu.wgu.grimes.c868pa.utilities.Constants.ACCOUNT_ID_KEY;

/**
 * Account Editor Activity, responsible for controlling both new and edit modes for accounts
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AccountEditorActivity extends AbstractEditorActivity {

    /**
     * Local View Model for the account editor
     */
    private AccountEditorViewModel mViewModel;

    @BindView(R.id.edit_text_account_editor_username)
    EditText mUsername;

    @BindView(R.id.edit_text_account_editor_password)
    EditText mPassword;

    /**
     * Local internal state for this activity
     */
    private final AccountState state = new AccountState();

    /**
     * Loads the data from the internal state to the screen
     */
    private void loadState() {
        mUsername.setText(state.username);
        mPassword.setText(state.password);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_account_editor;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(AccountEditorViewModel.class);

        // update the view when the model is changed
        mViewModel.mLiveData.observe(this, (account) -> {
            if (account != null) {
                mUsername.setText(state.username == null ? account.getUsername() : state.username);
                mUsername.setText(state.password == null ? account.getPassword() : state.password);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_account));
            mNew = true;
        } else {
            setTitle(getString(R.string.edit_account));
            mId = extras.getInt(ACCOUNT_ID_KEY);
            mViewModel.loadAccount(mId);
        }
    }

    @Override
    protected void save() {
        String username = String.valueOf(mUsername.getText());
        String password = String.valueOf(mPassword.getText());
        if (username.trim().isEmpty()) {
            showValidationError("Missing Username", "Please enter a username");
            return;
        }
        mViewModel.saveAccount(username, password);
        String toastMessage = username + " saved";
        showToast(toastMessage);
        closeActivity();
    }

    @Override
    protected void delete() {
        AccountEntity account = mViewModel.mLiveData.getValue();
        String accountUsername = account == null ? "<NA>" : account.getUsername();
        mViewModel.validateDeleteAccount(account,
                () -> { // success
                    mViewModel.deleteAccount();
                    String toastMessage = accountUsername + " Deleted";
                    showToast(toastMessage);
                    closeActivity();
                }, () -> { // failure
                    String text = accountUsername + " can't be deleted because...";
                    showValidationError("Can't delete", text);
                });
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            state.username = savedInstanceState.getString(getString(R.string.ACCOUNT_USERNAME_KEY));
            state.password = savedInstanceState.getString(getString(R.string.ACCOUNT_PASSWORD_KEY));
            loadState();
        }
    }

    @Override
    protected void saveState(@NonNull Bundle outState) {
        outState.putString(getString(R.string.ACCOUNT_USERNAME_KEY), String.valueOf(mUsername.getText()));
        outState.putString(getString(R.string.ACCOUNT_PASSWORD_KEY), String.valueOf(mPassword.getText()));
    }

    @Override
    protected int getDeleteMenuItem() {
        return R.id.delete_account;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_account_editor;
    }

    @Override
    protected int getSaveMenuItem() {
        return R.id.save_account;
    }

    private static class AccountState {
        String username;
        String password;
    }

}