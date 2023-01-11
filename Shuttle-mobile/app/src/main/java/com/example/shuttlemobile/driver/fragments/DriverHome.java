package com.example.shuttlemobile.driver.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.shuttlemobile.BlankFragment;
import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.driver.services.CurrentRideStatusService;
import com.example.shuttlemobile.driver.services.DriversLocationService;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.util.SettingsUtil;
import com.mapbox.geojson.Point;

public class DriverHome extends GenericUserMapFragment {
    private boolean initiallyMovedToLocation = false;
    private BroadcastReceiver rideStatusReceiver;
    private BroadcastReceiver driversLocationReceiver;

    Fragment currentFragment;
    DriverCurrentRide currentRideFragment = new DriverCurrentRide();
    BlankFragment blankFragment = new BlankFragment();

    private Ride.Status rideStatus;

    public static DriverHome newInstance(SessionContext session) {
        DriverHome fragment = new DriverHome();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCurrentFragment(blankFragment);
    }

    public void setCurrentFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .replace(R.id.container_d_home, fragment);
        fragmentTransaction.addToBackStack("DriverHome");
        fragmentTransaction.commit();
        currentFragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setReceiveOperations();
        setPullingRideStatus();
        registerReceivers();
    }

    @Override
    public void onStop() {
        unregisterReceivers();
        super.onStop();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPullingLocation();
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
        return R.layout.fragment_driver_home;
    }

    @Override
    public int getMapViewID() {
        return R.id.d_mapView;
    }
    @Override
    public void onNewLocation(Location location) {
        if (!initiallyMovedToLocation) {
            // When the screen opens, move the map to our location (just the first time).
            lookAtPoint(Point.fromLngLat(location.getLongitude(), location.getLatitude()), 15, 4000);
            initiallyMovedToLocation = true;
        }
    }

    private void placeFragment(){
        this.setCurrentFragment(currentRideFragment);
    }

    private void removeFragment() {
        this.setCurrentFragment(blankFragment);
    }

    private void registerReceivers(){
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver((rideStatusReceiver),
                new IntentFilter(CurrentRideStatusService.RESULT)
        );
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver((driversLocationReceiver),
                new IntentFilter(DriversLocationService.RESULT)
        );
    }

    private void unregisterReceivers(){
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(rideStatusReceiver);
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(driversLocationReceiver);
    }

    private void setPullingRideStatus() {
        Intent intent = new Intent(getActivity(), CurrentRideStatusService.class);
        long driverId = SettingsUtil.getUserJWT().getId();
        intent.putExtra(CurrentRideStatusService.DRIVER_ID, driverId);
        requireActivity().startService(intent);
    }

    private void setPullingLocation() {
        Intent intent = new Intent(getActivity(), DriversLocationService.class);
        requireActivity().startService(intent);
    }

    private void setReceiveOperations() {
        Handler handler = new Handler();
        rideStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.hasExtra(CurrentRideStatusService.ERROR)){
                    String message =  intent.getStringExtra(CurrentRideStatusService.NEW_ERROR_MESSAGE);
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }
                else{
                    Ride.Status status = (Ride.Status) intent.getSerializableExtra(CurrentRideStatusService.NEW_STATUS_UPDATE);
                    if(rideStatus != status){
                        if(status == Ride.Status.Accepted){
                            rideStatus = Ride.Status.Accepted;
                            placeFragment();
                            long driverId = SettingsUtil.getUserJWT().getId();
                            currentRideFragment.setRide(driverId);
                        }
                        else{
                            if(rideStatus == Ride.Status.Accepted){
                                currentRideFragment.stopTimer();
                            }
                            rideStatus = null;
                            currentRideFragment.clearRoute();
                            removeFragment();
                        }
                    }
                }
            }
        };
        driversLocationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.hasExtra(DriversLocationService.ERROR)){
                    String message =  intent.getStringExtra(DriversLocationService.NEW_ERROR_MESSAGE);
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }
                else{
                    double[] latitudes = intent.getDoubleArrayExtra(DriversLocationService.NEW_LAT);
                    double[] longitudes = intent.getDoubleArrayExtra(DriversLocationService.NEW_LNG);
                    deleteAllPoints();
                    for(int i = 0; i < latitudes.length; ++i){
                        Point driverLocation = Point.fromLngLat(longitudes[i], latitudes[i]);
                        handler.post(() -> drawCar(driverLocation, true));
                    }
                }
            }
        };
    }

}