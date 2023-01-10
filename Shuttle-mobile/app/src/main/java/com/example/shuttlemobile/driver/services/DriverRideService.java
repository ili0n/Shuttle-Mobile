package com.example.shuttlemobile.driver.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shuttlemobile.ride.IRideService;
import com.example.shuttlemobile.ride.RideDTO;
import com.example.shuttlemobile.unregistered.login.ILoginService;
import com.example.shuttlemobile.unregistered.login.LoginDTO;
import com.example.shuttlemobile.unregistered.login.TokenDTO;
import com.example.shuttlemobile.user.JWT;
import com.example.shuttlemobile.util.SettingsUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverRideService extends Service {
    public DriverRideService() {
    }

    @Override
    public void onCreate() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        final int delay = 3000;

        executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchNewMessages();
                            handler.postDelayed(this, delay);
                        }
                    }, delay);
                }
            }
        );
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void fetchNewMessages() {
        final JWT jwt = SettingsUtil.getUserJWT();
        Call<RideDTO> call = IRideService.service.getActiveRideDriver(jwt.getId());
        call.enqueue(new Callback<RideDTO>() {
            @Override
            public void onResponse(Call<RideDTO> call, Response<RideDTO> response) {
                RideDTO ride = response.body();
                if (ride == null) {
                    Log.e("DRIVER RIDE", "null");
                } else {
                    Log.e("DRIVER RIDE", ride.toString());
                }
            }

            @Override
            public void onFailure(Call<RideDTO> call, Throwable t) {
                Log.e("REST ERROR", t.toString());
            }
        });
    }
}