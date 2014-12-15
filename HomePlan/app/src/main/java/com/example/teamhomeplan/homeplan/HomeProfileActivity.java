package com.example.teamhomeplan.homeplan;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.teamhomeplan.homeplan.helper.Session;


public class HomeProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sign out if not authenticated.
        if (!Session.isAuthenticated()) {
            Session.signOut(this);
        }

        setContentView(R.layout.activity_home_profile);

        setTitle("Home plan");

        Button onAddUserActivity = (Button) findViewById(R.id.homeprofile_btnAddActivity);
        onAddUserActivity.setOnClickListener(onAddUserActivityClick);
    }


    private View.OnClickListener onAddUserActivityClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeProfileActivity.this, MutateUserActivityActivity.class);
            startActivity(intent);
        }
    };
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.profile_signout): {
                Session.signOut(this);
                break;
            }
            case (R.id.profile_toProfileActivity): {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
