package com.example.shuttlemobile.driver.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.driver.subactivities.DriverHistoryDetailsActivity;
import com.example.shuttlemobile.ride.Ride;

import java.util.ArrayList;
import java.util.List;

public class DriverHistory extends GenericUserFragment {
    public static DriverHistory newInstance(SessionContext session) {
        DriverHistory fragment = new DriverHistory();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeList();
    }

    private void initializeList() {
        ListView listView = getActivity().findViewById(R.id.list_d_history);

        List<Ride> rides = new ArrayList<>();
        rides.add(new Ride());
        rides.add(new Ride());
        rides.add(new Ride());
        rides.add(new Ride());
        rides.add(new Ride());
        rides.add(new Ride());
        rides.add(new Ride());

        listView.setAdapter(new EasyListAdapter<Ride>() {
            @Override
            public List<Ride> getList() { return rides; }
            @Override
            public LayoutInflater getLayoutInflater() { return DriverHistory.this.getLayoutInflater(); }
            @Override
            public int getListItemLayoutId() { return R.layout.list_d_history; }
            @Override
            public void applyToView(View view, Ride obj) {
                TextView routeA = view.findViewById(R.id.list_d_history_route_A);
                TextView routeB = view.findViewById(R.id.list_d_history_route_B);
                TextView date = view.findViewById(R.id.list_d_history_date);
                TextView time = view.findViewById(R.id.list_d_history_time);
                TextView cost = view.findViewById(R.id.list_d_history_cost);
                TextView driverFullName = view.findViewById(R.id.list_d_history_pname);
                ImageView driverPfp = view.findViewById(R.id.list_d_history_ppfp);

                //passengerName.setText(obj.getPassenger().getName());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ride obj = (Ride)listView.getItemAtPosition(i);
                openRideDetailsActivity(obj);
            }
        });
    }

    private void openRideDetailsActivity(Ride ride) {
        Intent intent = new Intent(getActivity(), DriverHistoryDetailsActivity.class);
        intent.putExtra(DriverHistoryDetailsActivity.PARAM_SESSION, session);
        intent.putExtra(DriverHistoryDetailsActivity.PARAM_RIDE, ride);
        startActivity(intent);
    }

}