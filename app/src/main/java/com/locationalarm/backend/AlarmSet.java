package com.locationalarm.backend;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.locationalarm.R;
import com.locationalarm.backend.Intent.AlarmManagerCancelAllIntent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Brent on 4/23/2017.
 */

//TODO call this on device startup https://developer.android.com/training/scheduling/alarms.html
public class AlarmSet {
    private final Set<Alarm> alarmSet;

    /*
    functionality
     */
    public void addAlarm(Alarm alarm, Context context) {
        alarmSet.add(alarm);
        synchronize(context);
    }

    public void removeAlarm(Alarm alarm, Context context) {
        alarmSet.remove(alarm);
        synchronize(context);
    }

    public void synchronize(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(
                PendingIntent.getActivity(
                        context,
                        R.integer.cancel_intent_request,
                        new AlarmManagerCancelAllIntent(),
                        PendingIntent.FLAG_ONE_SHOT
                ));
        for(Alarm alarm : alarmSet) {
            //TODO manager.set();
        }
    }

    /*
    SETUP, SAVING
     */
    public AlarmSet(Context context) {
        Resources resources = getResources(context);
        SharedPreferences sharedPreferences = getSharedPreferences(context);

        Set<String> savedString = sharedPreferences.getStringSet(
                resources.getString(R.string.alarm_storage_alarms),
                new HashSet<String>()
        );
        this.alarmSet = new HashSet<Alarm>();
        for(String string : savedString) {
            alarmSet.add(new Alarm(string));
        }
    }

    public void save(Context context) {
        Set<String> alarms = new HashSet<String>();
        for(Alarm alarm: alarmSet) {
            alarms.add(alarm.getHashString());
        }
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putStringSet(
                context.getResources().getString(R.string.alarm_storage_alarms),
                alarms
        );
    }

    private Resources getResources(Context context) {
        return context.getResources();
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getResources().getString(R.string.alarm_storage),
                Context.MODE_PRIVATE
        );
    }
}
