package com.locationalarm.backend.alarm;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.locationalarm.backend.alarm.builder.AlarmTypeBuilder;

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
            String[] providers,
            String locationText) {
        super(displayName,triggerDistance,updateInterval,longitude,latitude, providers, locationText);
        this.startTimes = startTimes;
    }

    private AlarmSpecific(AlarmStub stub, long[] startTimes) {
        this(
                startTimes,
                stub.getDisplayName(),
                stub.getTriggerDistance(),
                stub.getUpdateInterval(),
                stub.getLongitude(),stub.getLatitude(),
                stub.getProviders(),
                stub.getLocationText()
        );
    }

    @Override
    public void setAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for(long time : startTimes) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,time,getPendingIntent(context));
        }

    }

    /*
parcelable
 */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelStub(dest);
        dest.writeInt(startTimes.length);
        dest.writeLongArray(startTimes);
    }

    public static final Parcelable.Creator<AlarmSpecific> CREATOR
            = new Parcelable.Creator<AlarmSpecific>() {
        @Override
        public AlarmSpecific createFromParcel(Parcel in) {
            AlarmStub alarmStub = Alarm.getAlarmStub(in);
            long[] startTimes = new long[in.readInt()];
            in.readLongArray(startTimes);
            return new AlarmSpecific(alarmStub,startTimes);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public AlarmSpecific[] newArray(int size) {
            return new AlarmSpecific[size];
        }
    };

    @Override
    public AlarmTypeBuilder getAlarmTypeBuilder() {
        return new AlarmTypeBuilder.Specific(startTimes);
    }
}
