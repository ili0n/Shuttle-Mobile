package com.example.shuttlemobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shuttlemobile.R;

public class UserLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Button btnLogin = findViewById(R.id.login_confirm);
        Button btnRegister = findViewById(R.id.login_register);

        btnLogin.setOnClickListener(view -> onLoginClick());
        btnRegister.setOnClickListener(view -> onRegisterClick());
    }

    private void onLoginClick() {
        Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
    }

    private void onRegisterClick() {
        Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
    }
}