package com.locationalarm.backend.intent;

import android.content.Intent;

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
