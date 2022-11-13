package com.example.shuttlemobile.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.inbox.ChatActivity;
import com.example.shuttlemobile.passenger.Passenger;
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
        //TextView rating = findViewById(R.id.driver_ride_history_item_rating);
        //TextView comment = findViewById(R.id.driver_ride_history_item_comment);
        Button openReviews = findViewById(R.id.driver_ride_history_item_open_ratings);
        Button chat = findViewById(R.id.driver_ride_history_item_inbox);
        ListView routes = findViewById(R.id.driver_ride_history_item_routes);
        ListView passengers = findViewById(R.id.driver_ride_history_item_passengers);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");

        double review_average = 5;
        String rating_comment = /* ratings.of(ride).comment */ "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        Object chat_ref = new String("Not null!!!");

        begin.setText(ride.getStart().format(fmt));
        end.setText(ride.getFinish().format(fmt));
        dist.setText(Double.toString(ride.getDistancePassed()) + "km");
        price.setText(Double.toString(ride.getPrice()) + " RSD");

        if (review_average == 0) {
            openReviews.setText(R.string.rideNoReviews);
            openReviews.setEnabled(false);
        } else {
            openReviews.setText(R.string.openReviews);
            openReviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getApplicationContext(), DriverReviewActivity.class);
                    in.putExtra("ride", ride);
                    startActivity(in);
                }
            });
        }

        //rating.setText(Integer.toString(rating_grade) + "/5");
        //comment.setText(rating_comment);



        passengers.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return ride.getPassengers().size();
            }

            @Override
            public Object getItem(int i) {
                return ride.getPassengers().get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View vi;
                if (view == null) {
                    vi = DriverRideHistoryItemActivity.this.getLayoutInflater().inflate(R.layout.list_passengers, null);
                } else {
                    vi = view;
                }

                Passenger obj = (Passenger)getItem(i);

                ImageView pfp = vi.findViewById(R.id.list_passenger_pfp);
                TextView name = vi.findViewById(R.id.list_passenger_name);
                // pfp.setImageResource();  // TODO
                name.setText("Name Surname"); // TODO

                return vi;
            }
        });

        passengers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Passenger obj = (Passenger)passengers.getItemAtPosition(i);
                Intent in = new Intent(getApplicationContext(), DriverPassengerInfoActivity.class);
                in.putExtra("passenger", obj);
                startActivity(in);
            }
        });

        if (chat_ref == null) {
            chat.setEnabled(false);
        } else {
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DriverRideHistoryItemActivity.this, ChatActivity.class);
                    Bundle b = new Bundle();
                    b.putStringArray("messages", getResources().getStringArray(R.array.messages));
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }

        routes.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return ride.getRoutes().size();
            }

            @Override
            public Object getItem(int i) {
                return ride.getRoutes().get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View vi;
                if (view == null) {
                    vi = DriverRideHistoryItemActivity.this.getLayoutInflater().inflate(R.layout.list_route_string, null);
                } else {
                    vi = view;
                }

                Route route = (Route)getItem(i);
                TextView strVal = vi.findViewById(R.id.route_string);
                strVal.setText(route.getPlaceName());

                return vi;
            }
        });
    }
}