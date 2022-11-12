package com.example.shuttlemobile.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.route.Route;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DriverRideHistoryItemActivity extends AppCompatActivity {
    private Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ride_history_item_activity);

        Intent in = getIntent();
        ride = (Ride)in.getSerializableExtra("ride");

        applyDataToLayout();

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

    private void applyDataToLayout() {
        TextView begin = findViewById(R.id.driver_ride_history_item_begin);
        TextView end = findViewById(R.id.driver_ride_history_item_end);
        TextView dist = findViewById(R.id.driver_ride_history_item_distance);
        TextView price = findViewById(R.id.driver_ride_history_item_price);
        TextView rating = findViewById(R.id.driver_ride_history_item_rating);
        TextView comment = findViewById(R.id.driver_ride_history_item_comment);
        Button chat = findViewById(R.id.driver_ride_history_item_inbox);
        ListView routes = findViewById(R.id.driver_ride_history_item_routes);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");

        int rating_grade = /* ratings.of(ride).rating */ 5;
        String rating_comment = /* ratings.of(ride).comment */ "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        Object chat_ref = null;

        begin.setText(ride.getStart().format(fmt));
        end.setText(ride.getFinish().format(fmt));
        dist.setText(Double.toString(ride.getDistancePassed()) + "km");
        price.setText(Double.toString(ride.getPrice()) + " RSD");
        rating.setText(Integer.toString(rating_grade) + "/5");
        comment.setText(rating_comment);


        // TODO: Passengers.

        if (chat_ref == null) {
            chat.setEnabled(false);
        } else {
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}