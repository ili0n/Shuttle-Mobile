package com.example.shuttlemobile.passenger;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shuttlemobile.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

public class PassengerRideHistoryActivity extends AppCompatActivity {
    ListView lvHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_ride_history);
        lvHistory = findViewById(R.id.passenger_history_list);

        ArrayList<Ride> rides = new ArrayList<Ride>();
        rides.add(new Ride(1, new Date(), new Date(), true, false, true, 100, 360));
        rides.add(new Ride(2, new Date(), new Date(), false, false, true, 100, 150));
        rides.add(new Ride(3, new Date(), new Date(), true, true, true, 100, 90));
        RideAdapter adapter = new RideAdapter(rides, PassengerRideHistoryActivity.this);
        lvHistory.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}