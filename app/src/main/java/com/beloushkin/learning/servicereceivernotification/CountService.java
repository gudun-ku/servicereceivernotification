package com.beloushkin.learning.servicereceivernotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountService extends Service {

    public static final String TAG = CountService.class.getSimpleName();
    public static final String TIME = "TIME";
    private ScheduledExecutorService mScheduledExecutorService;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotificationBuilder;

    public CountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not a bound service
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        mScheduledExecutorService = Executors.newScheduledThreadPool(1);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationBuilder = getNotificationBuilder();

        mNotificationBuilder.setContentTitle("Count service notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        startForeground(123,getNotification("Start service"));

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currTime = System.currentTimeMillis();
                Log.d(TAG, "run: " + currTime);


                mNotificationManager.notify(123,getNotification(" time is " + currTime));
                Intent simpleIntent = new Intent(SimpleReceiver.SIMPLE_ACTION);
                simpleIntent.putExtra(TIME, currTime);
                sendBroadcast(simpleIntent);
            }
        }, 1000, 4000, TimeUnit.MILLISECONDS);


        return START_STICKY;
    }

    private Notification getNotification( String text) {
        return mNotificationBuilder.setContentText(text)
                .build();
    }

    @Override
    public void onDestroy() {
        //!!! stop the executor service!!!
        mScheduledExecutorService.shutdownNow();
        Log.d(TAG, "onDestroy: ");
    }


    private NotificationCompat.Builder getNotificationBuilder() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return new NotificationCompat.Builder(this);
        } else {
            String channelId = getString(R.string.cs_channel_id);
            if(mNotificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel channel =
                        new NotificationChannel(channelId,
                                "Count service", NotificationManager.IMPORTANCE_LOW);
            }

            return  new NotificationCompat.Builder(this, getString(R.string.cs_channel_id));
        }
    }


}
