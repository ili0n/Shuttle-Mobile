package com.example.shuttlemobile.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shuttlemobile.R;

public class DriverMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        Toolbar toolbar = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setOnMenuItemClickListener(item -> toolbarOnItemClick(item));
        getSupportActionBar().setTitle(R.string.titleHome);

        setVisibleFragment(DriverMainFragment.newInstance());
    }

    private void setVisibleFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.driver_fragment_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean toolbarOnItemClick(MenuItem item) {
        final int itemId = item.getItemId();

        switch (itemId) {
            case R.id.driver_main_menu_home:
                setVisibleFragment(DriverMainFragment.newInstance());
                getSupportActionBar().setTitle(R.string.titleHome);
                break;
            case R.id.driver_main_menu_history:
                setVisibleFragment(DriverRideHistoryFragment.newInstance());
                getSupportActionBar().setTitle(R.string.titleHistory);
                break;
            case R.id.driver_main_menu_inbox:
                setVisibleFragment(DriverInboxFragment.newInstance());
                getSupportActionBar().setTitle(R.string.titleInbox);
                break;
            case R.id.driver_main_menu_account:
                setVisibleFragment(DriverAccountFragment.newInstance());
                getSupportActionBar().setTitle(R.string.titleAccount);
                break;
            default:
                break;
        }
        return false;
    }
}