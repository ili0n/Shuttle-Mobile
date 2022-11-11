package com.example.shuttlemobile.driver;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;

public class DriverRideHistoryFragment extends Fragment {
    public DriverRideHistoryFragment() {}

    public static DriverRideHistoryFragment newInstance() {
        return new DriverRideHistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_ride_history, container, false);
    }
}