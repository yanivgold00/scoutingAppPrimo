package com.a4586.primo.primoscoutingapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;

/**
 * Created by yaniv on 24/03/2018.
 */

//Low battery notification control
public class BatteryService extends BroadcastReceiver {

    boolean ran = false; // Don't spam the notification. If notification was already sent
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!ran){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0); // Getting the level
            if(level < 50) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.app_logo)
                                .setContentTitle("Battery Low")
                                .setContentText("Battery is under 50% D:");
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



