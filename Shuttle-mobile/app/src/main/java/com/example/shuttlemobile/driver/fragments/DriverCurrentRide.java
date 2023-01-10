package com.example.shuttlemobile.driver.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shuttlemobile.BlankFragment;
import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.UserChatActivity;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;
import com.mapbox.geojson.Point;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverCurrentRide#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverCurrentRide extends Fragment {

    private ScheduledExecutorService executor;
    private LocalTime time = LocalTime.of(0, 0, 0);

    private ListView lvPassengers;
    private ListView lvLocations;
    private CheckBox cbBaby;
    private CheckBox cbPet;
    private TextView tvTime;
    private Button btnFinish;
    private Button btnPanic;

    private Point destination;
    private Point departure;
    private Ride currentRide;

    public DriverCurrentRide() {
    }

    public static DriverCurrentRide newInstance(String param1, String param2) {
        DriverCurrentRide fragment = new DriverCurrentRide();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: get from intent
        this.currentRide = fetchRide();
        return inflater.inflate(R.layout.fragment_driver_current_ride, container, false);
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
        DriverHome parentFrag = ((DriverHome)DriverCurrentRide.this.getParentFragment());
        if (parentFrag != null) {
            parentFrag.drawRoute(departure, destination, "#c92418");
        }
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
                return DriverCurrentRide.this.getLayoutInflater();
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
                return DriverCurrentRide.this.getLayoutInflater();
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

        btnFinish.setOnClickListener(view -> finishRide());
        btnPanic.setOnClickListener(view -> openChat());

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

    private void finishRide(){
        stopExecutor();
        DriverHome parentFrag = ((DriverHome)DriverCurrentRide.this.getParentFragment());
        if (parentFrag != null) {
            parentFrag.setCurrentFragment(new BlankFragment());
        }
        Toast.makeText(getActivity(), "Finished ride", Toast.LENGTH_LONG).show();
    }

    private void openChat() {
        // TODO session
        Intent intent = new Intent(getActivity(), UserChatActivity.class);
        intent.putExtra("session", "Asd");
        intent.putExtra("chat", "Asd");

        startActivity(intent);
    }
}