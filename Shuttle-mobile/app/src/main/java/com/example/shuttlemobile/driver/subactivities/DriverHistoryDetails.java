package com.example.shuttlemobile.driver.subactivities;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.driver.Driver;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.route.LocationDTO;
import com.mapbox.geojson.Point;

public class DriverHistoryDetails extends GenericUserMapFragment {
    private RideDTO ride;
    private Point A, B;

    public void setRide(RideDTO dto) {
        this.ride = dto;
    }

    public static DriverHistoryDetails newInstance(RideDTO ride) {
        DriverHistoryDetails dhd = new DriverHistoryDetails();
        dhd.setRide(ride);
        return dhd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LocationDTO A_loc = ride.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();
        A = Point.fromLngLat(A_loc.getLongitude(), A_loc.getLatitude());
        B = Point.fromLngLat(B_loc.getLongitude(), B_loc.getLatitude());

        drawRoute(A, B, "#FF0000");
        fitViewport(A, B, 3000); // TODO: What?
    }

    @Override
    public String getPublicMapApiToken() {
        return getActivity().getResources().getString(R.string.mapbox_access_token);
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_driver_history_details;
    }


    @Override
    public int getMapViewID() {
        return R.id.map_driver_history_details;
    }

    @Override
    public void onNewLocation(Location location) {

    }
}