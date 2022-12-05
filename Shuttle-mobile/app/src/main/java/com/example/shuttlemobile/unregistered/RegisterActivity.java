package com.example.shuttlemobile.unregistered;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toolbar;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.SimpleToolbarActivity;
import com.google.android.material.appbar.AppBarLayout;

public class RegisterActivity extends SimpleToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}