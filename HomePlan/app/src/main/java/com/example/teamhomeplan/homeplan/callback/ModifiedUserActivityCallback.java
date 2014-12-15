package com.example.teamhomeplan.homeplan.callback;

import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.ServiceException;

/**
 * Created by Niek on 12/15/2014.
 *
 * Callback for when a User acitvity is modified or added.
 */
public interface ModifiedUserActivityCallback {
    void onAfterUserActivityModified(UserActivity userActivity);
    void onAfterUserActivityModifiedException(ServiceException ex);
}
