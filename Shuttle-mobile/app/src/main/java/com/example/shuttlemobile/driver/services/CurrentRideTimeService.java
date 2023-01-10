package com.example.shuttlemobile.driver.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.shuttlemobile.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CurrentRideTimeService extends Service {

    private ScheduledExecutorService executor;
    private LocalDateTime startTime;
    private LocalBroadcastManager  broadcaster;

    static final public String RESULT = "TIME_PROCESSED";
    static final public String NEW_TIME_MESSAGE = "TIME_MESSAGE";
    static final public String TIME_START = "time start";

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    public void sendResult(String message) {
        Intent intent = new Intent(RESULT);
        if(message != null)
            intent.putExtra(NEW_TIME_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String startTimeStr =  intent.getExtras().getString(TIME_START);
        startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ISO_DATE_TIME);
        startExecutor();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startExecutor(){
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            long secondsTotal = ChronoUnit.SECONDS.between(startTime, LocalDateTime.now());
            @SuppressLint("DefaultLocale") String result = String.format("%d:%02d:%02d",
                    secondsTotal / 3600, (secondsTotal % 3600) / 60, secondsTotal % 60);
            sendResult(getResources().getString(R.string.elapsed_time) + result);
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