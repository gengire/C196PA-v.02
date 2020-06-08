package edu.wgu.grimes.c196pa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.utilities.Constants;

import static edu.wgu.grimes.c196pa.utilities.Constants.EMAIL_MESSAGE_KEY;
import static edu.wgu.grimes.c196pa.utilities.Constants.EMAIL_SUBJECT_KEY;

public class SendEmailActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_send_email_text_to)
    EditText mTo;

    @BindView(R.id.edit_text_send_email_text_subject)
    EditText mSubject;

    @BindView(R.id.edit_text_send_email_text_message)
    EditText mMessage;

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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        if (R.menu.menu_send_email != 0) {
            inflater.inflate(R.menu.menu_send_email, menu);
        }
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

    private void initView() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mSubject.setText(extras.getString(EMAIL_SUBJECT_KEY));
            mMessage.setText(extras.getString(EMAIL_MESSAGE_KEY));
        }
    }

    @OnClick(R.id.btn_send_email)
    void handleSendEmailClick() {
        sendMail();
    }

    private void sendMail() {
        String recipientList = mTo.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = mSubject.getText().toString();
        String message = mMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}
