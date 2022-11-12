package com.example.shuttlemobile.passenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shuttlemobile.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RideAdapter extends ArrayAdapter<Ride> {
    private final Context context;
    private List<Ride> rides;


    public RideAdapter(List<Ride> rides, Context context) {
        super(context, -1, rides);
        this.context = context;
        this.rides = rides;
    }

    @Override
    public int getCount() {
        return rides.size();
    }

    @Override
    public Ride getItem(int i) {
        return rides.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Ride ride = rides.get(i);
        View listView = view;
        if(listView == null) {
            listView = LayoutInflater.from(this.context).inflate(R.layout.fragment_passenger_ride_history_item, null);
        }
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.US);

        TextView tvStart =(TextView) listView.findViewById(R.id.start);
        tvStart.setText(formatter.format(ride.getStart()));

        TextView tvEnd =(TextView) listView.findViewById(R.id.end);
        tvEnd.setText(formatter.format(ride.getEnd()));

        ImageView ivBaby = (ImageView) listView.findViewById(R.id.baby);
        ivBaby.setVisibility(ride.isBaby() ? View.VISIBLE: View.INVISIBLE);

        ImageView ivPets = (ImageView) listView.findViewById(R.id.pets);
        ivPets.setVisibility(ride.isPets() ? View.VISIBLE: View.INVISIBLE);

        ImageView ivFair = (ImageView) listView.findViewById(R.id.fair);
        ivFair.setVisibility(ride.isSplitFair() ? View.VISIBLE: View.INVISIBLE);

        NumberFormat numberFormatter = DecimalFormat.getInstance(Locale.US);

        numberFormatter.setMaximumFractionDigits(2);
        TextView tvPrice = (TextView) listView.findViewById(R.id.price);
        tvPrice.setText(numberFormatter.format(ride.getPrice()));

        TextView tvEvaluatedTime = (TextView) listView.findViewById(R.id.evaluatedTime);
        long diff = ride.getEvaluatedTime();
        tvEvaluatedTime.setText(String.format(Locale.getDefault(), "%d h %d m",  diff / 60, diff % 60));

        return listView;
    }
}
