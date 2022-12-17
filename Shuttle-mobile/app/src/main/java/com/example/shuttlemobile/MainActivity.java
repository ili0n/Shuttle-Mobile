package com.example.shuttlemobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.shuttlemobile.passenger.PassengerActivity;
import com.example.shuttlemobile.unregistered.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSION_LOCATION = 1;

    private boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                checkForPermissions();
                exitSplashScreen();
            }
        }, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                    finishAndRemoveTask();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Check and ask for all permissions that are required ahead-of-time.
     * If the user declines any permission, the app will close.
     */
    private void checkForPermissions() {
        checkForLocationPermission();
        // checkForInternetPermission(); TODO
    }

    private void checkForLocationPermission() {
        if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        && !hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            String[] permissions = new String[] {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            };

            ActivityCompat.requestPermissions(this, permissions, PERMISSION_LOCATION);
        }
    }

    private void exitSplashScreen() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}