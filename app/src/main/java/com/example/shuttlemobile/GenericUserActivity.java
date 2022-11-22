package com.example.shuttlemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

/**
 * <p>
 *     GenericUserActivity is an empty abstract activity with an empty toolbar.<br/>
 *     The toolbar has a back button.
 *     The toolbar is initialized in <b>onAttachedToWindow()</b> instead of onCreate().
 * </p>
 * <p>
 *     Override <b>toolbarOnItemClick()</b> for custom toolbar events.
 *     <br/>
 *     Override <b>onCreateOptionsMenu()</b> to add toolbar buttons from a menu resource.
 * </p>
 */
public class GenericUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_user);
    }

    @Override
    public void onAttachedToWindow() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnMenuItemClickListener(item -> toolbarOnItemClick(item));
        getSupportActionBar().setTitle("");
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

    protected boolean toolbarOnItemClick(MenuItem item) {
        final int itemId = item.getItemId();
        return false;
    }
}