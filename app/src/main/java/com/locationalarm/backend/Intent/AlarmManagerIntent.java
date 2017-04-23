package com.locationalarm.backend.Intent;

import android.content.Intent;

import com.locationalarm.backend.Alarm;

/**
 * Created by Brent on 4/23/2017.
 */

public class AlarmManagerIntent extends Intent {
    private final String hashString;

    public AlarmManagerIntent(Alarm alarm) {
        this.hashString = alarm.getHashString();
    }

    public String getHashString() {
        return hashString;
    }

    @Override
    public boolean filterEquals(Intent intent) {
        if(intent instanceof AlarmManagerIntent) {
            return hashString.equals(((AlarmManagerIntent) intent).getHashString());
        } else if(intent instanceof AlarmManagerCancelAllIntent) {
            return ((AlarmManagerCancelAllIntent) intent).filterEquals(this);
        }
        return false;
    }
}
