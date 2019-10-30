package edu.itagd.tgnamo.istodayagooddayto.controller;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import edu.itagd.tgnamo.istodayagooddayto.R;
import edu.itagd.tgnamo.istodayagooddayto.view.activities.ActivitiesActivity;
import edu.itagd.tgnamo.istodayagooddayto.view.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {
    final String CHANNEL_ID = "CHANNEL_NOTIFICATION";
    final int PENDING_INTENT_REQUEST_CODE = 10;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("broadcastReceived", "onReceive Accessed");
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel", NotificationManager.IMPORTANCE_DEFAULT);

        // Notification manager that will display the notification.
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder;

        // Create intent to open activity when notification is clicked
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent activitiesIntent = new Intent(context, ActivitiesActivity.class);
        activitiesIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //PendingIntent to show intent.
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, PENDING_INTENT_REQUEST_CODE, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingActivitiesIntent = PendingIntent.getActivity(context, PENDING_INTENT_REQUEST_CODE, activitiesIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build the notification components.
        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingMainIntent)
                .setContentTitle("ITAGD")
                .setContentText("Good Morning! Let's see what you can do!")
                .setAutoCancel(true)
                .addAction(R.drawable.ic_launcher_foreground, "My Activities", pendingActivitiesIntent);

        manager.notify(PENDING_INTENT_REQUEST_CODE, builder.build());
    }
}