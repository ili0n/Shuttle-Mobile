package com.example.shuttlemobile.driver.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.driver.DriverStatsDTO;
import com.example.shuttlemobile.passenger.dto.GraphEntryDTO;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class DriverAccountStats extends GenericUserFragment {


    public static DriverAccountStats newInstance(SessionContext session) {
        DriverAccountStats fragment = new DriverAccountStats();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_account_stats, container, false);
        EditText rejections = view.findViewById(R.id.d_stat_rejection);
        EditText rides = view.findViewById(R.id.d_stat_rides);
        EditText hours = view.findViewById(R.id.d_stat_hours);
        EditText money = view.findViewById(R.id.d_stat_money);

        Button submit = view.findViewById(R.id.d_stat_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO request
                DriverStatsDTO dto = new DriverStatsDTO(10, 10, 100, 2500);

                setData(rejections, rides, hours, money, dto);
            }
        });

        return view;
    }

    private void setData(EditText rejections, EditText rides, EditText hours, EditText money, DriverStatsDTO statsDTO) {
        rejections.setText(statsDTO.getRejections() + "");
        rides.setText(statsDTO.getRides() + "");
        hours.setText(statsDTO.getHours() + "");
        money.setText(statsDTO.getMoney() + "");
    }


}