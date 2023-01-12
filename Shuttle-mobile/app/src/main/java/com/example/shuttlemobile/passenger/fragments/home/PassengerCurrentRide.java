package com.example.shuttlemobile.passenger.fragments.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.driver.services.CurrentRideTimeService;
import com.example.shuttlemobile.passenger.fragments.PassengerHome;
import com.example.shuttlemobile.passenger.services.PassengerRideService;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.route.LocationDTO;
import com.mapbox.geojson.Point;

public class PassengerCurrentRide extends Fragment {
    private TextView txtElapsedTime;

    private RideDTO ride = null;
    private Point A = null;
    private Point B = null;

    private PassengerHome parent = null;

    private BroadcastReceiver rideReceiver;
    private BroadcastReceiver timeReceiver;

    boolean startedTimer;


    public static PassengerCurrentRide newInstance() {
        return new PassengerCurrentRide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_current_ride, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initParent();
        initViewElements(view);
        initTimeReceiver();
        initRideReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(rideReceiver);
        getActivity().unregisterReceiver(timeReceiver);
        stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeToRideReceiver();
        subscribeToTimeReceiver();
        startTimer();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void initParent() {
        parent = (PassengerHome) PassengerCurrentRide.this.getParentFragment();
        if (parent == null) {
            throw new IllegalStateException("Parent is null (should be DriverHome)");
        }
    }

    private void initViewElements(View view) {
        txtElapsedTime = view.findViewById(R.id.txt_p_cur_ride_timer);
    }

    private void initTimeReceiver() {
        // TODO: This service is used by both passenger and driver, but
        //  it's declared in the driver package. See also startTimer().

        timeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(CurrentRideTimeService.NEW_TIME_MESSAGE);
                txtElapsedTime.setText(s);
            }
        };

        subscribeToTimeReceiver();
    }

    private void subscribeToTimeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CurrentRideTimeService.RESULT);
        getActivity().registerReceiver(timeReceiver, intentFilter);
    }

    private void initRideReceiver() {
        rideReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                RideDTO rideDTO = (RideDTO)intent.getSerializableExtra(PassengerRideService.INTENT_RIDE_KEY);
                onGetRide(rideDTO);
            }
        };

        subscribeToRideReceiver();
    }

    private void subscribeToRideReceiver() {
        if (rideReceiver == null) {
            return;
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PassengerRideService.BROADCAST_CHANNEL);
        getActivity().registerReceiver(rideReceiver, intentFilter);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void onGetRide(RideDTO dto) {
        Log.e("?", ride == null ? "null" : ride.toString());
        if (dto == null) {
            ride = null;
            A = null;
            B = null;
            stopTimer();
            return;
        }

        // If the currently cached ride is different from this one, redraw the route and focus on it.

        boolean isNewRide = false;
        if (ride == null || !ride.getId().equals(dto.getId())) {
            isNewRide = true;
        }

        boolean shouldShowTimer = false;
        if (ride != null) {
            Ride.Status currentStatus = Ride.Status.valueOf(ride.getStatus());
            Ride.Status dtoStatus = Ride.Status.valueOf(dto.getStatus());
            if (dtoStatus == Ride.Status.Accepted && dto.getStartTime() != null) {
                shouldShowTimer = true;
            }
        }

        // Update ride.

        ride = dto;
        final LocationDTO A_loc = ride.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();
        A = Point.fromLngLat(A_loc.getLongitude(), A_loc.getLatitude());
        B = Point.fromLngLat(B_loc.getLongitude(), B_loc.getLatitude());

        if (isNewRide) {
            updateViewElements();
            drawAndFocusCurrentRoute();
        }

        if (shouldShowTimer) {
            if (!startedTimer)
                startTimer();
        } else {
            stopTimer();
        }
    }

    private void startTimer() {
        if (ride != null && ride.getStartTime() != null) {
            Intent intent = new Intent(getActivity(), CurrentRideTimeService.class);
            intent.putExtra(CurrentRideTimeService.TIME_START, ride.getStartTime());
            requireActivity().startService(intent);
            startedTimer = true;
        }
    }

    private void stopTimer() {
        Intent myService = new Intent(requireContext(), CurrentRideTimeService.class);
        requireContext().stopService(myService);
    }

    private void drawAndFocusCurrentRoute() {
        if (A != null && B != null) {
            parent.drawRoute(A, B, "#FF0000");
            parent.fitViewport(A, B, 3000);
        }
    }
    private void updateViewElements() {

    }
}