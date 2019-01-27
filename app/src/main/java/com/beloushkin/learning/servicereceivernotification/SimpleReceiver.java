package com.beloushkin.learning.servicereceivernotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class SimpleReceiver extends BroadcastReceiver {

    // NEVER DO THAT WAY !!!
    private WeakReference<TextView> mTextViewWeakReference;


    public SimpleReceiver(TextView textView) {
        mTextViewWeakReference = new WeakReference<>(textView);
    }

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

        /*
        Intent launchActivityIt = new Intent(context, TempActivity.class);
        launchActivityIt.putExtra(CountService.TIME, time);
        context.startActivity(launchActivityIt);
        */

        TextView textView = mTextViewWeakReference.get();
        StringBuilder sbText = new StringBuilder(textView.getText().toString());
        sbText.append("Time is : " + time).append("\n");
        textView.setText(sbText.toString());

    }


}
