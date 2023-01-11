package com.example.shuttlemobile.passenger.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.orderride.OrderActivity;
import com.mapbox.geojson.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PassengerHome extends GenericUserMapFragment {
    private Button fitCamera, moveCamera; // Helper buttons for navigation.d
    private EditText txtDeparture, txtDestination;
    private Button btnCreateRoute;
    private Button btnOrderRoute;
    private boolean initiallyMovedToLocation = false;

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
        btnOrderRoute = view.findViewById(R.id.btn_p_home_order);
        fitCamera = view.findViewById(R.id.btnFitCam);
        moveCamera = view.findViewById(R.id.btnMoveCam);

        txtDeparture.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    makeRouteFromInput();
                    btnOrderRoute.setEnabled(hasRoute());
                }
            }
        });
        txtDestination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    makeRouteFromInput();
                    btnOrderRoute.setEnabled(hasRoute());
                }
            }
        });

        btnCreateRoute.setOnClickListener(view1 -> {
            hideKeyboard();
            makeRouteFromInput();
            btnOrderRoute.setEnabled(hasRoute());
        });

        btnOrderRoute.setEnabled(hasRoute());
        btnOrderRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Order route, create activity, send departure and destination through intent.
                Intent i = new Intent(getActivity(), OrderActivity.class);
                startActivity(i);
                Toast.makeText(PassengerHome.this.getActivity(), "Order", Toast.LENGTH_SHORT).show();
            }
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

        List<Address> addressesDep;
        List<Address> addressesDest;

        try {
            addressesDep = geocoder.getFromLocationName(dep, 1);
            addressesDest = geocoder.getFromLocationName(dest, 1);

            if (addressesDep.size() == 0) {
                Log.e("E", "Could not find departure");
                A = null;
                return;
            }
            if (addressesDest.size() == 0) {
                Log.e("E", "Could not find destination");
                B = null;
                return;
            }
        } catch (IOException e) {
            A = null;
            B = null;
            e.printStackTrace();
            return;
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

    private boolean hasRoute() {
        return A != null && B != null;
    }

    @Override
    public void onNewLocation(Location location) {
        if (!initiallyMovedToLocation) {
            // When the screen opens, move the map to our location (just the first time).
            lookAtPoint(Point.fromLngLat(location.getLongitude(), location.getLatitude()), 15, 4000);
            initiallyMovedToLocation = true;
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