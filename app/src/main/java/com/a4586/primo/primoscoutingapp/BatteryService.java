package com.a4586.primo.primoscoutingapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by yaniv on 24/03/2018.
 */

//Low battery notification control
public class BatteryService extends BroadcastReceiver {

    boolean ran = false; // Don't spam the notification. If notification was already sent

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ran) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0); // Getting the level
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 1);
            Log.d("TAG",plugged + "");
            if (level < 90 && plugged == 0) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context, "M_CH_ID")
                                .setSmallIcon(R.mipmap.app_logo)
                                .setContentTitle("Battery Low")
                                .setContentText("Battery is under 90% load before your next game");
                int NOTIFICATION_ID = 12345;
                Intent targetIntent = new Intent(context, ScoutingChooseActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);
                NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nManager.notify(NOTIFICATION_ID, builder.build());
                ran = true;
            }
        }
    }
}



