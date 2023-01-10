package com.example.shuttlemobile.driver.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.shuttlemobile.ride.IRideService;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.ride.dto.RideDriverDTO;
import com.example.shuttlemobile.ride.dto.RidePassengerDTO;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;
import com.mapbox.geojson.Point;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverCurrentRide#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverCurrentRide extends Fragment {
    private boolean isLargeLayout;

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
    private RideDTO currentRide;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();
    private IRideService rideService = retrofit.create(IRideService.class);

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
        isLargeLayout = getResources().getBoolean(R.bool.large_layout);
        return inflater.inflate(R.layout.fragment_driver_current_ride, container, false);
    }


    private void setPoints(){
        this.departure = Point.fromLngLat(19.842550,45.254410);
        this.destination = Point.fromLngLat(20.683809, 43.725128);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewElements(view);
        setRide();
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

    private void setRide(){

        Call<RideDTO> call =  rideService.getRide(1);
        call.enqueue(new Callback<RideDTO>() {
            @Override
            public void onResponse(@NonNull Call<RideDTO> call, @NonNull Response<RideDTO> response) {
                if(response.isSuccessful()){
                    DriverCurrentRide.this.currentRide = response.body();
                    fillData();
                }
                else{
                    Toast.makeText(getActivity(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RideDTO> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillData(){
        fillPassengers();
        fillLocations();
        cbBaby.setChecked(this.currentRide.getBabyTransport());
        cbPet.setChecked(this.currentRide.getPetTransport());
        btnFinish.setOnClickListener(view -> finishRide());
        btnPanic.setOnClickListener(view -> openPanicDialog());
    }

    private void openPanicDialog() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        PanicPromptFragment panicFragment = new PanicPromptFragment();

        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            panicFragment.show(fragmentManager, "dialog");
        } else {
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, panicFragment)
                    .addToBackStack(null).commit();
        }

    }

    private void fillPassengers() {
        List<String> passengers = this.currentRide.getPassengers().stream()
                .map( RidePassengerDTO::getEmail)
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
    }

    private void fillLocations() {
        List<String> locations = currentRide.getLocations().stream()
                .map(route -> route.getDeparture() + " -> " + route.getDestination())
                .collect(Collectors.toList());
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
        Call<RideDTO> call = rideService.endRide(currentRide.getId());
        call.enqueue(new Callback<RideDTO>() {
            @Override
            public void onResponse(Call<RideDTO> call, Response<RideDTO> response) {
                if(response.isSuccessful()){
                    stopExecutor();
                    DriverHome parentFrag = ((DriverHome)DriverCurrentRide.this.getParentFragment());
                    if (parentFrag != null) {
                        parentFrag.setCurrentFragment(new BlankFragment());
                    }
                    Toast.makeText(getActivity(), "Finished ride", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RideDTO> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}