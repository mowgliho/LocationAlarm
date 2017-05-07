package com.locationalarm.backend.alarm;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

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

    private AlarmRepeating(AlarmStub stub, long startTime, long repeatInterval) {
        this(
                startTime,
                stub.getDisplayName(),
                stub.getTriggerDistance(),
                stub.getUpdateInterval(),
                stub.getLongitude(),stub.getLatitude(),
                repeatInterval,
                stub.getProviders()
        );
    }

    @Override
    public void setAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,startTime,repeatInterval,getPendingIntent(context));
    }

    /*
    parcelable
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelStub(dest);
        dest.writeLong(startTime);
        dest.writeLong(repeatInterval);
    }

    public static final Parcelable.Creator<AlarmRepeating> CREATOR
            = new Parcelable.Creator<AlarmRepeating>() {
        @Override
        public AlarmRepeating createFromParcel(Parcel in) {
            AlarmStub alarmStub = Alarm.getAlarmStub(in);
            long startTime = in.readLong();
            long repeatInterval = in.readLong();
            return new AlarmRepeating(alarmStub,startTime,repeatInterval);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public AlarmRepeating[] newArray(int size) {
            return new AlarmRepeating[size];
        }
    };
}
