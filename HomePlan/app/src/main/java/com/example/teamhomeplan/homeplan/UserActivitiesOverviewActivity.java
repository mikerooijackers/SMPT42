package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.teamhomeplan.homeplan.adapter.ActivityListAdapter;
import com.example.teamhomeplan.homeplan.callback.UserActivitiesLoadedCallback;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.example.teamhomeplan.homeplan.tasks.GetActivitiesTask;

import java.util.List;

/**
 * Created by Niek on 15/01/15.
 *
 * Overview for the useractivities added by the user.
 */
public class UserActivitiesOverviewActivity extends Activity implements UserActivitiesLoadedCallback {
    private View loaderIcon;
    private ActivityListAdapter listAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        GetActivitiesTask activitiesLoaderTask = new GetActivitiesTask(this);
        loaderIcon = findViewById(R.id.userActivitiesLoader);
        activitiesLoaderTask.execute();

        loaderIcon.setVisibility(View.VISIBLE);

        Button addActivityButton = (Button) findViewById(R.id.useractivitesoverview_useractivities_add);
        addActivityButton.setOnClickListener(onAddActivityClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useractiviesoverview);

        if(!Session.isAuthenticated())
        {
            Session.signOut(this);
        }
    }


    private View.OnClickListener onAddActivityClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UserActivitiesOverviewActivity.this, MutateUserActivityActivity.class);
            startActivity(intent);
        }
    };

    public void onUserActivitiesLoaded(List<UserActivity> userActivitiesLoaded) {
        loaderIcon.setVisibility(View.GONE);

        ListView userActivitiesListView = (ListView) findViewById(R.id.listview_useractivies);
        listAdapter = new ActivityListAdapter(this,userActivitiesLoaded);
        userActivitiesListView.setAdapter(listAdapter);
        userActivitiesListView.setOnItemClickListener(onListViewItemClicked);
    }


    private AdapterView.OnItemClickListener onListViewItemClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ActivityListAdapter adapter = (ActivityListAdapter) parent.getAdapter();

            UserActivity activity = adapter.getItem(position);

            if(activity == null)
            {
                Log.e("ActivityNull", "User activity that was clicked was null");
                return;
            }

            Intent intent = new Intent(UserActivitiesOverviewActivity.this, MutateUserActivityActivity.class);
            intent.putExtra("activityid", activity.getUserActivityId());
            startActivity(intent);
        }
    };

    @Override
    public void onUserActivitiesLoadedException(ServiceException ex) {
        //TODO: Show a loading error.
        loaderIcon.setVisibility(View.GONE);
      }

}
