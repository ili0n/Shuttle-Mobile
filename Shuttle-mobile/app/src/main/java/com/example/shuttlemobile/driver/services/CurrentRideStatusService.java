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

public class CurrentRideStatusService extends PullingService {

    static final public String PREFIX = "CURRENT_RIDE_DRIVER_FINISHED_RIDE_";

    static final public String RESULT = PREFIX + "PROCESSED";
    static final public String ERROR = PREFIX + "ERROR";
    static final public String NEW_ERROR_MESSAGE = PREFIX + "FETCHING_ERROR_MESSAGE";
    static final public String NEW_STATUS_UPDATE = PREFIX + "STATUS_MESSAGE";
    static final public String DRIVER_ID = PREFIX + "driver id";

    public void sendResult(String s) {
        //Ride.Status status = Ride.Status.valueOf(s.toUpperCase());
        Intent intent = new Intent(RESULT);
        intent.putExtra(NEW_STATUS_UPDATE, s);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    protected void startExecutor(Intent intent){
        long driverId =  intent.getExtras().getLong(DRIVER_ID);
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            Call<RideDTO> call = IRideService.service.getActiveRide(driverId);
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
                         sendError(ERROR, NEW_ERROR_MESSAGE, response.message());
                    }
                }

                @Override
                public void onFailure(Call<RideDTO> call, Throwable t) {
                    sendError(ERROR, NEW_ERROR_MESSAGE, t.getMessage());
                }
            });

        }, 0, 2, TimeUnit.SECONDS);
    }
}