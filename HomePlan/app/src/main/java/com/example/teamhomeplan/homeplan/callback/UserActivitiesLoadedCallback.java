package com.example.teamhomeplan.homeplan.callback;

import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.ServiceException;

import java.util.List;

/**
 * Created by Niek on 12/12/14.
 *
 * Interface for providing a callback after the user activities have loaded.
 */
public interface UserActivitiesLoadedCallback {
    /**
     * Event for after user activities after loading was successful
     * @param userActivitiesLoaded The loaded user activities
     */
    void onUserActivitiesLoaded(List<UserActivity> userActivitiesLoaded);

    /**
     * Used as an event, called when a loading Exception occurred
     * @param ex the exception that occurred
     */
    void onUserActivitiesLoadedException(ServiceException ex);
}
