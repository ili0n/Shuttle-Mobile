package com.example.shuttlemobile.passenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.example.shuttlemobile.R;

import java.util.ArrayList;
import java.util.Date;

public class PassengerRideHistoryFragment extends Fragment {
    ListView lvHistory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contentView = inflater.inflate(R.layout.fragment_passenger_ride_history, container, false);

        lvHistory = contentView.findViewById(R.id.passenger_history_list);

        ArrayList<Ride> rides = new ArrayList<Ride>();
        rides.add(new Ride(1, new Date(), new Date(), true, false, true, 100, 360));
        rides.add(new Ride(2, new Date(), new Date(), false, false, true, 100, 150));
        rides.add(new Ride(3, new Date(), new Date(), true, true, true, 100, 90));
        RideAdapter adapter = new RideAdapter(rides, getActivity());
        lvHistory.setAdapter(adapter);
        return contentView;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}