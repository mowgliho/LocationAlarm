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
public abstract class Alarm {
    private final String displayName;
    private final double triggerDistance;
    private final long updateInterval;
    private final double longitude, latitude;
    private final String[] providers;


    protected Alarm(String displayName, double triggerDistance, long updateInterval, double longitude, double latitude, String[] providers) {
        this.displayName = displayName;
        this.triggerDistance = triggerDistance;
        this.updateInterval = updateInterval;
        this.longitude = longitude;
        this.latitude = latitude;
        this.providers = providers;
    }

    public String getHashString() {
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getTriggerDistance() {
        return triggerDistance;
    }

    public long getUpdateInterval() {
        return updateInterval;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String[] getProviders() {
        return providers;
    }

    /*
    Get the intent that will schedule daily alarms
     */
    protected PendingIntent getPendingIntent(Context context) {
        AlarmIntent alarmIntent = AlarmIntent.getAlarmIntent(context,this, SchedulingAlarmBroadcastReceiver.class);
        return PendingIntent.getBroadcast(
                context, R.integer.alarm_manager_set_request,alarmIntent,PendingIntent.FLAG_ONE_SHOT);
    }

    public abstract void setAlarms(Context context);

    @Override
    public String toString() {
        return displayName;
    }
}
