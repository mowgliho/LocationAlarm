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
 * just holds the alarm
 */

public class AlarmIntent extends Intent {
    private final Alarm alarm;

    public static AlarmIntent getAlarmIntent(Context context, Alarm alarm, Class className) {
        return new AlarmIntent(context, alarm, className);
    }

    private AlarmIntent(Context context, Alarm alarm, Class className) {
        super(context, className);
        this.alarm = alarm;
    }

    public Alarm getAlarm() {
        return alarm;
    }
}
