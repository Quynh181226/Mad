package com.example.mad2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mad2.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

//        sharedPreferences.edit().clear().apply();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                Intent intent;
//                if (isFirstTime) {
//                    intent = new Intent(SplashActivity.this, WelcomeActivity.class);
//                } else
                if (!isLoggedIn) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}