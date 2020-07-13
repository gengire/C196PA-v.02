package edu.wgu.grimes.c868pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c868pa.App;
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.utilities.HashingUtil;
import edu.wgu.grimes.c868pa.viewmodels.LoginViewModel;

/**
 * Login Activity, responsible for authenticating the user credentials
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Local view model for the login activity
     */
    private LoginViewModel mViewModel;

    @BindView(R.id.edit_text_user_name)
    protected EditText mUsername;

    @BindView(R.id.edit_text_password)
    protected EditText mPassword;

    @BindView(R.id.btn_login)
    protected Button mLogin;

    @BindView(R.id.text_view_register)
    protected TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initViewModel();
        Log.d("LOGIN", "LoginActivity:onCreate complete");
}

    private void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    @OnClick(R.id.text_view_register)
    protected void onRegisterClick() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_login)
    protected void onLoginClick() {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        Log.d("LOGIN", "user: " + username + " attempting to login with password: " + password);
        mViewModel.getAccount(username, account -> {
            if (account != null) {
                Log.d("LOGIN", "verified active account, verifying password");
                if (password != null && !"".equals(password.trim())) {
                    boolean loginSucceeded = HashingUtil.validatePassword(password, account.getPassword());
                    if (loginSucceeded) {
                        Log.d("LOGIN", "password verified, logging into the system");
                        mViewModel.setLoggedInAccountId(account.getId());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.d("LOGIN", "the password given was incorrect: " + password);
                        showToast("Login failed");
                    }
                } else {
                    Log.d("LOGIN", "blank password, failing login");
                    showToast("Login failed");
                }
            } else {
                Log.d("LOGIN", "the username given was not valid");
                showToast("Login failed");
            }
        });
    }

    private void showToast(final String toastMessage) {
        LoginActivity.this.runOnUiThread(() ->
            Toast.makeText(LoginActivity.this, toastMessage, Toast.LENGTH_SHORT).show());
    }
}