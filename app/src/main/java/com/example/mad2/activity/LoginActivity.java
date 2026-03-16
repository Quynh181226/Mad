package com.example.mad2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mad2.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private CheckBox chkRemember;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        chkRemember = findViewById(R.id.chkRemember);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            edtUsername.setText(savedUsername);
            edtPassword.setText(savedPassword);
            chkRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(LoginActivity.this,
                            "Vui lòng kết nối internet để đăng nhập",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    edtUsername.setError("Vui lòng nhập tên đăng nhập");
                    return;
                }
                if (password.isEmpty()) {
                    edtPassword.setError("Vui lòng nhập mật khẩu");
                    return;
                }

                if (username.equals("admin") && password.equals("123456")) {
                    if (chkRemember.isChecked()) {
                        sharedPreferences.edit()
                                .putString("username", username)
                                .putString("password", password)
                                .apply();
                    } else {
                        sharedPreferences.edit()
                                .remove("username")
                                .remove("password")
                                .apply();
                    }

                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply();

                    Toast.makeText(LoginActivity.this,
                            "Xin chào, " + username + "!",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Sai tên đăng nhập hoặc mật khẩu",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}