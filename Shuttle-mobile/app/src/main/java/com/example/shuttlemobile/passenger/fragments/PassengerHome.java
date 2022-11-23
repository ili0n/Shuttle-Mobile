package com.example.shuttlemobile.passenger.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.BlankFragment;
import com.example.shuttlemobile.R;

public class PassengerHome extends Fragment {
    public static PassengerHome newInstance() {
        return new PassengerHome();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_home, container, false);
    }
}