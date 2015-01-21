package com.example.teamhomeplan.homeplan;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.teamhomeplan.homeplan.adapter.ActivityListAdapter;
import com.example.teamhomeplan.homeplan.callback.PlanGenerationCallback;
import com.example.teamhomeplan.homeplan.domain.Plan;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Constants;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.example.teamhomeplan.homeplan.helper.Utilities;
import com.example.teamhomeplan.homeplan.tasks.GeneratePlanTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niek on 18-1-2015
 * <p/>
 * Activity for the planning generator.
 */
public class PlanningGeneratorActivity extends ActionBarActivity implements PlanGenerationCallback {

    private ActivityListAdapter activityListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_generator);

        Button manageActivitiesButton = (Button) findViewById(R.id.planning_generator_manage_activities);
        manageActivitiesButton.setOnClickListener(onSelectUserActivitesClicked);

        Button generatePlanButton = (Button) findViewById(R.id.planning_generator_generate);
        generatePlanButton.setOnClickListener(onGeneratePlanClicked);


        TimePicker startTimePicker = (TimePicker) findViewById(R.id.planning_generator_starttime);
        TimePicker endTimePicker = (TimePicker) findViewById(R.id.planning_generator_endtime);

        startTimePicker.setIs24HourView(true);
        startTimePicker.setCurrentHour(8);
        startTimePicker.setCurrentMinute(0);

        endTimePicker.setIs24HourView(true);
        endTimePicker.setCurrentHour(17);
        endTimePicker.setCurrentMinute(0);
    }


    /**
     * Called when the activity for selecting user activities is finished.
     *
     * @param requestCode the code we requested a result for
     * @param resultCode  the received resultcode
     * @param data        the resulting intent containing the data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == resultCode) {
            switch (requestCode) {
                case 1:
                    ListView selectedActivitiesListView = (ListView) findViewById(R.id.planning_generator_selected_activities);

                    ArrayList<UserActivity> selectedActivities = data.getParcelableArrayListExtra(Constants.SELECTED_USERACTIVITIES);
                    ActivityListAdapter listAdapter = new ActivityListAdapter(this, selectedActivities);

                    selectedActivitiesListView.setAdapter(listAdapter);

                    activityListAdapter = listAdapter;
                    break;
            }
        }
    }

    /**
     * Show the activity for selecting user activities.
     */
    private View.OnClickListener onSelectUserActivitesClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PlanningGeneratorActivity.this, SelectUserActivitiesActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener onGeneratePlanClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO implement;

            TimePicker startTimePicker = (TimePicker) findViewById(R.id.planning_generator_starttime);
            TimePicker endTimePicker = (TimePicker) findViewById(R.id.planning_generator_endtime);

            long startMillis = Utilities.getTimePickerInMilliseconds(startTimePicker);
            long endMillis = Utilities.getTimePickerInMilliseconds(endTimePicker);

            List<UserActivity> userActivities = new ArrayList<>();
            if (activityListAdapter != null)
            {
                userActivities = activityListAdapter.getUserActivities();
            }


            GeneratePlanTask generatePlanTask = new GeneratePlanTask(PlanningGeneratorActivity.this, startMillis, endMillis, userActivities);
            generatePlanTask.execute();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planning_generator, menu);
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

    @Override
    public void afterSuccessfulGeneration(Plan plan) {
        Session.currentLoadedPlan = plan;

        Intent intent = new Intent(PlanningGeneratorActivity.this, DayOverviewActivity.class);
        startActivity(intent);
    }

    @Override
    public void afterGenerationFailed(ServiceException se) {
        Toast.makeText(this, se.toString(), Toast.LENGTH_LONG).show();
    }
}
