package com.example.shuttlemobile.driver.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.fragments.PassengerAccountFavorites;
import com.example.shuttlemobile.passenger.fragments.PassengerAccountInfo;
import com.example.shuttlemobile.passenger.fragments.PassengerAccountStats;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DriverAccount extends GenericUserFragment {
    TabLayout tabLayout;
    FragmentContainerView fragContainer;
    List<Fragment> fragments = new ArrayList<>();

    public static DriverAccount newInstance(SessionContext session) {
        DriverAccount fragment = new DriverAccount();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMap();
        buildTabLayout();
        tabLayout.getTabAt(0).select();
        setVisibleFragment(fragments.get(0));
    }

    private void initMap() {
        fragments.add(DriverAccountInfo.newInstance(session));
        fragments.add(DriverAccountStats.newInstance(session));
        fragments.add(DriverAccountReports.newInstance(session));
    }

    private void buildTabLayout() {
        tabLayout = getActivity().findViewById(R.id.tab_d_acc);
        fragContainer = getActivity().findViewById(R.id.fragview_d_acc_tab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int index = tab.getPosition();
                setVisibleFragment(fragments.get(index));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    protected final void setVisibleFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .replace(fragContainer.getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}