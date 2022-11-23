package com.example.shuttlemobile.driver.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.passenger.fragments.PassengerAccount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriverAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriverAccount extends Fragment {
    public static DriverAccount newInstance() {
        return new DriverAccount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_account, container, false);
    }

}