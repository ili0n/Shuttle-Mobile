package com.example.shuttlemobile.passenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.util.MockupData;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PassengerRideHistoryFragment extends Fragment {

    public static PassengerRideHistoryFragment newInstance() {
        return new PassengerRideHistoryFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.titleHistory);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contentView = inflater.inflate(R.layout.fragment_passenger_ride_history, container, false);

        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = getView().findViewById(R.id.passenger_history_list);

        List<Ride> rides = MockupData.getRidesForPassenger();
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return rides.size();
            }

            @Override
            public Object getItem(int i) {
                return rides.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View vi;
                if (view == null) {
                    vi = PassengerRideHistoryFragment.this.getLayoutInflater().inflate(R.layout.fragment_passenger_ride_history_item, null);
                } else {
                    vi = view;
                }

                Ride obj = (Ride)getItem(i);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

                TextView tvStart = vi.findViewById(R.id.start);
                //TextView tvEnd = vi.findViewById(R.id.end);
                ImageView ivBaby = vi.findViewById(R.id.baby);
                ImageView ivPets = vi.findViewById(R.id.pets);
                ImageView ivFair = vi.findViewById(R.id.fair);
                TextView tvPrice = vi.findViewById(R.id.price);
                TextView tvEvaluatedTime = vi.findViewById(R.id.evaluatedTime);

                tvStart.setText(obj.getStart().format(formatter));
                //tvEnd.setText(obj.getFinish().format(formatter));
                ivBaby.setVisibility(obj.isHasBaby() ? View.VISIBLE: View.INVISIBLE);
                ivPets.setVisibility(obj.isHasPets() ? View.VISIBLE: View.INVISIBLE);
                ivFair.setVisibility(obj.getPassengers().size() > 1 ? View.VISIBLE: View.INVISIBLE);
                tvPrice.setText(Double.toString(obj.getPrice()) + " RSD");
                tvEvaluatedTime.setText(obj.getEstimation().format(DateTimeFormatter.ofPattern("H:mm")));

                boolean odd = i % 2 == 1;
                if (odd) {
                    vi.setBackgroundColor(getResources().getColor(R.color.lightGray1));
                } else {
                    vi.setBackgroundColor(getResources().getColor(R.color.lightGray2));
                }

                return vi;
            }
        });
    }
}