package com.example.shuttlemobile.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.inbox.ChatActivity;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;

// Contains all information about a passenger that the driver can view.
public class DriverPassengerInfoActivity extends AppCompatActivity {
    private Passenger passenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_passenger_info);

        Intent in = getIntent();
        passenger = (Passenger)in.getSerializableExtra("passenger");

        Toolbar toolbar = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Passenger");

        Button chat = findViewById(R.id.driver_passenger_info_chatbtn);
        Object chat_ref = new String("Not null.");
        if (chat_ref == null) {
            chat.setEnabled(false);
        } else {
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DriverPassengerInfoActivity.this, ChatActivity.class);
                    Bundle b = new Bundle();
                    b.putStringArray("messages", getResources().getStringArray(R.array.messages));
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }
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