package com.example.shuttlemobile.driver.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.driver.fragments.home.DriverHomeAcceptanceRide;

public class DriverHome extends GenericUserFragment {
    private FragmentContainerView fragmentContainerView;
    private DriverHomeAcceptanceRide fragmentAcceptance;

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

        setSubFragment();
    }

    private void initViewElements(@NonNull View view) {
        fragmentContainerView = view.findViewById(R.id.driver_home_fragment_frame);
    }

    private void initFragments() {
        fragmentAcceptance = DriverHomeAcceptanceRide.newInstance();
    }

    private void setSubFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .replace(R.id.driver_home_fragment_frame, fragmentAcceptance);
        fragmentTransaction.addToBackStack("DriverHome");
        fragmentTransaction.commit();
    }
}