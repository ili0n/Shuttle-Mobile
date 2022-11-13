package com.example.shuttlemobile.route;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.shuttlemobile.R;

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

        favouriteRoutes = new ArrayList<Route>();
        favouriteRoutes.add(
                new Route(123, LocalDateTime.now(), LocalDateTime.now(),
                        LocalTime.now(ZoneId.systemDefault()),123, 2,
                        new Location(123, 123), new Location(500, 500)));

        lvFavouriteRoutes = findViewById(R.id.lvFavouriteRoutes);
        lvFavouriteRoutes.setAdapter(new FavouriteRouteAdapter(favouriteRoutes,FavouriteRoutesActivity.this));
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