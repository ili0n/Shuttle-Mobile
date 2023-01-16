package com.example.shuttlemobile.driver.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.dto.GraphEntryDTO;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;

public class DriverAccountReports extends GenericUserFragment {

    private LineChart chart;
    private TableLayout table;

    private final int COST_SUM_COLOR = R.color.purple_200;
    private final int TOTAL_LENGTH_COLOR = R.color.red;
    private final int NUM_OF_RIDES_COLOR = R.color.green;

    final Calendar startDateCalendar = Calendar.getInstance();
    public static DriverAccountReports newInstance(SessionContext session) {
        DriverAccountReports fragment = new DriverAccountReports();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_account_reports, container, false);
        EditText startDate = view.findViewById(R.id.driver_report_start_date);
        setDate(startDate,startDateCalendar);
        EditText endDate = view.findViewById(R.id.driver_report_end_date);
        setDate(endDate,startDateCalendar);
        table = view.findViewById(R.id.legend_d_chart);
        view.findViewById(R.id.driver_report_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
            }
        });
        createGraph(view);
//        setData();
        return view;
    }

    private void setDate(EditText editText,Calendar calendar){
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(editText,calendar);
            }
        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText editText, Calendar calendar){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(calendar.getTime()));
    }

    public void createGraph(View v){
        chart = v.findViewById(R.id.driver_stats_chart);
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