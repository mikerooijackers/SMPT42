package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.teamhomeplan.homeplan.callback.AuthenticateCallback;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.helper.Session;


public class LoginActivity extends ActionBarActivity implements AuthenticateCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    @Override
    public void afterSuccesfullyAuthenticated(User user) {
        //TODO: Show the main screen.
    }

    @Override
    public void afterAuthenticationFailed(Exception error) {
        //TODO: show authentication error.
    }
}
