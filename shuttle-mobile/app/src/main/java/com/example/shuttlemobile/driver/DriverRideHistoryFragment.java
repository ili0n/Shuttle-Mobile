package com.example.shuttlemobile.driver;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.util.MockupData;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DriverRideHistoryFragment extends Fragment {
    public DriverRideHistoryFragment() {}

    public static DriverRideHistoryFragment newInstance() {
        return new DriverRideHistoryFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.titleHistory);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_ride_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView ridesView = getView().findViewById(R.id.driver_ride_history_list);

        ridesView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return MockupData.getRides().size();
            }

            @Override
            public Object getItem(int i) {
                return MockupData.getRides().get(i);
            }

            @Override
            public long getItemId(int i) {
                return ((Ride)getItem(i)).getID();
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View vi;
                if (view == null) {
                    vi = DriverRideHistoryFragment.this.getLayoutInflater().inflate(R.layout.list_driver_ride_history, null);
                } else {
                    vi = view;
                }

                Ride obj = (Ride)getItem(i);
                long durMinutes = ChronoUnit.MINUTES.between(obj.getStart(), obj.getFinish());

                TextView start = vi.findViewById(R.id.driver_ride_history_list_begin_date);
                TextView duration = vi.findViewById(R.id.driver_ride_history_list_duration);
                TextView userCount = vi.findViewById(R.id.driver_ride_history_list_passenger_count);
                TextView price = vi.findViewById(R.id.driver_ride_history_list_price);

                start.setText(obj.getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                duration.setText(Long.toString(durMinutes) + " min");
                userCount.setText(Integer.toString(obj.getPassengers().size()));
                price.setText(Double.toString(obj.getPrice()) + " RSD");

                return vi;
            }
        });
        
        ridesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ride obj = (Ride)ridesView.getItemAtPosition(i);

                Intent intent = new Intent(getActivity(), DriverRideHistoryItemActivity.class);
                intent.putExtra("ride", obj);
                startActivity(intent);
            }
        });
    }
}