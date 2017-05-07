package com.locationalarm.backend.alarm.builder;

import android.os.Parcel;
import android.os.Parcelable;

import com.locationalarm.backend.alarm.Alarm;
import com.locationalarm.backend.alarm.AlarmOnce;
import com.locationalarm.backend.alarm.AlarmRepeating;
import com.locationalarm.backend.alarm.AlarmSpecific;

/**
 * Created by Brent on 5/7/2017.
 */

public interface AlarmTypeBuilder extends Parcelable {
    public Alarm buildAlarm(AlarmBuilder alarmBuilder);

    public static class Once implements AlarmTypeBuilder{
        private final long startTime;

        public Once(final long startTime) {
            this.startTime = startTime;
        }

        @Override
        public Alarm buildAlarm(AlarmBuilder alarmBuilder) {
            return new AlarmOnce(
                    startTime,
                    alarmBuilder.getDisplayName(),
                    alarmBuilder.getTriggerDistance(),
                    alarmBuilder.getUpdateInterval(),
                    alarmBuilder.getLongitude(),
                    alarmBuilder.getLatitude(),
                    alarmBuilder.getProviders()
            );
        }

        @Override
        public int describeContents() {
            return 0;
        }

        /*
                 parcelable
                  */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(startTime);
        }

        public static final Parcelable.Creator<Once> CREATOR
                = new Parcelable.Creator<Once>() {
            @Override
            public Once createFromParcel(Parcel in) {
                long startTime = in.readLong();
                return new Once(startTime);
            }

            // We just need to copy this and change the type to match our class.
            @Override
            public Once[] newArray(int size) {
                return new Once[size];
            }
        };
    }

    public static class Repeating implements AlarmTypeBuilder{
        private final long startTime, repeatInterval;

        public Repeating(final long startTime, long repeatInterval) {
            this.startTime = startTime;
            this.repeatInterval = repeatInterval;
        }

        @Override
        public Alarm buildAlarm(AlarmBuilder alarmBuilder) {
            return new AlarmRepeating(
                    startTime,
                    alarmBuilder.getDisplayName(),
                    alarmBuilder.getTriggerDistance(),
                    alarmBuilder.getUpdateInterval(),
                    alarmBuilder.getLongitude(),
                    alarmBuilder.getLatitude(),
                    repeatInterval,
                    alarmBuilder.getProviders()
            );
        }

        @Override
        public int describeContents() {
            return 0;
        }

        /*
                 parcelable
                  */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(startTime);
            dest.writeLong(repeatInterval);
        }

        public static final Parcelable.Creator<Repeating> CREATOR
                = new Parcelable.Creator<Repeating>() {
            @Override
            public Repeating createFromParcel(Parcel in) {
                long startTime = in.readLong();
                long repeatInterval = in.readLong();
                return new Repeating(startTime, repeatInterval);
            }

            // We just need to copy this and change the type to match our class.
            @Override
            public Repeating[] newArray(int size) {
                return new Repeating[size];
            }
        };
    }

    public static class Specific implements AlarmTypeBuilder{
        private final long[] startTimes;

        public Specific(final long startTimes[]) {
            this.startTimes = startTimes;
        }

        @Override
        public Alarm buildAlarm(AlarmBuilder alarmBuilder) {
            return new AlarmSpecific(
                    startTimes,
                    alarmBuilder.getDisplayName(),
                    alarmBuilder.getTriggerDistance(),
                    alarmBuilder.getUpdateInterval(),
                    alarmBuilder.getLongitude(),
                    alarmBuilder.getLatitude(),
                    alarmBuilder.getProviders()
            );
        }

        @Override
        public int describeContents() {
            return 0;
        }

        /*
                 parcelable
                  */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(startTimes.length);
            dest.writeLongArray(startTimes);
        }

        public static final Parcelable.Creator<Specific> CREATOR
                = new Parcelable.Creator<Specific>() {
            @Override
            public Specific createFromParcel(Parcel in) {
                long[] startTimes = new long[in.readInt()];
                in.readLongArray(startTimes);
                return new Specific(startTimes);
            }

            // We just need to copy this and change the type to match our class.
            @Override
            public Specific[] newArray(int size) {
                return new Specific[size];
            }
        };
    }
}
