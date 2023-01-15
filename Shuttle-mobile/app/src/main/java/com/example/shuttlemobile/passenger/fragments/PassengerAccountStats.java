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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class PassengerAccountStats extends GenericUserFragment {
    private LineChart chart;

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
        chart = v.findViewById(R.id.p_chart);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
    }
    private void setData() {
        ArrayList<GraphEntryDTO> entries = mockEntries();
        ArrayList<Entry> costSum = new ArrayList<>();
        ArrayList<Entry> totalLength = new ArrayList<>();
        ArrayList<Entry> numOfRides = new ArrayList<>();
        for (int i = (int) 0; i < entries.size(); i++) {
            costSum.add(new Entry((float) i, entries.get(i).getCostSum().floatValue()));
            totalLength.add(new Entry((float) i, entries.get(i).getLength().floatValue()));
            numOfRides.add(new Entry((float) i, entries.get(i).getNumberOfRides().floatValue()));
        }

        LineDataSet costSumSet;
        LineDataSet totalLengthSet;
        LineDataSet numOfRidesSet;
//        change if data already exists
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {

            costSumSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            costSumSet.setValues(costSum);

            totalLengthSet = (LineDataSet) chart.getData().getDataSetByIndex(1);
            totalLengthSet.setValues(totalLength);

            numOfRidesSet = (LineDataSet) chart.getData().getDataSetByIndex(2);
            numOfRidesSet.setValues(numOfRides);

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
//        initial setting of data
        } else {
            setAxis(entries);
            costSumSet = new LineDataSet(costSum, "Cost sum");
            costSumSet.setColors(new int[] { R.color.purple_200 }, requireContext());
            costSumSet.setDrawIcons(false);

            totalLengthSet = new LineDataSet(totalLength, "Total length");
            totalLengthSet.setColors(new int[] { R.color.red }, requireContext());
            setAxis(entries);
            totalLengthSet.setDrawIcons(false);

            numOfRidesSet = new LineDataSet(numOfRides, "Number of rides");
            numOfRidesSet.setColors(new int[] {R.color.green }, requireContext());
            setAxis(entries);
            numOfRidesSet.setDrawIcons(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(costSumSet);
            dataSets.add(totalLengthSet);
            dataSets.add(numOfRidesSet);


            LineData data = new LineData(dataSets);
            data.setValueTextSize(10f);
//            data.setBarWidth(0.9f);
            chart.setData(data);
        }
    }

    private ArrayList<GraphEntryDTO> mockEntries() {
        ArrayList<GraphEntryDTO> entries = new ArrayList<>();
        GraphEntryDTO g1 = new GraphEntryDTO("asd", 1L, (double) 2.0, 3.0);
        GraphEntryDTO g2 = new GraphEntryDTO("efw", 3L, (double) 7.0, 9.0);
        GraphEntryDTO g3 = new GraphEntryDTO("xcv", 4L, (double) 3.0, 10.0);
        GraphEntryDTO g4 = new GraphEntryDTO("qwr", 5L, (double) 1.0, 2.0);
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