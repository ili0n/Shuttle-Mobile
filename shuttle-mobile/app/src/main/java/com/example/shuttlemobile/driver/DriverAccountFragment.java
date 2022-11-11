package com.example.shuttlemobile.driver;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;


public class DriverAccountFragment extends Fragment {
    public DriverAccountFragment() {}

    public static DriverAccountFragment newInstance() {
        return new DriverAccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_account, container, false);
    }
}