package com.example.teamhomeplan.homeplan;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.teamhomeplan.homeplan.adapter.ActivityListAdapter;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.helper.Constants;

import java.util.ArrayList;

/**
 * Created by Niek on 18-1-2015
 *
 * Activity for the planning generator.
 */
public class PlanningGeneratorActivity extends ActionBarActivity {

    private ActivityListAdapter activityListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_generator);

        Button manageActivitiesButton = (Button) findViewById(R.id.planning_generator_manage_activities);
        manageActivitiesButton.setOnClickListener(onSelectUserActivitesClicked);

        Button generatePlanButton = (Button) findViewById(R.id.planning_generator_generate);
        generatePlanButton.setOnClickListener(onGeneratePlanClicked);
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
}
