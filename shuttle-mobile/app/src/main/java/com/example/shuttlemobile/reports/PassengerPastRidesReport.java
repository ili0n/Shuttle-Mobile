package com.example.shuttlemobile.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.shuttlemobile.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PassengerPastRidesReport extends AppCompatActivity {

    private EditText etStartTime;
    private EditText etStartDate;
    private EditText etEndTime;
    private EditText etEndDate;
    private Button btnPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_past_rides_report);
        addToolbar();
        init();


    }
    void init(){
        btnPreview = findViewById(R.id.btnPreviewPassengerPastRidesReport);


        initEditTexts();
        initEditTextListeners();

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassengerPastRidesReport.this,
                        PassengerPastRidesReportPreview.class);
                Bundle bundle = new Bundle();
                bundle.putString("startDateTime",
                        etStartTime.getText().toString()+ etStartDate.getText().toString());
                bundle.putString("endDateTime",
                        etEndTime.getText().toString()+ etEndDate.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setTitle("Your past rides report");
    }

    private void initEditTextListeners() {
        etEndDate.setOnClickListener(new EditTextDateClickListener(etEndDate));
        etStartDate.setOnClickListener(new EditTextDateClickListener(etStartDate));

        etStartTime.setOnClickListener(new EditTextTimeClickListener(etStartTime));
        etEndTime.setOnClickListener(new EditTextTimeClickListener(etEndTime));
    }

    class EditTextDateClickListener implements View.OnClickListener {
        private final EditText et;

        public EditTextDateClickListener(EditText et) {
            this.et = et;
        }

        @Override
        public void onClick(View view) {
            int day = LocalDate.now().getDayOfMonth();
            int month = LocalDate.now().getMonthValue();
            int year = LocalDate.now().getYear();
            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(PassengerPastRidesReport.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            String result = String.format(Locale.getDefault(), "%d.%d.%d", day, month, year);
                            et.setText(result);
                        }
                    }, year, month, day);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        }
    }

    class EditTextTimeClickListener implements View.OnClickListener {
        private final EditText et;

        public EditTextTimeClickListener(EditText et) {
            this.et = et;
        }

        @Override
        public void onClick(View view) {
            LocalTime now = LocalTime.now();
            int hours = now.getHour();
            int minutes = now.getMinute();
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(PassengerPastRidesReport.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                            LocalTime selectedTime = LocalTime.of(hours, minutes);
                            String result = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                            et.setText(result);
                        }
                    }, hours, minutes, true);
            mTimePicker.setTitle("Select Date");
            mTimePicker.show();
        }

    }

    private void initEditTexts() {
        etEndDate = findViewById(R.id.etEndDate);
        etStartDate = findViewById(R.id.etStartDate);
        etEndTime = findViewById(R.id.etEndTime);
        etStartTime = findViewById(R.id.etStartTime);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}