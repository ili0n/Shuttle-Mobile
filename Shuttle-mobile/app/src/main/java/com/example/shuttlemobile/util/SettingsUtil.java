package com.example.shuttlemobile.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shuttlemobile.common.SettingsFragment;
import com.example.shuttlemobile.user.User;

public class SettingsUtil {
    public static final String PREF_FILE = "ShuttleMobile.prefs.preferences";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_SURNAME = "user_surname";
    public static final String KEY_USER_PHONE = "user_phone";
    public static final String KEY_USER_LOCATION = "user_location";
    public static final String KEY_USER_BLOCKED = "user_blocked";
    public static final String KEY_USER_ACTIVE = "user_active";
    public static final String KEY_USER_PFP = "user_pfp";
    public static final String KEY_USER_ROLE = "user_role";
    public static final String VAL_USER_ROLE_PASSENGER = "passenger";
    public static final String VAL_USER_ROLE_DRIVER = "driver";

    public static void setUser(SharedPreferences prefs, User user) {
        SharedPreferences.Editor pe = prefs.edit();

        pe.putLong(KEY_USER_ID, user.getId());
        pe.putString(KEY_USER_EMAIL, user.getEmail());
        pe.putString(KEY_USER_NAME, user.getName());
        pe.putString(KEY_USER_SURNAME, user.getLastName());
        pe.putString(KEY_USER_PHONE, user.getPhone());
        pe.putString(KEY_USER_LOCATION, user.getLocation());
        pe.putBoolean(KEY_USER_BLOCKED, user.isBlocked());
        pe.putBoolean(KEY_USER_ACTIVE, user.isActive());
        pe.putString(KEY_USER_ROLE, user.getRole().toString());
        pe.putString(KEY_USER_PFP, user.getPfp());
        pe.commit();
    }

    public static User getUser(SharedPreferences prefs) {
        Long id = prefs.getLong(KEY_USER_ID, -1);
        String email = prefs.getString(KEY_USER_EMAIL, "");
        String name = prefs.getString(KEY_USER_NAME, "");
        String lastName = prefs.getString(KEY_USER_SURNAME, "");
        String phone = prefs.getString(KEY_USER_PHONE, "");
        String location = prefs.getString(KEY_USER_LOCATION, "");
        boolean blocked = prefs.getBoolean(KEY_USER_BLOCKED, false);
        boolean active = prefs.getBoolean(KEY_USER_ACTIVE, false);
        User.Role role = User.Role.valueOf(prefs.getString(KEY_USER_ROLE, "Passenger"));
        String pfp = prefs.getString(KEY_USER_PFP, "");

        return new User(id, name, lastName, location, phone, email, "", pfp, blocked, active, role);
    }
}
