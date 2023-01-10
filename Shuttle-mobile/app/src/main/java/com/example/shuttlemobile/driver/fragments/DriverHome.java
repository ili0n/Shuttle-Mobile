package com.example.shuttlemobile.driver.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.shuttlemobile.BlankFragment;
import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.driver.fragments.home.DriverHomeAcceptanceRide;
import com.example.shuttlemobile.driver.fragments.home.DriverHomeJustMap;
import com.example.shuttlemobile.driver.services.DriverRideService;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.ride.RideDTO;

import java.util.Locale;

public class DriverHome extends GenericUserFragment {
    private FragmentContainerView fragmentContainerView;
    private BroadcastReceiver rideReceiver;

    private DriverHomeJustMap fragmentJustMap;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewElements(view);
        initFragments();

        initRideReceiver();
        determineSubFragment(null);
    }

    private void initViewElements(@NonNull View view) {
        fragmentContainerView = view.findViewById(R.id.driver_home_fragment_frame);
    }

    private void initFragments() {
        fragmentJustMap = DriverHomeJustMap.newInstance();
        fragmentAcceptance = DriverHomeAcceptanceRide.newInstance();
    }

    private void setSubFragmentIfDifferent(Fragment fragment) {
        if (currentFragment != fragment) {
            setSubFragment(fragment);
        }
    }

    private void setSubFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .replace(R.id.driver_home_fragment_frame, fragment);
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

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DriverRideService.BROADCAST_CHANNEL);
        getActivity().registerReceiver(rideReceiver, intentFilter);
    }

    private void onGetRide(RideDTO dto) {
        determineSubFragment(dto);
    }

    private void determineSubFragment(RideDTO dto) {
        if (dto == null) {
            setSubFragmentIfDifferent(fragmentJustMap);
            return;
        }

        Ride.State state = Ride.State.valueOf(dto.getStatus().toUpperCase());

        switch (state) {
            case PENDING:
                setSubFragmentIfDifferent(fragmentAcceptance);
                break;
            case STARTED: case ACCEPTED:
                setSubFragmentIfDifferent(fragmentAcceptance); // TODO: Use fragmentCurrentRide.
                break;
            case CANCELED: case FINISHED: case REJECTED:
                setSubFragmentIfDifferent(fragmentJustMap);
                break;
            default:
                throw new IllegalStateException("Unsupported state: " + state);
        }
    }
}