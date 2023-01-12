package com.example.shuttlemobile.passenger.orderride.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RidePropertiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RidePropertiesFragment extends Fragment {


    public RidePropertiesFragment() {
        // Required empty public constructor
    }


    public static RidePropertiesFragment newInstance(SessionContext session) {
        RidePropertiesFragment fragment = new RidePropertiesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ride_properties, container, false);
        setSpinnerItems(view);

        return view;
    }

    private void setSpinnerItems(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.vehicle_type_spinner);
        List<String> types = getVehicleTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    List<String> getVehicleTypes() {
        List<String> list = new ArrayList<>();
        list.add("STANDARD");
        list.add("VAN");
        list.add("LUXURY");
        return list;
    }
}