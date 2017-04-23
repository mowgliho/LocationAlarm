package com.locationalarm.backend.alarm;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by Brent on 4/23/2017.
 */

public class AlarmRepeating extends Alarm {
    private final long startTime;
    private final long repeatInterval;

    public AlarmRepeating(
            long startTime,
            String displayName,
            double triggerDistance,
            long updateInterval,
            double longitude, double latitude,
            long repeatInterval,
            String[] providers) {
        super(displayName,triggerDistance,updateInterval,longitude,latitude, providers);
        this.startTime = startTime;
        this.repeatInterval = repeatInterval;
    }

    @Override
    public void setAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,startTime,repeatInterval,getPendingIntent(context));
    }
}
