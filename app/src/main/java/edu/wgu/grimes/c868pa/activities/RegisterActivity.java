package edu.wgu.grimes.c868pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.function.Consumer;

import javax.crypto.spec.PBEKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel mViewModel;

    @BindView(R.id.edit_text_user_name)
    protected EditText mUsername;

    @BindView(R.id.edit_text_password)
    protected EditText mPassword;

    @BindView(R.id.edit_text_confirm_password)
    protected EditText mConfirmPassword;

    @BindView(R.id.btn_register)
    protected Button mRegister;

    @BindView(R.id.text_view_login)
    protected TextView mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(RegisterViewModel.class);
    }

    @OnClick(R.id.btn_register)
    protected void onRegisterClick() {
        String username = mUsername.getText().toString();

        if (username == null || "".equals(username)) {
            showToast("Please provide a username");
        } else {
            mViewModel.userExists(username, exists -> {
                if (exists) {
                    showToast("Username already taken");
                } else {
                    String password = mPassword.getText().toString();
                    String confirmPassword = mConfirmPassword.getText().toString();
                    if (password == null || "".equals(password)) {
                        showToast("Please provide a password");
                    } else {
                        if (password.equals(confirmPassword)) {
                            mViewModel.createAccount(username, password);
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            showToast("Passwords must match");
                        }
                    }
                }
            });
        }

    }

    @OnClick(R.id.text_view_login)
    protected void onLoginClick() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void showToast(final String toastMessage) {
        RegisterActivity.this.runOnUiThread(() ->
                Toast.makeText(RegisterActivity.this, toastMessage, Toast.LENGTH_SHORT).show());
    }
}