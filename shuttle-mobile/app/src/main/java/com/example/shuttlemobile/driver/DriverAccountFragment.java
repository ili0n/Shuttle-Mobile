package com.example.shuttlemobile.driver;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.vehicle.*;


public class DriverAccountFragment extends Fragment {
    public DriverAccountFragment() {
    }

    public static DriverAccountFragment newInstance() {
        return new DriverAccountFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.titleAccount);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Vehicle vehicle = MockupVehicle.getVehicle();
        ((TextView) getView().findViewById(R.id.accountBabyValue)).setText(String.valueOf(vehicle.isBaby()));
        ((TextView) getView().findViewById(R.id.accountSpotsValue)).setText(String.valueOf(vehicle.getSpots()));
        ((TextView) getView().findViewById(R.id.accountplateValue)).setText(vehicle.getPlate());
        ((TextView) getView().findViewById(R.id.accountModelValue)).setText(vehicle.getModel());
        ((TextView) getView().findViewById(R.id.accountLicenceValue)).setText(vehicle.getLicenceNum());
        ((TextView) getView().findViewById(R.id.nameValue)).setText(vehicle.getDriver().getName());
        ((TextView) getView().findViewById(R.id.surnameValue)).setText(vehicle.getDriver().getSurname());
        ((TextView) getView().findViewById(R.id.phoneValue)).setText(vehicle.getDriver().getPhone());
        ((TextView) getView().findViewById(R.id.emailValue)).setText(vehicle.getDriver().getEmail());
        ((TextView) getView().findViewById(R.id.addressValue)).setText(vehicle.getDriver().getAddress());
        ((TextView) getView().findViewById(R.id.accountLicenceValue)).setText(vehicle.getLicenceNum());
        ((ImageView) getView().findViewById(R.id.driverProfilePic)).setImageResource(R.drawable.icarus);


    }
}