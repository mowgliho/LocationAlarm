package com.locationalarm.backend.Intent;

import android.app.PendingIntent;
import android.content.Intent;

import com.locationalarm.backend.Alarm;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Brent on 4/23/2017.
 */

public class AlarmManagerCancelAllIntent extends Intent {

    public AlarmManagerCancelAllIntent() {
    }

    @Override
    public boolean filterEquals(Intent intent) {
        if(intent instanceof AlarmManagerIntent) {
            return true;
        }
        return false;
    }
}
