package com.example.shuttlemobile.route;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;

import java.util.List;
import java.util.Locale;


public class FavouriteRouteAdapter extends BaseAdapter {

    private final List<Route> routes;
    private final Context context;

    public FavouriteRouteAdapter(List<Route> routes, Context context) {
        this.routes = routes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.routes.size();
    }

    @Override
    public Object getItem(int i) {
        return this.routes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.route_list_item, null);
        }
        TextView tvDistance = view.findViewById(R.id.routeListItemDistance);
        TextView tvStart= view.findViewById(R.id.routeListItemStart);
        TextView tvEnd= view.findViewById(R.id.routeListItemEnd);
        TextView tvTime= view.findViewById(R.id.routeListItemTime);
        TextView tvPrice= view.findViewById(R.id.routeListItemPrice);

        Route route = routes.get(i);

        tvDistance.setText(String.valueOf(route.getDistance()));
//        TODO: get address with api
        tvStart.setText(String.format(Locale.getDefault(), "(%d, %d)",
                route.getPointStart().getLatitude(), route.getPointStart().getLongitude()));
        tvEnd.setText(String.format(Locale.getDefault(), "(%d, %d)",
                route.getPointEnd().getLatitude(), route.getPointEnd().getLongitude()));
        tvTime.setText(route.getEstimation().toString());
        tvPrice.setText(String.valueOf(route.getPrice()));

        return view;
    }
}
