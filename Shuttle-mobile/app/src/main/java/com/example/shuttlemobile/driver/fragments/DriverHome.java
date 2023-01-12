package com.example.shuttlemobile.driver.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.shuttlemobile.BlankFragment;
import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.driver.fragments.home.DriverCurrentRide;
import com.example.shuttlemobile.driver.fragments.home.DriverHomeAcceptanceRide;
import com.example.shuttlemobile.driver.services.CurrentRideStatusService;
import com.example.shuttlemobile.driver.services.DriverRideService;
import com.example.shuttlemobile.driver.services.DriversLocationService;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.ride.dto.VehicleLocationDTO;
import com.example.shuttlemobile.user.IUserService;
import com.example.shuttlemobile.util.SettingsUtil;
import com.mapbox.geojson.Point;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverHome extends GenericUserMapFragment {
    private boolean initiallyMovedToLocation = false;
    private FragmentContainerView fragmentContainerView;
    private BroadcastReceiver rideReceiver;
    private BroadcastReceiver isActiveReceiver;
    private Switch activeSwitch;

    private BroadcastReceiver rideStatusReceiver;
    private BroadcastReceiver driversLocationReceiver;

    private BlankFragment blankFragment;
    private DriverCurrentRide currentRideFragment = new DriverCurrentRide();
    private DriverHomeAcceptanceRide fragmentAcceptance;

    private Fragment currentFragment = null;
    private RideDTO lastDto = null;

    public static DriverHome newInstance(SessionContext session) {
        DriverHome fragment = new DriverHome();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCurrentFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .replace(R.id.driver_home_fragment_frame_home, fragment);
        fragmentTransaction.addToBackStack("DriverHome");
        fragmentTransaction.commit();
        currentFragment = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
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
        //Handler handler = new Handler();
        rideStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                if (intent.hasExtra(CurrentRideStatusService.ERROR)) {
//                    String message =  intent.getStringExtra(CurrentRideStatusService.NEW_ERROR_MESSAGE);
//                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
//                } else {
//                    String status = intent.getStringExtra(CurrentRideStatusService.NEW_STATUS_UPDATE);
//
//                    /*
//                    if status changed
//                        if accepted
//                            OPEN ACCEPTED
//                        else
//                            if current one was accepted
//
//
//                    */
//
//                    if (lastDto.getStatus() != status) {
//                        if (status == Ride.Status.Accepted)  {
//                            rideStatus = Ride.Status.Accepted;
//                            placeFragment();
//                            long driverId = SettingsUtil.getUserJWT().getId();
//                            currentRideFragment.setRide(driverId);
//                        } else {
//                            if (rideStatus == Ride.Status.Accepted) {
//                                currentRideFragment.stopTimer();
//                            }
//                            rideStatus = null;
//                            currentRideFragment.clearRoute();
//                            removeFragment();
//                        }
//                    }
//                }
            }
        };
        driversLocationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                if(intent.hasExtra(DriversLocationService.ERROR)){
//                    String message =  intent.getStringExtra(DriversLocationService.NEW_ERROR_MESSAGE);
//                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
//                }
//                else{
//                    List<VehicleLocationDTO> locations =
//                            (List<VehicleLocationDTO>) intent.getSerializableExtra(DriversLocationService.NEW_VEHICLES_LOCATIONS);
//                    deleteAllPoints();
//                    for(VehicleLocationDTO dto: locations){
//                        LocationDTO location = dto.getLocation();
//                        Point driverLocation = Point.fromLngLat(location.getLongitude(), location.getLatitude());
//                        handler.post(() -> drawCar(driverLocation, dto.getAvailable()));
//                    }
//                }
            }
        };
    }

    @Override
    public int getMapViewID() {
        return R.id.map_driver_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewElements(view);
        initFragments();

        initRideReceiver();
        initIsActiveReceiver();
        determineSubFragment(null);

        setPullingLocation();
    }

    private void initViewElements(@NonNull View view) {
        fragmentContainerView = view.findViewById(R.id.driver_home_fragment_frame_home);
        activeSwitch = view.findViewById(R.id.switch_is_active);
        initSwitchToggle();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(rideReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rideReceiver != null) {
            subscribeToRideReceiver();
        }
    }

    private void initSwitchToggle() {
        activeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isPressed() == false)
                    return;

                if (isChecked) {
                    IUserService.service.setActive(SettingsUtil.getUserJWT().getId()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {}

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.e("REST ERROR", t.toString());
                        }
                    });
                } else {
                    IUserService.service.setInactive(SettingsUtil.getUserJWT().getId()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {}

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.e("REST ERROR", t.toString());
                        }
                    });
                }
            }
        });
    }

    private void initFragments() {
        blankFragment = BlankFragment.newInstance();
        fragmentAcceptance = DriverHomeAcceptanceRide.newInstance();
    }

    private void setSubFragmentIfDifferent(Fragment fragment) {
        if (currentFragment != fragment) {
            setSubFragment(fragment);
        }
    }

    private void setSubFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom)
                .setReorderingAllowed(true)
                .replace(R.id.driver_home_fragment_frame_home, fragment);
        fragmentTransaction.addToBackStack("DriverHome");
        fragmentTransaction.commit();
        currentFragment = fragment;
    }

    private void initRideReceiver() {
        rideReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                RideDTO rideDTO = (RideDTO)intent.getSerializableExtra(DriverRideService.INTENT_RIDE_KEY);
                onGetRide(rideDTO);
            }
        };

        subscribeToRideReceiver();
    }

    private void subscribeToRideReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DriverRideService.BROADCAST_CHANNEL);
        getActivity().registerReceiver(rideReceiver, intentFilter);
    }

    private void initIsActiveReceiver() {
        isActiveReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean isActive = intent.getBooleanExtra(DriverRideService.INTENT_IS_ACTIVE_KEY, false);
                onGetIsActive(isActive);
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DriverRideService.ACTIVE_CHANNEL);
        getActivity().registerReceiver(isActiveReceiver, intentFilter);
    }

    private void onGetIsActive(Boolean isActive) {
        activeSwitch.setChecked(isActive);
    }

    private void onGetRide(RideDTO dto) {
        determineSubFragment(dto);
    }

    private void determineSubFragment(RideDTO dto) {
        if (dto == null) {
            setSubFragmentIfDifferent(blankFragment);
            removeRoute();
            return;
        }

        Ride.State state = Ride.State.valueOf(dto.getStatus().toUpperCase());

        switch (state) {
            case PENDING:
                setSubFragmentIfDifferent(fragmentAcceptance);
                break;
            case STARTED: case ACCEPTED:
                setSubFragmentIfDifferent(currentRideFragment); // TODO: Use fragmentCurrentRide.
                break;
            case CANCELED: case FINISHED: case REJECTED:
                setSubFragmentIfDifferent(blankFragment);
                removeRoute();
                break;
            default:
                throw new IllegalStateException("Unsupported state: " + state);
        }
    }
}