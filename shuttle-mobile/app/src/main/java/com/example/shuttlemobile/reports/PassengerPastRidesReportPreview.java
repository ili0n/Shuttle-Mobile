package com.example.shuttlemobile.reports;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shuttlemobile.R;

public class PassengerPastRidesReportPreview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_past_rides_report_preview);

        String startString = getIntent().getStringExtra("startDateTime");
        String endString  = getIntent().getStringExtra("endDateTime");
    }
}