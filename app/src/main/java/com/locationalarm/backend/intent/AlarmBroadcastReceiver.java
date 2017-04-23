package com.locationalarm.backend.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.locationalarm.backend.alarm.Alarm;

/**
 * Created by Brent on 4/23/2017.
 */

@SuppressWarnings("MissingPermission")//TODO check permissions
public class AlarmBroadcastReceiver extends BroadcastReceiver implements LocationListener {
    private String[] providers;
    private Location target;
    private double triggerDistance;
    private String displayName;

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Alarm alarm = ((AlarmIntent) intent).getAlarm();
        this.providers = alarm.getProviders();
        this.target = new Location("");
        target.setLongitude(alarm.getLongitude());
        target.setLatitude(alarm.getLatitude());
        this.triggerDistance = alarm.getTriggerDistance();
        this.displayName = alarm.getDisplayName();

        this.context = context;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        for (String provider : providers) {
            locationManager.requestSingleUpdate(provider, this, null);
        }
    }

    //TODO use speed, accuracy to provide time estimate?
    @Override
    public void onLocationChanged(Location location) {
        if(location.distanceTo(target) < triggerDistance) {
            MediaPlayer.create(context,Settings.System.DEFAULT_RINGTONE_URI).start();
            Toast.makeText(context,displayName,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
