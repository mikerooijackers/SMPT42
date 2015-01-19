package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.UUID;

/**
 * Created by Edwin on 17-1-2015.
 */
public class AdjustDayLengthActivity extends Activity {

    private UUID userId;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_day_length);

        TimePicker timePicker = (TimePicker) findViewById(R.id.tpAdjustDayLength);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(8);
        timePicker.setCurrentMinute(0);
    }
}
