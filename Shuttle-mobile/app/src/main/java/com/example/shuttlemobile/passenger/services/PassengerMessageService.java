package com.example.shuttlemobile.passenger.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.message.Message;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.util.NotificationUtil;

import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PassengerMessageService extends Service {
    private ExecutorService executorService;
    public PassengerMessageService() {
    }

    @Override
    public void onCreate() {
        executorService = Executors.newSingleThreadExecutor();
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
        Log.e("PassengerMessageService", "fetchNewMessages()");

        //Message newMessage = new Message();
        //newMessage.setSender(new Passenger());
        //newMessage.setMessage("Hello PassengerMessageService::fetchNewMessages()");

        // If I'm the recipient of this message, send a notification.
        //if (newMessage.getRecipient() == session.getUser()) {
        //sendNotification(newMessage);
        //}

        Intent intent = new Intent(NotificationUtil.PASSENGER_NOTIFICATION_CHANNEL_ID);
        sendBroadcast(intent);
    }

    private void sendNotification(Message newMessage) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NotificationUtil.PASSENGER_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(newMessage.getSender().getName())
                .setContentText(newMessage.getMessage())
                .setSmallIcon(R.drawable.ic_baseline_inbox_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(112213, builder.build());
    }
}