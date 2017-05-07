package com.locationalarm.frontend;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.locationalarm.R;
import com.locationalarm.backend.alarm.builder.AlarmBuilder;
import com.locationalarm.util.Constants;

import org.w3c.dom.Text;

public class LocationSelectionActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 0;

    private AlarmBuilder alarmBuilder;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        this.alarmBuilder = this.getIntent().getExtras().getParcelable(Constants.ALARMBUILDER);
        textView = (TextView) findViewById(R.id.locationSelectionText);
        setText();
    }

    private void setText() {
        textView.setText("Lat: " + alarmBuilder.getLatitude() + " Long: " + alarmBuilder.getLongitude());
    }

    public void setUsingCoordinates(View v) {
        Intent intent = new Intent(this, LocationCoordinatesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ALARMBUILDER, alarmBuilder);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCODE) {
            if(resultCode == Activity.RESULT_OK){
                alarmBuilder = data.getParcelableExtra(Constants.LOCATION_ALARM_SELECTION);
                setText();
            }
        }
    }
}
