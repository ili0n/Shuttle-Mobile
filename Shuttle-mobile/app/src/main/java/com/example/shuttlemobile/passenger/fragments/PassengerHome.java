package com.example.shuttlemobile.passenger.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PassengerHome extends GenericUserMapFragment {
    private Button fitCamera, moveCamera; // Helper buttons for navigation.d
    private EditText txtDeparture, txtDestination;
    private Button btnCreateRoute;

    /**
     * Departure point.
     */
    private Point A = null;

    /**
     * Destination point.
     */
    private Point B = null;

    public static PassengerHome newInstance(SessionContext session) {
        PassengerHome fragment = new PassengerHome();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initViewElements(@NonNull View view) {
        txtDeparture = view.findViewById(R.id.txt_p_home_departure);
        txtDestination = view.findViewById(R.id.txt_p_home_destination);
        btnCreateRoute = view.findViewById(R.id.btn_p_home_makeRoute);
        fitCamera = view.findViewById(R.id.btnFitCam);
        moveCamera = view.findViewById(R.id.btnMoveCam);

        btnCreateRoute.setOnClickListener(view1 -> {
            hideKeyboard();
            makeRouteFromInput();
        });
        fitCamera.setOnClickListener(view12 -> fitCameraToRoute());
        moveCamera.setOnClickListener(view13 -> focusOnPointA());
    }

    private void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(btnCreateRoute.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Exception e) {
        }
    }

    /**
     * Given the text values for departure and destination, try to construct a route and move the
     * screen to focus on the route.
     */
    private void makeRouteFromInput() {
        final String dep = txtDeparture.getText().toString();
        final String dest = txtDestination.getText().toString();

        final Geocoder geocoder = new Geocoder(getContext());

        List<Address> addressesDep = new ArrayList<>();
        List<Address> addressesDest = new ArrayList<>();

        try {
            addressesDep = geocoder.getFromLocationName(dep, 1);
            addressesDest = geocoder.getFromLocationName(dest, 1);

            if (addressesDep.size() == 0) {
                Log.e("E", "Could not find departure");
                return;
            }
            if (addressesDest.size() == 0) {
                Log.e("E", "Could not find destination");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Address adrA = addressesDep.get(0);
        final Address adrB = addressesDest.get(0);

        A = Point.fromLngLat(adrA.getLongitude(), adrA.getLatitude());
        B = Point.fromLngLat(adrB.getLongitude(), adrB.getLatitude());

        drawRouteAndPoints();
        fitCameraToRoute();
    }

    /**
     * Draw the route from points A and B, including said points.
     * If either A or B are null, nothing happens.
     */
    private void drawRouteAndPoints() {
        if (A != null && B != null) {
            drawRoute(A, B, "#2369ED");
            drawCircle(A, 8.0, "#FF0000");
            drawCircle(B, 8.0, "#FFff00");
        }
    }

    /**
     * Move and zoom the screen to fit the route between A and B.
     * If either A or B are null, nothing happens.
     */
    private void fitCameraToRoute() {
        if (A != null && B != null) {
            fitViewport(A, B, 3000);
        }
    }

    /**
     * Move and zoom the screen to focus on A.
     * If A is null, nothing happens.
     */
    private void focusOnPointA() {
        if (A != null) {
            lookAtPoint(A, 15, 3000);
        }
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