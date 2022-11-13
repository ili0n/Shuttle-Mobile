package com.example.shuttlemobile.passenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.shuttlemobile.R;

public class PassengerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_register);
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(view -> onRegisterClick());
    }

    private void onRegisterClick() {
        startActivity(new Intent(this, PassengerMainActivity.class));
        finish();
    }
}