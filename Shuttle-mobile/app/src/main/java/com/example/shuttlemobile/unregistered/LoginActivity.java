package com.example.shuttlemobile.unregistered;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.driver.DriverActivity;
import com.example.shuttlemobile.driver.services.DriverMessageService;
import com.example.shuttlemobile.passenger.PassengerActivity;
import com.example.shuttlemobile.passenger.services.PassengerMessageService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initButtonCallbacks();

        // TODO: Remove this.
        Toast.makeText(this, "To login as driver enter 'driver' in email", Toast.LENGTH_SHORT).show();
    }

    private void initButtonCallbacks() {
        Button btnLogin = findViewById(R.id.btn_un_login);
        Button btnRegister = findViewById(R.id.btn_un_register);

        btnLogin.setOnClickListener(view -> login());
        btnRegister.setOnClickListener(view -> openRegistrationActivity());
    }

    private void login() {
        // TODO: Once we have real data, perform authorization.

        EditText txtEmail = findViewById(R.id.txt_un_email);
        EditText txtPassword = findViewById(R.id.txt_un_password);

        if (txtEmail.getText().toString().equals("driver")) {
            startActivity(new Intent(getApplicationContext(), DriverActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), PassengerActivity.class));
        }
    }

    private void openRegistrationActivity() {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
}