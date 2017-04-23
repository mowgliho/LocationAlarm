package com.locationalarm.frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.locationalarm.R;
import com.locationalarm.backend.AlarmSet;
import com.locationalarm.backend.AlarmSetListener;
import com.locationalarm.backend.alarm.Alarm;

public class CurrentAlarmActivity extends AppCompatActivity implements AlarmSetListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private ArrayAdapter<Alarm> adapter;
    private AlarmSet alarmSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    //Want to add an alarm
    @Override
    public void onClick(View v) {

    }
}
