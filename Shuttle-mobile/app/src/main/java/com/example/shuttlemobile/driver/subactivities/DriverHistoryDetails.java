package com.example.shuttlemobile.driver.subactivities;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.mapbox.geojson.Point;

public class DriverHistoryDetails extends GenericUserMapFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
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