package com.example.shuttlemobile.passenger.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.mapbox.geojson.Point;

public class PassengerHome extends GenericUserMapFragment {
    private Button fitCamera;
    private Button moveCamera;
    
    public static PassengerHome newInstance(SessionContext session) {
        PassengerHome fragment = new PassengerHome();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initViewElements(@NonNull View view) {
        fitCamera = view.findViewById(R.id.btnFitCam);
        fitCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Point A = Point.fromLngLat(19.80613, 45.23673);
                final Point B = Point.fromLngLat(19.80057, 45.24089);
                fitViewport(A, B, 3000);
            }
        });

        moveCamera = view.findViewById(R.id.btnMoveCam);
        moveCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Point A = Point.fromLngLat(19.80613, 45.23673);
                lookAtPoint(A, 20, 3000);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewElements(view);
    }

    @Override
    public String getPublicMapApiToken() {
        return getResources().getString(R.string.mapbox_access_token);
    }

    @Override
    public void onMapLoaded() {
        drawCar(Point.fromLngLat(0, 0), true);
        drawCar(Point.fromLngLat(3, 0), false);

        final Point A = Point.fromLngLat(19.80613, 45.23673);
        final Point B = Point.fromLngLat(19.80057, 45.24089);

        drawRoute(A, B, "#2369ED");
        drawCircle(A, 8.0, "#FF0000");
        drawCircle(B, 8.0, "#FFff00");
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_passenger_home;
    }

    @Override
    public int getMapViewID() {
        return R.id.mapView;
    }
}