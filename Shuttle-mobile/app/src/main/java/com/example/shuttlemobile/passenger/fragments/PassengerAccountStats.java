package com.example.shuttlemobile.passenger.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.dto.GraphEntryDTO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class PassengerAccountStats extends GenericUserFragment {
    private BarChart chart;

    public static PassengerAccountStats newInstance(SessionContext session) {
        PassengerAccountStats fragment = new PassengerAccountStats();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_passenger_account_stats, container, false);
        createGraph(v);
        setData();
        return v;
    }

    public void createGraph(View v){
        chart = v.findViewById(R.id.chart1);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
    }
    private void setData() {
        ArrayList<GraphEntryDTO> entries = mockEntries();
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = (int) 0; i < entries.size(); i++) {
            values.add(new BarEntry((float) i, entries.get(i).getCostSum().floatValue()));
        }

        BarDataSet set1;
//        change if data already exists
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
//        initial setting of data
        } else {
            setAxis(entries);
            set1 = new BarDataSet(values, "Passenger stats");
            set1.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chart.setData(data);
        }
    }

    private ArrayList<GraphEntryDTO> mockEntries() {
        ArrayList<GraphEntryDTO> entries = new ArrayList<>();
        GraphEntryDTO g1 = new GraphEntryDTO("asd", 1L, (double) 2.0, 3.0);
        GraphEntryDTO g2 = new GraphEntryDTO("efw", 1L, (double) 7.0, 3.0);
        GraphEntryDTO g3 = new GraphEntryDTO("xcv", 1L, (double) 3.0, 3.0);
        GraphEntryDTO g4 = new GraphEntryDTO("qwr", 1L, (double) 1.0, 3.0);
        entries.add(g1);
        entries.add(g2);
        entries.add(g3);
        entries.add(g4);
        return entries;
    }

    private void setAxis(ArrayList<GraphEntryDTO> entries) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter((value, axis) -> entries.get((int) value).getTime());
    }
}