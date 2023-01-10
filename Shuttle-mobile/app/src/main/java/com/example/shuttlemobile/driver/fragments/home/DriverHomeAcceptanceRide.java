package com.example.shuttlemobile.driver.fragments.home;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.driver.Driver;
import com.example.shuttlemobile.driver.fragments.DriverHome;

public class DriverHomeAcceptanceRide extends GenericUserMapFragment {
    @Override
    public String getPublicMapApiToken() {
        return getResources().getString(R.string.mapbox_access_token);
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
    public void onMapLoaded() {

    }

    @Override
    public void onNewLocation(Location location) {

    }

    public static DriverHomeAcceptanceRide newInstance() {
        return new DriverHomeAcceptanceRide();
    }
}