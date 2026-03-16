package com.example.mad2.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad2.R;

public class ViewDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);

        TextView txtWelcome = findViewById(R.id.textViewWelcome);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        if (userName != null) {
            txtWelcome.setText("Xin chào: " + userName);
        }

        Toast.makeText(ViewDemo.this, "Welcome, " + userName, Toast.LENGTH_SHORT).show();
    }
}