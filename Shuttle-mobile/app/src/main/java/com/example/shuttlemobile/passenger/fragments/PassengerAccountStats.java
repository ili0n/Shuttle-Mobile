package com.example.shuttlemobile.passenger.fragments;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.dto.GraphEntryDTO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.concurrent.Callable;

public class PassengerAccountStats extends GenericUserFragment {
    private LineChart chart;
    private TableLayout table;

    private final int COST_SUM_COLOR = R.color.purple_200;
    private final int TOTAL_LENGTH_COLOR = R.color.red;
    private final int NUM_OF_RIDES_COLOR = R.color.green;

    public static PassengerAccountStats newInstance() {
        PassengerAccountStats fragment = new PassengerAccountStats();
        Bundle bundle = new Bundle();
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
        table = v.findViewById(R.id.legend_p_chart);
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

        calculateAndAdd(entries);

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
        chart.getLegend().setEnabled(false);
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
            costSumSet.setColors(new int[] { COST_SUM_COLOR }, requireContext());
            costSumSet.setDrawIcons(false);

            totalLengthSet = new LineDataSet(totalLength, "Total length");
            totalLengthSet.setColors(new int[] { TOTAL_LENGTH_COLOR }, requireContext());
            setAxis(entries);
            totalLengthSet.setDrawIcons(false);

            numOfRidesSet = new LineDataSet(numOfRides, "Number of rides");
            numOfRidesSet.setColors(new int[] { NUM_OF_RIDES_COLOR }, requireContext());
            setAxis(entries);
            numOfRidesSet.setDrawIcons(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(costSumSet);
            dataSets.add(totalLengthSet);
            dataSets.add(numOfRidesSet);

            LineData data = new LineData(dataSets);
            data.setValueTextSize(10f);
            chart.setData(data);

        }
    }

    private void calculateAndAdd(List<GraphEntryDTO> data){
        double sum = data.stream().mapToDouble(GraphEntryDTO::getCostSum).sum();
        OptionalDouble avgO = data.stream().mapToDouble(GraphEntryDTO::getCostSum).average();
        double avg;
        if(avgO.isPresent()){
            avg = avgO.getAsDouble();
        }
        else {
            avg = 0;
        }
        addRow(COST_SUM_COLOR, "Cost sum", sum, avg);

        sum = data.stream().mapToDouble(GraphEntryDTO::getLength).sum();
        avgO = data.stream().mapToDouble(GraphEntryDTO::getLength).average();
        if(avgO.isPresent()){
            avg = avgO.getAsDouble();
        }
        else {
            avg = 0;
        }
        addRow(TOTAL_LENGTH_COLOR, "Total length", sum, avg);

        sum = data.stream().mapToDouble(GraphEntryDTO::getNumberOfRides).sum();
        avgO = data.stream().mapToDouble(GraphEntryDTO::getNumberOfRides).average();
        if(avgO.isPresent()){
            avg = avgO.getAsDouble();
        }
        else {
            avg = 0;
        }
        addRow(NUM_OF_RIDES_COLOR, "Number of rides", sum, avg);

    }

    @SuppressLint("SetTextI18n")
    private void addRow(int colorVal, String labelText, double sum, double avg){
        LayoutInflater inflater = getLayoutInflater();
        TableRow tr = (TableRow) inflater.inflate(R.layout.chart_data_row,
                table, false);
        table.addView(tr);

        View color =(View) tr.findViewById(R.id.color_p_chart);
        TextView tvLabel = (TextView) tr.findViewById(R.id.lbl_p_chart_data);
        TextView tvSum = (TextView) tr.findViewById(R.id.lbl_p_chart_sum);
        TextView tvAvg = (TextView) tr.findViewById(R.id.lbl_p_chart_avg);

        color.setBackgroundColor(getResources().getColor(colorVal, null));
        tvLabel.setText(labelText);
        tvSum.setText(Double.toString(sum));
        tvAvg.setText( Double.toString(avg));
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