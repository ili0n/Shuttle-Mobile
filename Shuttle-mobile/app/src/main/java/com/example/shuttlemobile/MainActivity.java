package com.example.shuttlemobile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLocationListener();
        initLocationManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableLocationListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableLocationListening();
    }

    private void initLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (location != null) {
                    Log.e("Location", location.toString());
                } else {
                    Log.e("Location", "null");
                }
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }
        };
    }

    private void initLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("", "No location permission, asking");
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            }, 13579);
            return;
        }
        createLocationRequest();
    }

    @SuppressLint("MissingPermission")
    private void createLocationRequest() {
        Log.e("", "createLocationRequest()");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000L, (float) 0.1, locationListener);
        enableLocationListening();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e("", "onRequestPermissionsResult()");
        if (requestCode == 13579) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                for (String perm : permissions) {
                    if (perm.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Log.e("PERMISSION_GRANTED", "Manifest.permission.ACCESS_FINE_LOCATION");
                        createLocationRequest();
                    } else if (perm.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        Log.e("PERMISSION_GRANTED", "Manifest.permission.ACCESS_COARSE_LOCATION");
                        createLocationRequest();
                    }
                }
            }
        }
    }

    private void enableLocationListening() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it.");
            alertDialog.setPositiveButton("Location Settings", (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            alertDialog.create().show();
        }
    }

    private void disableLocationListening() {
        locationManager.removeUpdates(locationListener);
    }
}