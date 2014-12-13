package com.example.teamhomeplan.homeplan.helper;

import android.app.Activity;
import android.content.Intent;

import com.example.teamhomeplan.homeplan.LoginActivity;
import com.example.teamhomeplan.homeplan.MainActivity;
import com.example.teamhomeplan.homeplan.domain.User;

/**
 * Created by Niek on 12/12/14.
 * <p/>
 * Class for storing the current user session (probably shouldn't be one in static variables but nobody cares.)
 */
public class Session {

    //TODO: Make this nicer.
    public static User authenticatedUser;

    public static boolean isAuthenticated() {
        return authenticatedUser != null;
    }

    public static void signOut(Activity context) {
        authenticatedUser = null;
        Intent newActivity = new Intent(context, LoginActivity.class);
        newActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(newActivity);
        context.finish();
    }
}
