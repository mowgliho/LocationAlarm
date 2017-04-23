package com.locationalarm.backend.intent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.locationalarm.R;
import com.locationalarm.backend.alarm.Alarm;

/**
 * Created by Brent on 4/23/2017.
 */


public class SchedulingAlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Alarm alarm = ((AlarmIntent) intent).getAlarm();
        Intent alarmIntent = AlarmIntent.getAlarmIntent(context,alarm, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, R.integer.alarm_manager_set_request,alarmIntent,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                alarm.getUpdateInterval(),
                pendingIntent);
    }
}