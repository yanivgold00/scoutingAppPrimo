package com.a4586.primo.primoscoutingapp;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Executor;

public class GameNotificationService extends Service {

    private final IBinder mBinder = new ServiceBinder(); // Used to binned the service to the activity

    boolean ran = false; // Don't spam the notification. If notification was already sent
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context = this;

    public GameNotificationService() {

    }

    // Enable to bind the service the activity
    public class ServiceBinder extends Binder {
        // Gets service.
        GameNotificationService getService() {
            return GameNotificationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    //Creating the basic player
    @Override
    public void onCreate() {
        super.onCreate();


    }

    public void checkEvent() {

            db.collection("Games").addSnapshotListener( new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    try {
                        int lastGame = documentSnapshots.size();
                        if (lastGame == 2) {
                            NotificationCompat.Builder builder =
                                    new NotificationCompat.Builder(context, "M_CH_ID")
                                            .setSmallIcon(R.mipmap.app_logo)
                                            .setContentTitle("next game you scout")
                                            .setContentText("game " + lastGame + " happend right now. your shift begins now!!!!!1!!!!");
                            int NOTIFICATION_ID = 12346;
                            Intent targetIntent = new Intent(context, ScoutingChooseActivity.class);
                            PendingIntent contentIntent = PendingIntent.getActivity(context , 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(contentIntent);
                            NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            nManager.notify(NOTIFICATION_ID, builder.build());
                            ran = true;

                        }


                        if (ran) {
                            stopService(SplashActivity.gameService);
                        }
                    }
                    catch (Exception e1) {
                        Log.d("TAG", "e");
                    }
                }
            });


    }

    //Starting the player
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkEvent();
        return START_STICKY;
    }



}
