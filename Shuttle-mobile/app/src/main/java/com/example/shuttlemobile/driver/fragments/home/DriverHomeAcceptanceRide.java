package com.example.shuttlemobile.driver.fragments.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.driver.Driver;
import com.example.shuttlemobile.driver.fragments.DriverHome;
import com.example.shuttlemobile.driver.services.DriverRideService;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.ride.RideDTO;
import com.example.shuttlemobile.route.LocationDTO;
import com.example.shuttlemobile.util.NotificationUtil;
import com.example.shuttlemobile.util.SettingsUtil;
import com.mapbox.geojson.Point;

import org.w3c.dom.Text;

public class DriverHomeAcceptanceRide extends GenericUserMapFragment {
    private TextView txtDeparture;
    private TextView txtDestination;
    private TextView txtDistance;
    private TextView txtTime;
    private TextView txtPrice;
    private TextView txtPassengerCount;
    private BroadcastReceiver rideReceiver;

    Point A = null;
    Point B = null;
    RideDTO ride = null;

    @Override
    public String getPublicMapApiToken() {
        return getContext().getResources().getString(R.string.mapbox_access_token);
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_driver_home_acceptance_ride;
    }

    @Override
    public int getMapViewID() {
        return R.id.map_acceptance_ride;
    }

    @Override
    public void onMapLoaded() {}

    @Override
    public void onNewLocation(Location location) {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewElements(view);
        initRideReceiver();
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

    public static DriverHomeAcceptanceRide newInstance() {
        return new DriverHomeAcceptanceRide();
    }

    private void initViewElements(View view) {
        txtDeparture = view.findViewById(R.id.txt_acceptance_ride_departure);
        txtDestination = view.findViewById(R.id.txt_acceptance_ride_destination);
        txtDistance = view.findViewById(R.id.txt_acceptance_ride_distance);
        txtTime = view.findViewById(R.id.txt_acceptance_ride_duration);
        txtPrice = view.findViewById(R.id.txt_acceptance_ride_price);
        txtPassengerCount = view.findViewById(R.id.txt_acceptance_ride_passengers);
    }

    private void onGetRide(RideDTO dto) {
        if (!isThisNewRide(dto)) {
            return;
        }
        LocationDTO Aloc = dto.getLocations().get(0).getDeparture();
        LocationDTO Bloc = dto.getLocations().get(dto.getLocations().size() - 1).getDestination();

        txtDeparture.setText(Aloc.getAddress());
        txtDestination.setText(Bloc.getAddress());
        txtDistance.setText("???");
        txtTime.setText(dto.getEstimatedTimeInMinutes() + "min");
        txtPrice.setText(dto.getTotalCost() + " RSD");
        txtPassengerCount.setText(dto.getPassengers().size() + "");

        ride = dto;
        A = null;
        B = null;

        tryDrawAndFocusRoute();

        if (!SettingsUtil.get(SettingsUtil.KEY_CURRENT_RIDE_ID, -1L).equals(dto.getId())) {
            NotificationUtil.sendNotification(
                    getActivity(),
                    NotificationUtil.DRIVER_NOTIFICATION_CHANNEL_ID,
                    "New ride",
                    "You have a new ride!",
                    R.drawable.car_green,
                    1100110110
            );
        }

        Log.e("..........", dto.getId() + "");
        SettingsUtil.put(SettingsUtil.KEY_CURRENT_RIDE_ID, dto.getId());
        // TODO: When you finish a ride, or if there's no ride, this should be set to null.
    }

    /**
     * Draw a new route and focus on it but only if there's new info (new ride basically, there's no
     * need to redraw the existing route if it's already on-screen).
     */
    private void tryDrawAndFocusRoute() {
        if (A == null && B == null) {
            LocationDTO Aloc = ride.getLocations().get(0).getDeparture();
            LocationDTO Bloc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();

            A = Point.fromLngLat(Aloc.getLongitude(), Aloc.getLatitude());
            B = Point.fromLngLat(Bloc.getLongitude(), Bloc.getLatitude());
            drawRoute(A, B, "#0000FF");
            fitViewport(A, B, 3000);
        }
    }

    /**
     * Check if the received DTO should override an existing ride in-memory (if any).
     * The idea behind is that a driver can have an active ride and a pending ride, so if he
     * for some reason gets a pending ride, it shouldn't be drawn on screen since the active
     * ride is more important.
     * @param dto New ride caught from the back. Can be null.
     * @return True if this ride should be in-focus, false otherwise.
     */
    private boolean isThisNewRide(RideDTO dto) {
        if (dto == null) {
            return false;
        }
        if (ride == null) {
            return true;
        }

        final Ride.State stateCurrent = Ride.State.valueOf(ride.getStatus().toUpperCase());
        final Ride.State stateUpcoming = Ride.State.valueOf(dto.getStatus().toUpperCase());
        if (stateCurrent == Ride.State.PENDING || stateCurrent == Ride.State.ACCEPTED || stateCurrent == Ride.State.STARTED) {
            return false;
        }

        return true;
    }
}