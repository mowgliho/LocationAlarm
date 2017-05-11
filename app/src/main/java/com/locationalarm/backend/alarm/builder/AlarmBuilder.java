package com.locationalarm.backend.alarm.builder;

import android.os.Parcel;
import android.os.Parcelable;

import com.locationalarm.backend.alarm.Alarm;
import com.locationalarm.backend.alarm.AlarmOnce;
import com.locationalarm.util.Constants;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Brent on 5/7/2017.
 */

public class AlarmBuilder implements Parcelable{
    private String displayName;
    private double triggerDistance;
    private long updateInterval;
    private double longitude, latitude;
    private String locationText;
    private String[] providers;
    private AlarmTypeBuilder alarmTypeBuilder;

    public AlarmBuilder(String displayName, double triggerDistance, long updateInterval,
                        double longitude, double latitude, String[] providers,
                        AlarmTypeBuilder alarmTypeBuilder, String locationText) {
        this.displayName = displayName;
        this.triggerDistance = triggerDistance;
        this.updateInterval = updateInterval;
        this.longitude = longitude;
        this.latitude = latitude;
        this.providers = providers;
        this.alarmTypeBuilder = alarmTypeBuilder;
        this.locationText = locationText;
    }

    public boolean valid() {
        return displayName != null
                && triggerDistance > 0
                && updateInterval > 0
                && providers != null
                && providers.length > 0
                && alarmTypeBuilder != null;
    }

    public static AlarmBuilder GETDEFAULT() {
        return new AlarmBuilder(null, -1, -1,0, 0, new String[]{}, null, null);
    }

    //create from alarm
    public AlarmBuilder(Alarm alarm) {
        this.displayName = alarm.getDisplayName();
        this.triggerDistance = alarm.getTriggerDistance();
        this.updateInterval = alarm.getUpdateInterval();
        this.longitude = alarm.getLongitude();
        this.latitude = alarm.getLatitude();
        this.locationText = alarm.getLocationText();
        this.providers = alarm.getProviders();
        this.alarmTypeBuilder = alarm.getAlarmTypeBuilder();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
    parcelable stubs
     */



    /*
        parcelable
         */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeDouble(triggerDistance);
        dest.writeLong(updateInterval);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeInt(providers.length);
        dest.writeStringArray(providers);
        dest.writeString(locationText);
        dest.writeParcelable(alarmTypeBuilder, Constants.ALARMTYPEBUILDERFLAG);
    }

    public static final Parcelable.Creator<AlarmBuilder> CREATOR
            = new Parcelable.Creator<AlarmBuilder>() {
        private double longitude;

        @Override
        public AlarmBuilder createFromParcel(Parcel in) {
            String displayName = in.readString();
            double triggerDistance = in.readDouble();
            long updateInterval = in.readLong();
            double longitude = in.readDouble();
            double latitude = in.readDouble();
            String[] providers = new String[in.readInt()];
            in.readStringArray(providers);
            String locationText = in.readString();
            AlarmTypeBuilder alarmTypeBuilder = in.readParcelable(AlarmTypeBuilder.class.getClassLoader());
            return new AlarmBuilder(displayName, triggerDistance, updateInterval, longitude, latitude, providers, alarmTypeBuilder, locationText);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public AlarmBuilder[] newArray(int size) {
            return new AlarmBuilder[size];
        }
    };

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }
}
