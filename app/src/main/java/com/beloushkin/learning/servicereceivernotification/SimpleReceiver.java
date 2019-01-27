package com.beloushkin.learning.servicereceivernotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SimpleReceiver extends BroadcastReceiver {

    //action for filter

    public static final String SIMPLE_ACTION =
            "com.beloushkin.learning.servicereceivernotification.SIMPLE_ACTION";

    private Toast mToast;

    private void showToast(Context context, String msg) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        long time = intent.getLongExtra(CountService.TIME,0L);
        showToast(context, "current time is : " + time);

    }


}
