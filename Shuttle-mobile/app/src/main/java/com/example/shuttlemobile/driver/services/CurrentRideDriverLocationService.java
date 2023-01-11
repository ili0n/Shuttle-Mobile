package com.example.shuttlemobile.driver.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.driver.fragments.DriverCurrentRide;
import com.example.shuttlemobile.driver.fragments.DriverHome;
import com.example.shuttlemobile.ride.IRideService;
import com.example.shuttlemobile.ride.dto.VehicleDTO;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;
import com.example.shuttlemobile.vehicle.IVehicleService;
import com.mapbox.geojson.Point;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentRideDriverLocationService extends Service {

    private ScheduledExecutorService executor;
    private LocalBroadcastManager broadcaster;

    static final public String RESULT = "CURRENT_RIDE_DRIVER_LOCATION_PROCESSED";
    static final public String NEW_LAT = "LAT_MESSAGE";
    static final public String NEW_LNG = "LNG_MESSAGE";
    static final public String DRIVER_ID = "driver id";


    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();

    private final IVehicleService vehicleService = retrofit.create(IVehicleService.class);

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    public void sendResult(double lat, double lng) {
        Intent intent = new Intent(RESULT);
        intent.putExtra(NEW_LAT, lat);
        intent.putExtra(NEW_LNG, lng);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long driverId =  intent.getExtras().getLong(DRIVER_ID);
        startExecutor(driverId);
        return super.onStartCommand(intent, flags, startId);
    }

    private void startExecutor(long driverId){
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            Call<VehicleDTO> call = vehicleService.getDriverLocation(driverId);
            call.enqueue(new Callback<VehicleDTO>() {
                @Override
                public void onResponse(Call<VehicleDTO> call, Response<VehicleDTO> response) {
                    if(response.isSuccessful()){
                        VehicleDTO vehicle = response.body();
                        double lng = vehicle.getCurrentLocation().getLongitude();
                        double lat = vehicle.getCurrentLocation().getLatitude();
                        sendResult(lat, lng);
                    }
                    else{
//                        Toast.makeText(getActivity(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<VehicleDTO> call, Throwable t) {
//                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }, 0, 1, TimeUnit.SECONDS);
    }

    private void stopExecutor(){
        executor.shutdownNow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopExecutor();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}