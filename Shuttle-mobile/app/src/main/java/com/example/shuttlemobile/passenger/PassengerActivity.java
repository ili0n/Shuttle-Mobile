package com.example.shuttlemobile.passenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shuttlemobile.BlankFragment;
import com.example.shuttlemobile.GenericUserActivity;
import com.example.shuttlemobile.R;
import com.example.shuttlemobile.passenger.fragments.PassengerAccount;
import com.example.shuttlemobile.passenger.fragments.PassengerHistory;
import com.example.shuttlemobile.passenger.fragments.PassengerHome;
import com.example.shuttlemobile.passenger.fragments.PassengerInbox;

import java.util.HashMap;
import java.util.Map;

public class PassengerActivity extends GenericUserActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
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

        if (f == null) {

        } else {
            setVisibleFragment(f);
        }

        return false;
    }

    @Override
    public int getFragmentFrameId() {
        return R.id.passenger_fragment_frame;
    }

    @Override
    protected void initializeFragmentMap() {
        fragments.put(R.id.toolbar_home, PassengerHome.newInstance());
        fragments.put(R.id.toolbar_history, PassengerHistory.newInstance());
        fragments.put(R.id.toolbar_inbox, PassengerInbox.newInstance());
        fragments.put(R.id.toolbar_account, PassengerAccount.newInstance());
    }

    @Override
    protected Fragment getDefaultFragment() {
        return fragments.get(R.id.toolbar_home);
    }
}