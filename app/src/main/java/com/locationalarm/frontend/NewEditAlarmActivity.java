package com.locationalarm.frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.locationalarm.R;
import com.locationalarm.backend.alarm.Alarm;
import com.locationalarm.backend.alarm.builder.AlarmBuilder;
import com.locationalarm.util.Constants;

public class NewEditAlarmActivity extends AppCompatActivity {
    private AlarmBuilder alarmBuilder;
    private EditText displayNameEdit;
    private TextView locationView;
    private TextView whenView;
    private EditText triggerDistanceEdit;
    private EditText updateIntervalEdit;
    private RadioGroup searchMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_alarm);
        Alarm alarm = this.getIntent().getExtras().getParcelable(Constants.NEWEDITALARM);
        if(alarm == null) {
            this.alarmBuilder = AlarmBuilder.GETDEFAULT();
        } else {
            this.alarmBuilder = new AlarmBuilder(alarm);
        }
        this.displayNameEdit = (EditText) findViewById(R.id.displayNameText);
        this.locationView = (TextView) findViewById(R.id.locationText);
        this.whenView = (TextView) findViewById(R.id.when);
        this.triggerDistanceEdit = (EditText) findViewById(R.id.triggerDistance);
        this.updateIntervalEdit = (EditText) findViewById(R.id.updateInterval);
        this.searchMethod = (RadioGroup) findViewById(R.id.search_method_group);
        setFields();
    }

    private void setFields() {
        displayNameEdit.setText(alarmBuilder.getDisplayName());
        locationView.setText("Lat: " + alarmBuilder.getLatitude() + " Long: " + alarmBuilder.getLongitude());
        whenView.setText("TODO");//TODO
        triggerDistanceEdit.setText(alarmBuilder.getTriggerDistance() + "");
        updateIntervalEdit.setText(alarmBuilder.getUpdateInterval() + "");
    }
}
