package com.example.shuttlemobile.driver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.shuttlemobile.ride.IRideService;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentRideStatusService extends Service {

    private ScheduledExecutorService executor;
    private LocalBroadcastManager broadcaster;

    static final public String PREFIX = "CURRENT_RIDE_DRIVER_FINISHED_RIDE_";

    static final public String RESULT = PREFIX + "PROCESSED";
    static final public String ERROR = PREFIX + "ERROR";
    static final public String NEW_ERROR_MESSAGE = PREFIX + "FETCHING_ERROR_MESSAGE";
    static final public String NEW_STATUS_UPDATE = PREFIX + "STATUS_MESSAGE";
    static final public String DRIVER_ID = PREFIX + "driver id";

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();

    private final IRideService rideService = retrofit.create(IRideService.class);

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    public void sendResult(Ride.Status status) {
        Intent intent = new Intent(RESULT);
        intent.putExtra(NEW_STATUS_UPDATE, status);
        broadcaster.sendBroadcast(intent);
    }

    public void sendError(String message) {
        Intent intent = new Intent(ERROR);
        intent.putExtra(NEW_ERROR_MESSAGE, message);
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
            Call<RideDTO> call = rideService.getActiveRide(driverId);
            call.enqueue(new Callback<RideDTO>() {
                @Override
                public void onResponse(Call<RideDTO> call, Response<RideDTO> response) {
                    if(response.isSuccessful()){
                        RideDTO vehicle = response.body();
                        if (vehicle != null) {
                            sendResult(vehicle.getStatus());
                        }
                        else{
                            sendResult(null);
                        }
                    }
                    else if(response.code() == 404){
                        sendResult(null);
                    }
                    else{
                         sendError(response.message());
                    }
                }

                @Override
                public void onFailure(Call<RideDTO> call, Throwable t) {
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