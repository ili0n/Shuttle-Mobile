package com.example.shuttlemobile.passenger.orderride.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class RidePropertiesFragment extends Fragment {
    private Switch babySwitch;
    private Switch petSwitch;
    private Spinner spinner;

    public static RidePropertiesFragment newInstance(SessionContext session) {
        RidePropertiesFragment fragment = new RidePropertiesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ride_properties, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSpinnerItems(view);
        petSwitch = (Switch) view.findViewById(R.id.pet_switch);
        babySwitch = (Switch) view.findViewById(R.id.baby_switch);
    }

    private void setSpinnerItems(View view) {
        spinner = (Spinner) view.findViewById(R.id.vehicle_type_spinner);
        List<String> types = getVehicleTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private List<String> getVehicleTypes() {
        List<String> list = new ArrayList<>();
        list.add("STANDARD");
        list.add("VAN");
        list.add("LUXURY");
        return list;
    }

    public boolean isPetChecked(){
        return petSwitch.isChecked();
    }

    public boolean isBabyChecked(){
        return babySwitch.isChecked();
    }

    public String getVehicleType(){
        return spinner.getSelectedItem().toString();
    }
}