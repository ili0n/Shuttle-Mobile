package com.example.shuttlemobile.passenger.orderride.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

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
    Spinner hourSpinner;
    Spinner minuteSpinner;

    public ScheduleRide() {
        // Required empty public constructor
    }


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_ride, container, false);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hourSpinner = (Spinner) view.findViewById(R.id.hour_spinner);
        minuteSpinner = (Spinner) view.findViewById(R.id.minute_spinner);
        setHourSpinnerItems(hourSpinner);
        setMinuteSpinnerItems(minuteSpinner);
        setSwitchListener(view, hourSpinner, minuteSpinner);
    }

    private void setSwitchListener(View view, Spinner hourSpinner, Spinner minuteSpinner) {
        Switch schedule = (Switch) view.findViewById(R.id.schedule_switch);
        schedule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hourSpinner.setEnabled(isChecked);
                minuteSpinner.setEnabled(isChecked);
            }
        });
    }

    private void setMinuteSpinnerItems(Spinner spinner) {

        List<Integer> hours = getMinuteValues();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, hours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setEnabled(false);
    }

    List<Integer> getMinuteValues() {
        List<Integer> minutes = new ArrayList<>();
        for (int i = 0; i < MAX_MINUTES; i += 5) {
            minutes.add(i);
        }
        return minutes;
    }

    private void setHourSpinnerItems(Spinner spinner) {
        List<Integer> hours = getHourValues();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, hours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setEnabled(false);
    }

    List<Integer> getHourValues() {
        List<Integer> hours = new ArrayList<>();
        for (int i = 0; i < MAX_HOURS; i++) {
            hours.add(i);
        }
        return hours;
    }

    public String getMinuteAdvance() {
        if (minuteSpinner.isEnabled()) {
            return minuteSpinner.getSelectedItem().toString();
        }
        return null;
    }


    public String getHourAdvance() {
        if (hourSpinner.isEnabled()) {
            return hourSpinner.getSelectedItem().toString();
        }
        return null;
    }
}