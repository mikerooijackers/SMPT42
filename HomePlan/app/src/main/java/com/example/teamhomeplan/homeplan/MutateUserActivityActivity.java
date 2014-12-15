package com.example.teamhomeplan.homeplan;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.teamhomeplan.homeplan.callback.ModifiedUserActivityCallback;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.enumerations.UserActivityIconType;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Utilities;
import com.example.teamhomeplan.homeplan.tasks.ModifyUserActivityTask;

import java.util.UUID;


public class MutateUserActivityActivity extends ActionBarActivity implements ModifiedUserActivityCallback {

    private View busyLoader=  null;

    private UUID editingUserActivityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutate_user_activity);
        setTitle("Modify User Activity");

        //Set time picker to 24 hour
        TimePicker userActivityTimePicker = (TimePicker) findViewById(R.id.useractivitymutate_timepicker);
        userActivityTimePicker.setIs24HourView(true);

        busyLoader = findViewById(R.id.mutateua_loader);

        Button saveChangesButton = (Button) findViewById(R.id.useractivitymutate_btnSave);
        saveChangesButton.setOnClickListener(onSaveChangesClicked);

        Intent intent = getIntent();
        String editingUserActivityIDString= intent.getStringExtra("editid");

        if(editingUserActivityIDString != null && !editingUserActivityIDString.equals("")){
            editingUserActivityID = UUID.fromString(editingUserActivityIDString);
        }
    }



    private View.OnClickListener onSaveChangesClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Show the loader
            busyLoader.setVisibility(View.VISIBLE);
            TextView errorLabel = (TextView) findViewById(R.id.useractivitymutate_lblError);

            String activityName = ((EditText) findViewById(R.id.useractivitymutate_Name)).getText().toString();

            int iconTypeInteger;
            String iconTypeInput = ((EditText) findViewById(R.id.useractivitymutate_Icon)).getText().toString();
            if(!Utilities.tryParseInt(iconTypeInput))
            {
                errorLabel.setText("Icon was not a valid number");
                return;
            } else {
                iconTypeInteger = Integer.parseInt(iconTypeInput);
            }
            UserActivityIconType iconType = UserActivityIconType.fromKey(iconTypeInteger);

            if(iconType == null)
            {
                errorLabel.setText("Icon type was not part of the enumeration.");
                return;
            }

            TimePicker timePicker = (TimePicker) findViewById(R.id.useractivitymutate_timepicker);
            long timeMilliseconds = (timePicker.getCurrentHour() * 3600 + timePicker.getCurrentMinute() * 60) * 1000;

            UserActivity userActivityToMutate = new UserActivity();
            userActivityToMutate.setIconType(iconType);
            userActivityToMutate.setUserId(editingUserActivityID);
            userActivityToMutate.setName(activityName);
            userActivityToMutate.setPlannedDuration(timeMilliseconds);

            ModifyUserActivityTask modifyUserActivityTask = new ModifyUserActivityTask(userActivityToMutate, MutateUserActivityActivity.this);
            modifyUserActivityTask.execute();
        }
    };


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mutate_user_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Callbacks after saving.
    @Override
    public void onAfterUserActivityModified(UserActivity userActivity) {
        busyLoader.setVisibility(View.GONE);

        //TODO: Finish the current activity, maybe set a result
        finish();
    }

    @Override
    public void onAfterUserActivityModifiedException(ServiceException ex) {
        busyLoader.setVisibility(View.GONE);
        TextView errorLabel = (TextView) findViewById(R.id.useractivitymutate_lblError);
        errorLabel.setText(ex.getExceptionMessage());
    }
}
