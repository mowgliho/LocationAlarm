package com.locationalarm.frontend;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.locationalarm.R;
import com.locationalarm.backend.AlarmSet;
import com.locationalarm.backend.AlarmSetListener;
import com.locationalarm.backend.alarm.Alarm;
import com.locationalarm.util.Constants;

public class CurrentAlarmActivity extends AppCompatActivity implements AlarmSetListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private final static int
        NEWALARMREQUESTCODE = 0,
        EDITALARMREQUESTCODE=1;
    private ArrayAdapter<Alarm> adapter;
    private AlarmSet alarmSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Constants.LOCATION_ALARM,"Starting CurrentAlarmActivity");
        setContentView(R.layout.activity_current_alarm);
        ListView listView = (ListView) findViewById(R.id.CurrentAlarmList);
        alarmSet = new AlarmSet(this);
        alarmSet.addListener(this);
        adapter = new ArrayAdapter<Alarm>(
                this,android.R.layout.simple_list_item_1);//TODO make a prettier display
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        loadList();
        Button addButton = (Button) findViewById(R.id.AddAlarmButton);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onChange() {
        loadList();
    }

    private void loadList() {
        adapter.clear();
        adapter.addAll(alarmSet.getAlarms());
    }

    //Want to edit an alarm
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Alarm alarm = alarmSet.getAlarm(position);
        newEditAlarm(alarm, EDITALARMREQUESTCODE);
    }

    //Want to add an alarm
    @Override
    public void onClick(View v) {
        newEditAlarm(null, NEWALARMREQUESTCODE);
    }

    private void newEditAlarm(Alarm alarm, int requestCode) {
        Intent intent = new Intent(this, NewEditAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.NEWEDITALARM, alarm);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Alarm resultAlarm = data.getExtras().getParcelable(Constants.NEWEDITALARMRESULT);
            if(requestCode == NEWALARMREQUESTCODE) {
                Log.i(Constants.LOCATION_ALARM,"New Alarm Intent returned with reqCode " + requestCode + " resCode " + resultCode + " alarm: " + resultAlarm);
                if(resultAlarm != null) {
                    alarmSet.addAlarm(resultAlarm,this);
                }
            } else if(requestCode == EDITALARMREQUESTCODE) {
                Alarm sentAlarm = data.getExtras().getParcelable(Constants.NEWEDITALARMSENT);
                Log.i(Constants.LOCATION_ALARM,"Edit Alarm Intent returned with reqCode " + requestCode + " resCode " + resultCode + " alarm: " + resultAlarm);
                if(resultAlarm == null) {//TODO is this triggered if just go back?
                   alarmSet.removeAlarm(sentAlarm,this);
                } else {
                    alarmSet.removeAlarm(sentAlarm,this);
                    alarmSet.addAlarm(resultAlarm,this);
                }
            }
        }

    }
}
