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
    void onUserActivitiesLoaded(List<UserActivity> userActivitiesLoaded);

    void onUserActivitiesLoadedException(ServiceException ex);
}
