package com.example.teamhomeplan.homeplan.helper;

import android.app.Activity;
import android.content.Intent;

import com.example.teamhomeplan.homeplan.LoginActivity;
import com.example.teamhomeplan.homeplan.MainActivity;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.domain.UserActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Niek on 12/12/14.
 * <p/>
 * Class for storing the current user session (probably shouldn't be one in static variables but nobody cares.)
 */
public class Session {

    //TODO: Make this nicer.
    public static User authenticatedUser;

    //List of all the user activities that are loaded.
    public static List<UserActivity> loadedUserActivities;

    public static UserActivity getUserActivity(String userActivityID)
    {
        if(userActivityID == null)
        {
            throw new IllegalArgumentException("UserActivityID");
        }

        UserActivity foundUserActivity = null;

        if(loadedUserActivities !=  null) {
            for (UserActivity ua : loadedUserActivities) {
                if(ua.getUserActivityId().equals(userActivityID))
                {
                    foundUserActivity = ua;
                    break;
                }
            }
        }

        return foundUserActivity;
    }

    /**
     * Check if the user of the application is authenticated
     * @return True when authenticated, false when not
     */
    public static boolean isAuthenticated() {
        return authenticatedUser != null;
    }

    /**
     * Sign the user out of the applicatior
     * @param context the current activity as context
     */
    public static void signOut(Activity context) {
        authenticatedUser = null;
        Intent newActivity = new Intent(context, LoginActivity.class);
        newActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(newActivity);
        context.finish();
    }
}
