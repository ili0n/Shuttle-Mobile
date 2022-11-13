package com.example.shuttlemobile.route;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.ride.Ride;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class FavouriteRoutesActivity extends AppCompatActivity {

    private ListView lvFavouriteRoutes;
    private List<Route> favouriteRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_routes);

        addToolbar();

        favouriteRoutes = new ArrayList<Route>();
        addMockData();

        lvFavouriteRoutes = findViewById(R.id.lvFavouriteRoutes);
        lvFavouriteRoutes.setAdapter(new FavouriteRouteAdapter(favouriteRoutes, FavouriteRoutesActivity.this));

        lvFavouriteRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Route route = (Route) adapterView.getItemAtPosition(position);
                new AlertDialog.Builder(FavouriteRoutesActivity.this).setTitle("Do you want to reserve this route now?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Reserving logic
                            }
                        })
                        .setNegativeButton("No", null).show();
            }
        });
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setTitle("Favourite routes");
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

    private void addMockData() {
        favouriteRoutes.add(
                new Route(123, LocalDateTime.now(), LocalDateTime.now(),
                        LocalTime.now(ZoneId.systemDefault()), 123, 2,
                        new Location(123, 123), new Location(500, 500)));
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