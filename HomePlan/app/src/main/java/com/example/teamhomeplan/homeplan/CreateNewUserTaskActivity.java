package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.teamhomeplan.homeplan.callback.CreateTaskCallback;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.enumerations.UserActivityIconType;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.tasks.CreateNewTaskTask;

import java.util.UUID;

/**
 * Created by Edwin on 07-Jan-15.
 */
public class CreateNewUserTaskActivity extends Activity implements CreateTaskCallback {

    private UUID userId;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        setTitle("Create new task");

        Intent intent = getIntent();
        String sUserId = intent.getStringExtra("editid");
        if (sUserId != null && !sUserId.equals("")) {
            userId = UUID.fromString(sUserId);
        }

        TimePicker timePicker = (TimePicker) findViewById(R.id.tp_activityDuration);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);

        findViewById(R.id.button_saveActivity).setOnClickListener(onSaveButtonClick);

    }

    protected View.OnClickListener onSaveButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String activityName = ((EditText) findViewById(R.id.et_activityName)).getText().toString();
            int hour = ((TimePicker) findViewById(R.id.tp_activityDuration)).getCurrentHour();
            int minute = ((TimePicker) findViewById(R.id.tp_activityDuration)).getCurrentMinute();

            long duration = new Long(hour) + new Long(minute);

            if (activityName.equals("")) {
                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (hour == 0 && minute == 0) {
                Toast.makeText(context, "Please enter a greater duration", Toast.LENGTH_SHORT).show();
                return;
            }

            UserActivity newUserActivity = new UserActivity();
            newUserActivity.setName(activityName);
            newUserActivity.setPlannedDuration(duration);
            // TODO set proper icon type
            newUserActivity.setIconType(UserActivityIconType.DOG);
            newUserActivity.setUserId(userId);

            CreateNewTaskTask createNewTask = new CreateNewTaskTask(CreateNewUserTaskActivity.this, newUserActivity);
            createNewTask.execute();
        }
    };

    @Override
    public void afterCreateTaskSuccessful(UserActivity activity) {
        this.finish();
    }

    @Override
    public void afterCreateTaskFailed(ServiceException exception) {
        Toast.makeText(context, "Something went wrong with creating the new activity", Toast.LENGTH_LONG).show();
    }
}
