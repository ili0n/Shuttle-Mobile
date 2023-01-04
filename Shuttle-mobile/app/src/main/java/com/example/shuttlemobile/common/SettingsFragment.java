package com.example.shuttlemobile.common;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.shuttlemobile.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    public static final String PREF_FILE = "ShuttleMobile.prefs.preferences";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_ROLE = "user_role";

    public static final String VAL_USER_ROLE_PASSENGER = "passenger";
    public static final String VAL_USER_ROLE_DRIVER = "driver";
}