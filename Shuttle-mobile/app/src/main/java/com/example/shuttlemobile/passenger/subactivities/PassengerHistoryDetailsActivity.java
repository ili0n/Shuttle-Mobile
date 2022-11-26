package com.example.shuttlemobile.passenger.subactivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.common.SimpleToolbarActivity;
import com.example.shuttlemobile.ride.Ride;

public class PassengerHistoryDetailsActivity extends SimpleToolbarActivity {
    protected SessionContext session;
    protected Ride ride;

    public static final String PARAM_SESSION = "session";
    public static final String PARAM_RIDE = "ride";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_history_details);

        initParams();
    }

    private void initParams() {
        Intent intent = getIntent();
        session = (SessionContext) intent.getSerializableExtra(PARAM_SESSION);
        ride = (Ride)intent.getSerializableExtra(PARAM_RIDE);
    }
}