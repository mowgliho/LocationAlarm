package com.locationalarm.backend;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by Brent on 4/23/2017.
 */

public class SystemBootReceiver extends BroadcastReceiver {
    public static void enable(Context context) {
        ComponentName receiver = new ComponentName(context, SystemBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            AlarmSet alarmSet = new AlarmSet(context);
            alarmSet.synchronize(context);
        }
    }
}
