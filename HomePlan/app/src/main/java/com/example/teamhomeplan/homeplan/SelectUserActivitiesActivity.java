package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.teamhomeplan.homeplan.adapter.ActivityListAdapter;
import com.example.teamhomeplan.homeplan.callback.UserActivitiesLoadedCallback;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Constants;
import com.example.teamhomeplan.homeplan.tasks.GetActivitiesTask;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Niek on 18-1-2014
 *
 * Activity for selecting user activities from a list.
 */
public class SelectUserActivitiesActivity extends Activity implements UserActivitiesLoadedCallback {

    private ArrayList<UserActivity> selectedUserActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_activities);

        selectedUserActivities = new ArrayList<>();

        GetActivitiesTask getActivitiesTask = new GetActivitiesTask(this);
        getActivitiesTask.execute();

        Button saveButton = (Button) findViewById(R.id.select_useractivities_save);
        saveButton.setOnClickListener(onSaveClicked);
    }

    @Override
    public void onUserActivitiesLoaded(List<UserActivity> userActivitiesLoaded) {
        ListView userActivitiesListView = (ListView) findViewById(R.id.selectuseractivities_listview);
        ActivityListAdapter listAdapter = new ActivityListAdapter(this, userActivitiesLoaded);
        userActivitiesListView.setAdapter(listAdapter);

        userActivitiesListView.setOnItemClickListener(onListItemClicked);
    }

    /**
     * When the save button is clicked, put the selected user activities in a new intent.
     */
    private View.OnClickListener onSaveClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra(Constants.SELECTED_USERACTIVITIES, selectedUserActivities);
            setResult(1, resultIntent);

            finish();
        }
    };

    /**
     * When an item is clicked in the list, check whether or not to check or uncheck it, and keep track of the item.
     */
    private AdapterView.OnItemClickListener onListItemClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ActivityListAdapter activityListAdapter = (ActivityListAdapter) parent.getAdapter();


            UserActivity userActivityClicked = activityListAdapter.getItem(position);
            if(selectedUserActivities.contains(userActivityClicked))
            {
                view.setSelected(false);
                selectedUserActivities.remove(userActivityClicked);
            } else {
                view.setSelected(true);
                selectedUserActivities.add(userActivityClicked);
            }
        }
    };

    @Override
    public void onUserActivitiesLoadedException(ServiceException ex) {
        //TODO: show a nice error.
    }
}
