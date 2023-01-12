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
 * Use the {@link ScheduleRide#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleRide extends Fragment {
    private static final int MAX_HOURS = 5;
    private static final int MAX_MINUTES = 60;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleRide() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ScheduleRide newInstance(SessionContext session) {
        ScheduleRide fragment = new ScheduleRide();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_ride, container, false);
        setHourSpinnerItems(view);
        setMinuteSpinnerItems(view);
        return view;
    }


    private void setMinuteSpinnerItems(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.minute_spinner);
        List<Integer> hours = getMinuteValues();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, hours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    List<Integer> getMinuteValues() {
        List<Integer> minutes = new ArrayList<>();
        for (int i = 0; i < MAX_MINUTES; i += 5) {
            minutes.add(i);
        }
        return minutes;
    }

    private void setHourSpinnerItems(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.hour_spinner);
        List<Integer> hours = getHourValues();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, hours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    List<Integer> getHourValues() {
        List<Integer> hours = new ArrayList<>();
        for (int i = 0; i < MAX_HOURS; i++) {
            hours.add(i);
        }
        return hours;
    }
}