package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.teamhomeplan.homeplan.helper.Session;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Class<? extends Activity> activityClass = null;
        if(Session.isAuthenticated())
        {
            //Show the main page;
        } else {
            //Show the Login activity.
            activityClass = LoginActivity.class;
        }

        Intent newActivity = new Intent(this, activityClass);
        this.startActivity(newActivity);
    }

}
