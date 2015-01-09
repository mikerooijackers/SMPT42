package com.example.teamhomeplan.homeplan.callback;

import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.ServiceException;

/**
 * Created by Edwin on 07-Jan-15.
 */
public interface CreateTaskCallback {
    void afterCreateTaskSuccessful(UserActivity activity);
    void afterCreateTaskFailed(ServiceException exception);
}
