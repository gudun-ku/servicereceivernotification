package com.beloushkin.learning.servicereceivernotification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountService extends Service {

    public static final String TAG = CountService.class.getSimpleName();
    public static final String TIME = "TIME";
    private ScheduledExecutorService mScheduledExecutorService;

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currTime = System.currentTimeMillis();
                Log.d(TAG, "run: " + currTime);
                Intent simpleIntent = new Intent(SimpleReceiver.SIMPLE_ACTION);
                simpleIntent.putExtra(TIME, currTime);
                sendBroadcast(simpleIntent);
            }
        }, 1000, 4000, TimeUnit.MILLISECONDS);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //!!! stop the executor service!!!
        mScheduledExecutorService.shutdownNow();
        Log.d(TAG, "onDestroy: ");
    }
}
