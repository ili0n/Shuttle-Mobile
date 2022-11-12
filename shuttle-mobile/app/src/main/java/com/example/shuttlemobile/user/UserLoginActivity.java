package com.example.shuttlemobile.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.driver.DriverMainActivity;
import com.example.shuttlemobile.inbox.ChatActivity;
import com.example.shuttlemobile.inbox.InboxActivity;

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
        String email = ((EditText) findViewById(R.id.login_email)).getText().toString();
        if (email.compareTo("driver") == 0) {
            startActivity(new Intent(this, DriverMainActivity.class));
        } else {
            startActivity(new Intent(this, InboxActivity.class));
        }
    }

    private void onRegisterClick() {
        Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
    }
}