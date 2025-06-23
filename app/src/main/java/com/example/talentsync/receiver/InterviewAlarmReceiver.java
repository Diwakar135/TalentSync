package com.example.talentsync.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class InterviewAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        Toast.makeText(context, "Interview Reminder: " + title, Toast.LENGTH_LONG).show();
        // For real notifications, integrate NotificationManager here
    }
}
