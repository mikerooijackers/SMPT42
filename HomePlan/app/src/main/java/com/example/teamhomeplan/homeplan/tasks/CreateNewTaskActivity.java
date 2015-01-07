package com.example.teamhomeplan.homeplan.tasks;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import com.example.teamhomeplan.homeplan.R;
import com.example.teamhomeplan.homeplan.domain.UserActivity;

/**
 * Created by Edwin on 07-Jan-15.
 */
public class CreateNewTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        setTitle("Create new task");

        TimePicker timePicker = (TimePicker) findViewById(R.id.tp_activityDuration);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);

        Button button = (Button) findViewById(R.id.button_saveActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activityName = ((EditText) findViewById(R.id.et_activityName)).getText().toString();
                int hour = ((TimePicker) findViewById(R.id.tp_activityDuration)).getCurrentHour();
                int minute = ((TimePicker) findViewById(R.id.tp_activityDuration)).getCurrentMinute();

                UserActivity newUserActivity = new UserActivity();
                newUserActivity.setName(activityName);
            }
        });

    }
}
