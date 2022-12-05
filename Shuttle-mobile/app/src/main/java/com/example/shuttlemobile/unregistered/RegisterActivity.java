package com.example.shuttlemobile.unregistered;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.SimpleToolbarActivity;

public class RegisterActivity extends SimpleToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}