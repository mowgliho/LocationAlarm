package com.locationalarm.backend.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.locationalarm.R;
import com.locationalarm.backend.intent.AlarmIntent;
import com.locationalarm.backend.intent.SchedulingAlarmBroadcastReceiver;

/**
 * Created by Brent on 4/23/2017.
 */

public class AlarmOnce extends Alarm {
    private final long startTime;

    public AlarmOnce(
            long startTime,
            String displayName,
            double triggerDistance,
            long updateInterval,
            double longitude, double latitude,
            String[] providers) {
        super(displayName,triggerDistance,updateInterval,longitude,latitude, providers);
        this.startTime = startTime;
    }

    @Override
    public void setAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,startTime,getPendingIntent(context));
    }
}
