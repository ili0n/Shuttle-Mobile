package com.example.shuttlemobile.passenger.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PassengerAccount fragment contains a <code>TabLayout</code> with 3 tabs:
 * <br/>
 * <ol>
 *     <li>Account Info</li>
 *     <li>Favorite Routes</li>
 *     <li>Statistics</li>
 * </ol>
 * Each of those is a separate fragment shown in <code>fragContainer</code>
 * and stored in the <code>fragments</code> list.
 */
public class PassengerAccount extends GenericUserFragment {
    TabLayout tabLayout;
    FragmentContainerView fragContainer;
    List<Fragment> fragments = new ArrayList<>();

    public static PassengerAccount newInstance(SessionContext session) {
        PassengerAccount fragment = new PassengerAccount();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMap();
        buildTabLayout();
        tabLayout.getTabAt(0).select();
        setVisibleFragment(fragments.get(0));
    }

    private void initMap() {
        // We can't use TabItem id's here since they technically don't have IDs.
        // getId() would always return -1. Therefore we use getPosition() and lists.
        // Be careful about the element order.
        fragments.add(PassengerAccountInfo.newInstance(session));
        fragments.add(PassengerAccountFavorites.newInstance(session));
        fragments.add(PassengerAccountStats.newInstance(session));
    }

    private void buildTabLayout() {
        tabLayout = getActivity().findViewById(R.id.tab_p_acc);
        fragContainer = getActivity().findViewById(R.id.fragview_tab);

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