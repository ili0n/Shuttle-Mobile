package com.example.shuttlemobile.passenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shuttlemobile.BlankFragment;
import com.example.shuttlemobile.GenericUserActivity;
import com.example.shuttlemobile.R;

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
        switch (itemId) {
            case R.id.toolbar_home:
                setVisibleFragment(BlankFragment.newInstance());
                break;
            case R.id.toolbar_history:
                setVisibleFragment(BlankFragment.newInstance());
                break;
            case R.id.toolbar_inbox:
                setVisibleFragment(BlankFragment.newInstance());
                break;
            case R.id.toolbar_account:
                setVisibleFragment(BlankFragment.newInstance());
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public int getFragmentFrameId() {
        return R.id.passenger_fragment_frame;
    }
}