package com.locationalarm.backend;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.locationalarm.R;
import com.locationalarm.backend.alarm.Alarm;
import com.locationalarm.backend.intent.AlarmManagerCancelAllIntent;
import com.locationalarm.util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Brent on 4/23/2017.
 */

//TODO call this on device startup https://developer.android.com/training/scheduling/alarms.html
public class AlarmSet {
    private final List<Alarm> alarms;
    private final Set<AlarmSetListener> listeners;

    /*
    functionality
     */
    public void addAlarm(Alarm alarm, Context context) {
        alarms.add(alarm);
        synchronize(context);
    }

    public void editAlarm(Alarm oldAlarm, Alarm newAlarm, Context context) {
        alarms.remove(oldAlarm);
        alarms.add(newAlarm);
        synchronize(context);
    }

    public void removeAlarm(Alarm alarm, Context context) {
        alarms.remove(alarm);
        synchronize(context);
    }

    public void synchronize(Context context) {
        Log.i(Constants.LOCATION_ALARM,"Synchronizing " + alarms.size() + " alarms");
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(
                PendingIntent.getActivity(
                        context,
                        R.integer.cancel_intent_request,
                        new AlarmManagerCancelAllIntent(),
                        PendingIntent.FLAG_ONE_SHOT
                ));
        for(Alarm alarm : alarms) {
            alarm.setAlarms(context);
        }
        changed();
    }

    /*
    LISTENER STUFF
     */
    public void addListener(AlarmSetListener listener) {
        listeners.add(listener);
    }

    private void changed() {
        for(AlarmSetListener listener: listeners) {
            listener.onChange();
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
        this.alarms = new ArrayList<Alarm>();
        for(String string : savedString) {
            //TODO parse from saved string alarms.add(new Alarm(string));
        }
        SystemBootReceiver.enable(context);
        this.listeners = new HashSet<AlarmSetListener>();
        Log.i(Constants.LOCATION_ALARM,"Initialized AlarmSet with " + alarms.size() + " alarms");
    }

    public void save(Context context) {
        Set<String> alarms = new HashSet<String>();
        for(Alarm alarm: this.alarms) {
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

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public Alarm getAlarm(int i) {
        return alarms.get(i);
    }
}
