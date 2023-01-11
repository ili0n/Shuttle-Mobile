package com.example.shuttlemobile.driver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.shuttlemobile.ride.dto.VehicleLocationDTO;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;
import com.example.shuttlemobile.vehicle.IVehicleService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriversLocationService extends Service {

    private ScheduledExecutorService executor;
    private LocalBroadcastManager broadcaster;

    static final public String PREFIX = "CURRENT_RIDE_DRIVER_LOCATION_";

    static final public String RESULT = PREFIX + "PROCESSED";
    static final public String ERROR = PREFIX + "ERROR";
    static final public String NEW_ERROR_MESSAGE = PREFIX + "FETCHING_ERROR_MESSAGE";
    static final public String NEW_LAT = PREFIX + "LAT_MESSAGE";
    static final public String NEW_LNG = PREFIX + "LNG_MESSAGE";
    static final public String DRIVER_ID = PREFIX + "driver id";


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

    public void sendResult(double[] latitudes, double[] longitudes) {

        if(latitudes.length != longitudes.length){
            sendError("Array sizes don't match");
        }
        else{
            Intent intent = new Intent(RESULT);
            intent.putExtra(NEW_LAT, latitudes);
            intent.putExtra(NEW_LNG, longitudes);
            broadcaster.sendBroadcast(intent);
        }
    }

    public void sendError(String message) {
        Intent intent = new Intent(ERROR);
        intent.putExtra(NEW_ERROR_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startExecutor();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startExecutor(){
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            Call<List<VehicleLocationDTO>> call = vehicleService.getDriversLocation();
            call.enqueue(new Callback<List<VehicleLocationDTO>>() {
                @Override
                public void onResponse(Call<List<VehicleLocationDTO>> call, Response<List<VehicleLocationDTO>> response) {
                    if(response.isSuccessful()){
                        double[] latitudes = response.body()
                                .stream()
                                .mapToDouble(vehicle -> vehicle.getLocation().getLatitude())
                                .toArray();

                        double[] longitudes = response.body()
                                .stream()
                                .mapToDouble(vehicle -> vehicle.getLocation().getLongitude())
                                .toArray();
                        sendResult(latitudes, longitudes);
                    }
                    else{
                        sendError(response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<VehicleLocationDTO>> call, Throwable t) {
                    sendError(t.getMessage());
                }
            });

        }, 0, 2, TimeUnit.SECONDS);
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