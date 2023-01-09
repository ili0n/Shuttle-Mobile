package com.example.shuttlemobile.driver.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;
import com.mapbox.geojson.Point;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DriverHome extends GenericUserMapFragment {

    private ScheduledExecutorService executor;
    private LocalTime time = LocalTime.of(0, 0, 0);

    private ListView lvPassengers;
    private ListView lvLocations;
    private CheckBox cbBaby;
    private CheckBox cbPet;
    private TextView tvTime;
    private Button btnFinish;
    private Button btnPanic;

    private boolean initiallyMovedToLocation = false;
    private Point destination;
    private Point departure;
    private Ride currentRide;


    public static DriverHome newInstance(SessionContext session) {
        DriverHome fragment = new DriverHome();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: get from intent
        this.currentRide = fetchRide();
        return inflater.inflate(R.layout.fragment_driver_home, container, false);
    }

    private static Ride fetchRide(){
        Ride ride = new Ride();
        List<Passenger> passengers = new ArrayList<>();

        Passenger pera = new Passenger();
        pera.setName("pera");
        pera.setLastName("peric");

        passengers.add(pera);
        ride.setPassengers(passengers);
        return ride;
    }

    private void setPoints(){
        this.departure = Point.fromLngLat(19.842550,45.254410);
        this.destination = Point.fromLngLat(20.683809, 43.725128);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewElements(view);
        fillData();
        setPoints();
        drawRoute(departure, destination, "#c92418");
    }

    private void initViewElements(View view) {
        lvPassengers = view.findViewById(R.id.lv_d_passengers);
        lvLocations = view.findViewById(R.id.lv_d_locations);
        cbBaby = view.findViewById(R.id.cb_d_baby);
        cbPet = view.findViewById(R.id.cb_d_pet);
        tvTime = view.findViewById(R.id.tv_d_estimated_time);
        btnFinish = view.findViewById(R.id.btn_d_finish);
        btnPanic = view.findViewById(R.id.btn_d_panic);
    }

    private void fillData(){
        List<String> passengers = this.currentRide.getPassengers().stream().map(
                passenger -> passenger.getName() + " " + passenger.getLastName())
                .collect(Collectors.toList());
        lvPassengers.setAdapter(new EasyListAdapter<String>() {
            @Override
            public List<String> getList() {
                return passengers;
            }

            @Override
            public LayoutInflater getLayoutInflater() {
                return DriverHome.this.getLayoutInflater();
            }

            @Override
            public void applyToView(View view, String passenger) {
                TextView tvPassenger = view.findViewById(R.id.list_d_content);
                tvPassenger.setText(passenger);
            }

            @Override
            public int getListItemLayoutId() {
                return R.layout.list_d_simple_item;
            }
        });

        // TODO add locations from ride
        List<String> locations = new ArrayList<>();
        locations.add("Zike Mikica 21");
        locations.add("Mike Mikica 32");
        lvLocations.setAdapter(new EasyListAdapter<String>() {
            @Override
            public List<String> getList() {
                return locations;
            }

            @Override
            public LayoutInflater getLayoutInflater() {
                return DriverHome.this.getLayoutInflater();
            }

            @Override
            public void applyToView(View view, String passenger) {
                TextView tvLocation = view.findViewById(R.id.list_d_content);
                tvLocation.setText(passenger);
            }

            @Override
            public int getListItemLayoutId() {
                return R.layout.list_d_simple_item;
            }
        });

        cbBaby.setChecked(this.currentRide.isHasBaby());
        cbPet.setChecked(this.currentRide.isHasPets());

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopExecutor();
                View container = getActivity().findViewById(R.id.container_d_current);
                container.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Finished ride", Toast.LENGTH_LONG).show();
            }
        });

        btnPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO move to chat
                }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        startExecutor();
        resumeTimer();
    }

    private void startExecutor(){
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    private void stopExecutor(){
        executor.shutdownNow();
    }

    @SuppressLint("SetTextI18n")
    private void resumeTimer(){
        executor.scheduleWithFixedDelay(() -> {
            requireActivity().runOnUiThread(() ->
                    tvTime.setText(
                            getResources().getString(R.string.elapsed_time)
                                    + time.format(DateTimeFormatter.ofPattern("mm:ss"))));
            time = time.plusSeconds(1);
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public String getPublicMapApiToken() {
        return getResources().getString(R.string.mapbox_access_token);
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_driver_home;
    }

    @Override
    public int getMapViewID() {
        return R.id.d_mapView;
    }
    @Override
    public void onNewLocation(Location location) {
        if (!initiallyMovedToLocation) {
            // When the screen opens, move the map to our location (just the first time).
            lookAtPoint(Point.fromLngLat(location.getLongitude(), location.getLatitude()), 15, 4000);
            initiallyMovedToLocation = true;
        }
    }
}