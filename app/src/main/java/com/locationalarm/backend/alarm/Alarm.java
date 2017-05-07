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
import com.locationalarm.util.HashCodeBuilder;

import static android.R.attr.name;

/**
 * Created by Brent on 4/23/2017.
 */
public abstract class Alarm implements Parcelable {
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

    /**
     * Equals
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Alarm) {
            if(o == null) return false;
            return ((Alarm) o).hashCode() == this.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.add(displayName);
        hashCodeBuilder.add(triggerDistance);
        hashCodeBuilder.add(updateInterval);
        hashCodeBuilder.add(longitude);
        hashCodeBuilder.add(latitude);
        for(String string : providers) {
            hashCodeBuilder.add(string);
        }
        return hashCodeBuilder.hashCode();
    }

    /*
    parcelable stubs
     */

    @Override
    public int describeContents() {
        return 0;
    }

    protected void writeToParcelStub(Parcel dest) {
        dest.writeInt(providers.length);
        dest.writeStringArray(providers);
        dest.writeString(displayName);
        dest.writeDouble(triggerDistance);
        dest.writeLong(updateInterval);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }

    protected static AlarmStub getAlarmStub(Parcel in) {
        String[] providers = new String[in.readInt()];
        in.readStringArray(providers);
        return new AlarmStub(
                in.readString(),
                in.readDouble(),
                in.readLong(),
                in.readDouble(),
                in.readDouble(),
                providers
        );
    }

    public abstract AlarmTypeBuilder getAlarmTypeBuilder();

    protected static final class AlarmStub {
        private final String displayName;
        private final double triggerDistance;
        private final long updateInterval;
        private final double longitude, latitude;
        private final String[] providers;


        private AlarmStub(String displayName, double triggerDistance, long updateInterval, double longitude, double latitude, String[] providers) {
            this.displayName = displayName;
            this.triggerDistance = triggerDistance;
            this.updateInterval = updateInterval;
            this.longitude = longitude;
            this.latitude = latitude;
            this.providers = providers;
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
    }

}
