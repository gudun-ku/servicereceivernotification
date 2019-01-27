package com.beloushkin.learning.servicereceivernotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        long time = getIntent().getLongExtra(CountService.TIME, 0);

        tvTime = findViewById(R.id.tv_time);
        tvTime.setText("Time is " + time);


    }
}
