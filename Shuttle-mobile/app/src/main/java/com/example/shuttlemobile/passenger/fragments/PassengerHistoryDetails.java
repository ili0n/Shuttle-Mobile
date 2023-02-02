package com.example.shuttlemobile.passenger.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shuttlemobile.FavoriteDialog;
import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.passenger.orderride.OrderActivity;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.route.LocationDTO;
import com.mapbox.geojson.Point;

public class PassengerHistoryDetails extends GenericUserMapFragment {
    private RideDTO ride;
    private Point A, B;
    private static final String RIDE_KEY = "ride";

    private TextView txtRouteFrom;
    private TextView txtRouteTo;
    private ImageButton btnFavorite;
    private Button btnOrderAgain;

    public static PassengerHistoryDetails newInstance(RideDTO ride) {
        PassengerHistoryDetails fragment = new PassengerHistoryDetails();
        Bundle args = new Bundle();
        args.putSerializable(RIDE_KEY, ride);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.ride = (RideDTO) getArguments().getSerializable(RIDE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_history_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LocationDTO A_loc = ride.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();
        A = Point.fromLngLat(A_loc.getLongitude(), A_loc.getLatitude());
        B = Point.fromLngLat(B_loc.getLongitude(), B_loc.getLatitude());

        drawRoute(A, B, "#FF0000");
        lookAtPoint(A, 10, 1000);
        initViews(view);
        fillData();
    }

    private void fillData() {
        final LocationDTO A_loc = ride.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();
        txtRouteFrom.setText(A_loc.getAddress());
        txtRouteTo.setText(B_loc.getAddress());

        btnOrderAgain.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), OrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(OrderActivity.KEY_ROUTE, ride.getLocations().get(0));
            bundle.putSerializable(OrderActivity.KEY_DIST, ride.getTotalLength());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        btnFavorite.setOnClickListener(view1 -> {
            DialogFragment favoriteDialog = FavoriteDialog.newInstance(ride);
            favoriteDialog.show(getChildFragmentManager(), "favorite");
        });
    }

    private void initViews(View view) {
        txtRouteFrom = view.findViewById(R.id.txt_p_ride_routeA);
        txtRouteTo = view.findViewById(R.id.txt_p_ride_routeB);
        
        btnOrderAgain = view.findViewById(R.id.bnt_p_ride_again);

        
        btnFavorite = view.findViewById(R.id.btn_p_ride_favorite);


    }

    @Override
    public String getPublicMapApiToken() {
        return requireActivity().getResources().getString(R.string.mapbox_access_token);
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_passenger_history_details;
    }

    @Override
    public int getMapViewID() {
        return R.id.p_details_map;
    }

    @Override
    public void onNewLocation(Location location) {

    }

}