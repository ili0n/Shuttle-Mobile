package com.example.shuttlemobile.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shuttlemobile.R;

public class DriverMainActivity extends AppCompatActivity {
    final String STACK_FRAGMENTS = "DriverMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        Toolbar toolbar = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setOnMenuItemClickListener(item -> toolbarOnItemClick(item));
        //getSupportActionBar().setTitle(R.string.titleHome);
    }

    private void setVisibleFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .replace(R.id.driver_fragment_frame, fragment);
        fragmentTransaction.addToBackStack(STACK_FRAGMENTS);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack(STACK_FRAGMENTS, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else {
            super.onBackPressed();
        }
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
                break;
            case R.id.driver_main_menu_history:
                setVisibleFragment(DriverRideHistoryFragment.newInstance());
                break;
            case R.id.driver_main_menu_inbox:
                setVisibleFragment(DriverInboxFragment.newInstance());
                break;
            case R.id.driver_main_menu_account:
                setVisibleFragment(DriverAccountFragment.newInstance());
                break;
            default:
                break;
        }
        return false;
    }
}