package com.example.shuttlemobile.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.ride.Ride;

public class DriverRideHistoryItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ride_history_item_activity);

        Intent in = getIntent();
        Ride ride = (Ride)in.getSerializableExtra("ride");
        String hi = in.getStringExtra("ride");

        TextView test = findViewById(R.id.driver_ride_history_item_time);
        test.setText(Double.toString(ride.getPrice()));

        Toolbar toolbar = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(R.string.titleHistory);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}