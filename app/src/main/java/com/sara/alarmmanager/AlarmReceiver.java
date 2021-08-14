package com.sara.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder notification = notificationHelper.getChannelNotification(title, description);
        notificationHelper.getManager().notify(1, notification.build());
    }
}
