package com.example.shuttlemobile.driver.fragments.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.driver.fragments.DriverHome;
import com.example.shuttlemobile.driver.fragments.PanicPromptFragment;
import com.example.shuttlemobile.driver.services.CurrentRideTimeService;
import com.example.shuttlemobile.driver.services.DriverRideService;
import com.example.shuttlemobile.ride.IRideService;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.ride.dto.RidePassengerDTO;
import com.example.shuttlemobile.route.LocationDTO;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;
import com.mapbox.geojson.Point;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverCurrentRide extends Fragment {
    private ListView lvPassengers;
    private ListView lvLocations;
    private CheckBox cbBaby;
    private CheckBox cbPet;
    private TextView tvTime;
    private Button btnFinish;
    private Button btnPanic;

    private RideDTO currentRide = null;

    private Point A = null;
    private Point B = null;

    private DriverHome parent = null;
    private BroadcastReceiver rideReceiver;
    private BroadcastReceiver timeReceiver;

    public static DriverCurrentRide newInstance() {
        DriverCurrentRide fragment = new DriverCurrentRide();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initParent();

        initTimeReceiver();
        initRideReceiver();

        initViewElements(view);
    }

    private void initParent() {
        parent = (DriverHome)DriverCurrentRide.this.getParentFragment();
        if (parent == null) {
            throw new IllegalStateException("Parent is null (should be DriverHome)");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_current_ride, container, false);
    }

    private void initRideReceiver() {
        rideReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                RideDTO rideDTO = (RideDTO)intent.getSerializableExtra(DriverRideService.INTENT_RIDE_KEY);
                onGetRide(rideDTO);
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DriverRideService.BROADCAST_CHANNEL);
        getActivity().registerReceiver(rideReceiver, intentFilter);
    }

    private void initTimeReceiver() {
        timeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(CurrentRideTimeService.NEW_TIME_MESSAGE);
                tvTime.setText(s);
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CurrentRideTimeService.RESULT);
        getActivity().registerReceiver(timeReceiver, intentFilter);
    }

    private void onGetRide(RideDTO dto) {
        if (dto == null) {
            currentRide = null;
            A = null;
            B = null;
            return;
        }

        // If the currently cached ride is different from this one, redraw the route and focus on it.

        boolean isNewRide = false;
        if (currentRide == null || !currentRide.getId().equals(dto.getId())) {
            isNewRide = true;
        }

        // Update ride.

        currentRide = dto;
        final LocationDTO A_loc = currentRide.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = currentRide.getLocations().get(currentRide.getLocations().size() - 1).getDestination();

        A = Point.fromLngLat(A_loc.getLongitude(), A_loc.getLatitude());
        B = Point.fromLngLat(B_loc.getLongitude(), B_loc.getLatitude());

        if (isNewRide) {
            startTimer();
            fillData();

            // Draw route.

            parent.drawRoute(A, B, "#FF0000");
            parent.fitViewport(A, B, 3000);
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

    private void fillData() {
        fillPassengers();
        fillLocations();
        cbBaby.setChecked(this.currentRide.getBabyTransport());
        cbPet.setChecked(this.currentRide.getPetTransport());
        btnFinish.setOnClickListener(view -> finishRide());
        btnPanic.setOnClickListener(view -> openPanicDialog());
    }

    private void openPanicDialog() {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        PanicPromptFragment panicFragment = new PanicPromptFragment();
//
//        if (isLargeLayout) {
//            // The device is using a large layout, so show the fragment as a dialog
//            panicFragment.show(fragmentManager, "dialog");
//        } else {
//            // The device is smaller, so show the fragment fullscreen
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            transaction.add(android.R.id.content, panicFragment)
//                    .addToBackStack(null).commit();
//        }
    }

    private void fillPassengers() {
        List<String> passengers = currentRide.getPassengers().stream().map(p -> p.getEmail()).collect(Collectors.toList());
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
                .map(route -> route.getDeparture().getAddress() + " -> " + route.getDestination().getAddress())
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

    private void finishRide(){
        Call<RideDTO> call = IRideService.service.endRide(currentRide.getId());
        call.enqueue(new Callback<RideDTO>() {
            @Override
            public void onResponse(Call<RideDTO> call, Response<RideDTO> response) {
            }

            @Override
            public void onFailure(Call<RideDTO> call, Throwable t) {
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    private void stopTimer() {
        Intent myService = new Intent(requireContext(), CurrentRideTimeService.class);
        requireContext().stopService(myService);
    }

    private void startTimer() {
        if (currentRide != null && currentRide.getStartTime() != null) {
            Intent intent = new Intent(getActivity(), CurrentRideTimeService.class);
            intent.putExtra(CurrentRideTimeService.TIME_START, currentRide.getStartTime());
            requireActivity().startService(intent);
        }
    }

//    public void setRide(long driverId){
//        Call<RideDTO> call =  IRideService.service.getActiveRide(driverId);
//        call.enqueue(new Callback<RideDTO>() {
//            @Override
//            public void onResponse(@NonNull Call<RideDTO> call, @NonNull Response<RideDTO> response) {
//                if(response.isSuccessful()){
//                    DriverCurrentRide.this.currentRide = response.body();
//                    Intent intent = new Intent(getActivity(), CurrentRideTimeService.class);
//                    if(currentRide.getStartTime() != null){
//                        intent.putExtra(CurrentRideTimeService.TIME_START, currentRide.getStartTime());
//                        requireActivity().startService(intent);
//                        fillData();
//                        setPoints();
//                    }
//                }
//                else{
//                    Toast.makeText(getActivity(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<RideDTO> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

}