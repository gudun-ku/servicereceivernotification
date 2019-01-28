package com.beloushkin.learning.servicereceivernotification;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    //  + создать сервис

    //  + добавить задачу

    //  + создать ресивер, зарегистрировать динамически
    //  + поймать системное событие
    //  +  поймать свое событие
    //
    //  +  поймать событие из сервиса
    //
    //    создать уведомление, сделать сервис foreground
    //
    //    бонус - статически объявить ресивер в манифесте
    //
    //    самостоятельно - bound services

    private Button btnStartService, btnStopService, btnSendBroadcast;
    private SimpleReceiver mSimpleReceiver;
    private String actionAirplaneModeChanged =  Intent.ACTION_AIRPLANE_MODE_CHANGED;
    private IntentFilter mIntentFilter;
    private TextView tvTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btn_start_service);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountService.class);
                startService(intent);
            }
        });


        btnStopService = findViewById(R.id.btn_stop_service);
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountService.class);
                stopService(intent);
            }
        });


        btnSendBroadcast = findViewById(R.id.btn_send_broadcast);
        btnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast(new Intent(SimpleReceiver.SIMPLE_ACTION));
            }
        });

        tvTime = findViewById(R.id.tv_time);
        mSimpleReceiver= new SimpleReceiver(tvTime);
        mIntentFilter = new IntentFilter(SimpleReceiver.SIMPLE_ACTION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mSimpleReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mSimpleReceiver);
    }
}
