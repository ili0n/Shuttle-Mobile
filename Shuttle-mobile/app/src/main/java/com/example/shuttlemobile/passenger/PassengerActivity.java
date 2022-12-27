package com.example.shuttlemobile.passenger;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shuttlemobile.common.GenericUserActivity;
import com.example.shuttlemobile.R;
import com.example.shuttlemobile.passenger.fragments.PassengerAccount;
import com.example.shuttlemobile.passenger.fragments.PassengerHistory;
import com.example.shuttlemobile.passenger.fragments.PassengerHome;
import com.example.shuttlemobile.common.InboxFragment;
import com.example.shuttlemobile.passenger.services.PassengerMessageService;

public class PassengerActivity extends GenericUserActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        startService(new Intent(this, PassengerMessageService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected boolean toolbarOnItemClick(MenuItem item) {
        final int itemId = item.getItemId();

        Fragment f = fragments.get(itemId);

        if (f != null) {
            setVisibleFragment(f);
        }

        return false;
    }

    @Override
    protected int getFragmentFrameId() {
        return R.id.passenger_fragment_frame;
    }

    @Override
    protected void initializeFragmentMap() {
        fragments.put(R.id.toolbar_home, PassengerHome.newInstance(session));
        fragments.put(R.id.toolbar_history, PassengerHistory.newInstance(session));
        fragments.put(R.id.toolbar_inbox, InboxFragment.newInstance(session));
        fragments.put(R.id.toolbar_account, PassengerAccount.newInstance(session));
    }

    @Override
    protected Fragment getDefaultFragment() {
        return fragments.get(R.id.toolbar_home);
    }
}