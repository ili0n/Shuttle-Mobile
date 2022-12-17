package com.example.shuttlemobile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.shuttlemobile.unregistered.LoginActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSION_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Timer().schedule(new TimerTask() {
            @SuppressLint("MissingPermission")
            @Override
            public void run() {
                checkForPermissions();
            }
        }, 3000);
    }

    private void setFusedLocationClient() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(500);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                Log.e("A", locationResult.getLastLocation().toString());
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onHasPermission();
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
     * If all permissions are accepted, continue.
     */
    private void checkForPermissions() {
        checkForLocationPermission();
    }

    private void checkForLocationPermission() {
        if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                && !hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            String[] permissions = new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            };

            ActivityCompat.requestPermissions(this, permissions, PERMISSION_LOCATION);
        } else {
            onHasPermission();
        }
    }

    private void onHasPermission() {
        setFusedLocationClient();
        exitSplashScreen();
    }

    private void exitSplashScreen() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}