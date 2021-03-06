//*********************************************************************************
//  File:             SendEmailActivity.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.R;

import static edu.wgu.grimes.c196pa.utilities.Constants.EMAIL_MESSAGE_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.EMAIL_SUBJECT_KEY;

/**
 * Send Email Activity, responsible for capturing the notes data and pre-populating it into
 * editable fields to send to the email client.
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class SendEmailActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_send_email_text_to)
    EditText mTo;

    @BindView(R.id.edit_text_send_email_text_subject)
    EditText mSubject;

    @BindView(R.id.edit_text_send_electronic_mail_text_message)
    EditText mMessage;

    /**
     * @see AbstractActivity#initViewModel()
     */
    private void initView() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mSubject.setText(extras.getString(EMAIL_SUBJECT_KEY));
            mMessage.setText(extras.getString(EMAIL_MESSAGE_KEY));
        }
    }

    /**
     * Passes the data on the screen to the email client on the phone
     */
    private void sendMail() {
        String recipientList = String.valueOf(mTo.getText());
        String[] recipients = recipientList.split(",");

        if (recipientList.length() > 0) {
            String subject = String.valueOf(mSubject.getText());
            String message = String.valueOf(mMessage.getText());

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose an email client"));
        } else {
            showValidationError("Missing recipients", "Please enter at least one email recipient");
        }
    }

    /**
     * @param title The title of the email
     * @param message The message of the email
     * @see AbstractActivity#showValidationError(String, String)
     */
    private void showValidationError(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setCancelable(true)
                .setPositiveButton("Okay", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ButterKnife.bind(this);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_send_email, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();
        if (itemId == R.id.send_email) {
            sendMail();
            return true;
        } else if (itemId == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
