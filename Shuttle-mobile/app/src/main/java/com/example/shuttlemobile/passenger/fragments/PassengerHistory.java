package com.example.shuttlemobile.passenger.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.passenger.services.PassengerMessageService;
import com.example.shuttlemobile.passenger.subactivities.PassengerHistoryDetailsActivity;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.util.NotificationUtil;

import java.util.ArrayList;
import java.util.List;

public class PassengerHistory extends GenericUserFragment {
    public static PassengerHistory newInstance(SessionContext session) {
        PassengerHistory fragment = new PassengerHistory();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeList();
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), NotificationUtil.PASSENGER_NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Notification Title!")
                .setContentText("Notification Text!")
                .setSmallIcon(R.drawable.car_green)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        notificationManager.notify(900009, builder.build());
    }

    private void initializeList() {
        ///////////////////////////////

        sendNotification();
        getActivity().startService(new Intent(getActivity(), PassengerMessageService.class));

        ///////////////////////////////

        ListView listView = getActivity().findViewById(R.id.list_p_history);

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
            public LayoutInflater getLayoutInflater() { return PassengerHistory.this.getLayoutInflater(); }
            @Override
            public int getListItemLayoutId() { return R.layout.list_p_history; }
            @Override
            public void applyToView(View view, Ride obj) {
                TextView routeA = view.findViewById(R.id.list_p_history_route_A);
                TextView routeB = view.findViewById(R.id.list_p_history_route_B);
                TextView date = view.findViewById(R.id.list_p_history_date);
                TextView time = view.findViewById(R.id.list_p_history_time);
                TextView cost = view.findViewById(R.id.list_p_history_cost);
                TextView driverFullName = view.findViewById(R.id.list_p_history_dname);
                ImageView driverPfp = view.findViewById(R.id.list_p_history_dpfp);

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
        Intent intent = new Intent(getActivity(), PassengerHistoryDetailsActivity.class);
        intent.putExtra(PassengerHistoryDetailsActivity.PARAM_SESSION, session);
        intent.putExtra(PassengerHistoryDetailsActivity.PARAM_RIDE, ride);
        startActivity(intent);
    }
}