package edu.wgu.grimes.c196pa;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen conf = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.BLACK)
                .withHeaderText("\nGrimes | C-196 Performance Assessment")
                .withFooterText("")
                .withBeforeLogoText("WGU Student\nScheduler" +
                        "\n"
                )
                .withAfterLogoText(
                        "\n" +
                        "Chris Grimes\n" +
                        "Student ID: 000981634\n" +
                        "Software Development (Oct 1, 2018)\n" +
                        "JoAnne McDermand\n" +
                        "cgrim29@wgu.edu")
                .withLogo(R.mipmap.ic_launcher_round);

        TextView beforeLogo = conf.getBeforeLogoTextView();
        beforeLogo.setTextSize(32);
        beforeLogo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        beforeLogo.setTextColor(Color.WHITE);
        conf.getHeaderTextView().setTextColor(Color.WHITE);
        conf.getAfterLogoTextView().setTextColor(Color.WHITE);

        View view = conf.create();
        setContentView(view);
    }
}
