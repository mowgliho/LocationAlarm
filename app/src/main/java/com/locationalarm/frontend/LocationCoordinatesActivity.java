package com.locationalarm.frontend;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.locationalarm.R;
import com.locationalarm.backend.alarm.builder.AlarmBuilder;
import com.locationalarm.util.Constants;

public class LocationCoordinatesActivity extends AppCompatActivity {
    private AlarmBuilder alarmBuilder;
    private EditText latitude;
    private EditText longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_coordinates);
        alarmBuilder = this.getIntent().getExtras().getParcelable(Constants.ALARMBUILDER);
        this.latitude = (EditText) findViewById(R.id.latitudeEditText);
        this.longitude = (EditText) findViewById(R.id.longitudeEditText);
        setText();
    }

    private void setText(){
        latitude.setText(alarmBuilder.getLatitude() + "");
        longitude.setText(alarmBuilder.getLongitude() + "");
    }

    public void confirm(View view) {
        Intent returnIntent = new Intent();
        alarmBuilder.setLatitude(Double.parseDouble(latitude.getText().toString()));
        alarmBuilder.setLongitude(Double.parseDouble(longitude.getText().toString()));
        returnIntent.putExtra(Constants.LOCATION_ALARM_SELECTION, alarmBuilder);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
