package com.example.btrbudget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notifications extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Set intent (go to login screen when notif clicked)
        Intent notifIntent = new Intent(context, Login.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT );

        // Build Notification builder object
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notify")
                // Icon
                .setSmallIcon(R.drawable.unknown)
                // Title
                .setContentTitle("Budget Report" )
                // Text
                .setContentText("Make sure to check your budget today!")
                // Priority
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Action (Intent)
                .setContentIntent( pendingIntent )
                // Notification closes when clicked
                .setAutoCancel( true );

        // Set up notification manager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Make the notification appear
        notificationManager.notify(200, builder.build() );

    }
}
