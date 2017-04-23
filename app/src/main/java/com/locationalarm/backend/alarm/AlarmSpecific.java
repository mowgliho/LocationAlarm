package com.locationalarm.backend.alarm;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by Brent on 4/23/2017.
 */

public class AlarmSpecific extends Alarm {
    private final long[] startTimes;

    public AlarmSpecific(
            long[] startTimes,
            String displayName,
            double triggerDistance,
            long updateInterval,
            double longitude, double latitude,
            String[] providers) {
        super(displayName,triggerDistance,updateInterval,longitude,latitude, providers);
        this.startTimes = startTimes;
    }

    @Override
    public void setAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for(long time : startTimes) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,time,getPendingIntent(context));
        }

    }
}
