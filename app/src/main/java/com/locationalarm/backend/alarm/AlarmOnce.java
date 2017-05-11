package com.locationalarm.backend.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.locationalarm.R;
import com.locationalarm.backend.alarm.builder.AlarmTypeBuilder;
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
            String[] providers, String locationText) {
        super(displayName,triggerDistance,updateInterval,longitude,latitude, providers, locationText);
        this.startTime = startTime;
    }

    private AlarmOnce(AlarmStub stub, long startTime) {
        this(
                startTime,
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
        alarmManager.set(AlarmManager.RTC_WAKEUP,startTime,getPendingIntent(context));
    }

    @Override
    public AlarmTypeBuilder getAlarmTypeBuilder() {
        return new AlarmTypeBuilder.Once(startTime);
    }

    /*
    parcelable
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelStub(dest);
        dest.writeLong(startTime);
    }

    public static final Parcelable.Creator<AlarmOnce> CREATOR
            = new Parcelable.Creator<AlarmOnce>() {
        @Override
        public AlarmOnce createFromParcel(Parcel in) {
            AlarmStub alarmStub = Alarm.getAlarmStub(in);
            long startTime = in.readLong();
            return new AlarmOnce(alarmStub,startTime);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public AlarmOnce[] newArray(int size) {
            return new AlarmOnce[size];
        }
    };

}
