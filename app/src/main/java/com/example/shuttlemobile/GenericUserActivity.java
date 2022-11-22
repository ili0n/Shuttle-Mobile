package com.example.shuttlemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
 *     <br/>
 *     Override <b>getFragmentFrameId()</b> to identify the fragment frame for your activity.
 * </p>
 */
public abstract class GenericUserActivity extends AppCompatActivity {
    private final String STACK_FRAGMENTS = "UserActivityFragment";

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

    /**
     * @return The ID of this activity's fragment frame.
     */
    protected abstract int getFragmentFrameId();
    
    protected final void setVisibleFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .replace(getFragmentFrameId(), fragment);
        fragmentTransaction.addToBackStack(STACK_FRAGMENTS);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack(STACK_FRAGMENTS, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }
}