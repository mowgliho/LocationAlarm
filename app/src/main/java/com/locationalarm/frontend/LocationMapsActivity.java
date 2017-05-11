package com.locationalarm.frontend;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.locationalarm.R;
import com.locationalarm.backend.alarm.builder.AlarmBuilder;
import com.locationalarm.util.Constants;

import java.io.IOException;
import java.util.List;

public class LocationMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final static String[] PROVIDERS = new String[]{LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER};
    private GoogleMap mMap;
    private LatLng searchResult;
    private String locationText;
    private AlarmBuilder alarmBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.alarmBuilder = this.getIntent().getExtras().getParcelable(Constants.ALARMBUILDER);
        ((EditText) findViewById(R.id.mapSearch)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    onSearch();
                }
                return false;
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO:
            return;
        }
        mMap.setMyLocationEnabled(true);
        setToCurrent();
    }

    private void setToCurrent() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            Location bestLocation = null;
            for (String provider : PROVIDERS) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO:
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
                if(location != null && bestLocation != null && location.getAccuracy() < bestLocation.getAccuracy()) {
                        bestLocation = location;
                }
            }
            if(bestLocation != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(bestLocation.getLatitude(),bestLocation.getLongitude())));
            }
        }
    }

    public void onSearch(View view) {
        onSearch();
    }

    public void onSearch() {
        EditText location = (EditText) findViewById(R.id.mapSearch);
        String searchText = location.getText().toString();
        if(searchText != null && !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = null;
            try {
                addressList = geocoder.getFromLocationName(searchText,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                searchResult = new LatLng(address.getLatitude(),address.getLongitude());
                this.locationText = address.toString();
                mMap.addMarker(new MarkerOptions().position(searchResult).title(searchText));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(searchResult));
            }
        }
    }

    public void onConfirm(View view) {
        Intent returnIntent = new Intent();
        if(searchResult != null) {
            alarmBuilder.setLatitude(searchResult.latitude);
            alarmBuilder.setLongitude(searchResult.longitude);
            alarmBuilder.setLocationText(locationText);
            returnIntent.putExtra(Constants.LOCATION_ALARM_SELECTION,alarmBuilder);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
    }
}
