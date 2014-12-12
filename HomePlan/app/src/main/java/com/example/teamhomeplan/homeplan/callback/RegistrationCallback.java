package com.example.teamhomeplan.homeplan.callback;

import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.exception.ServiceException;

/**
 * Created by Niek on 12/12/2014.
 *
 * Callback for when the registration asynctask is called
 */
public interface RegistrationCallback {
    void afterRegistrationSuccessful(User registeredUser);
    void afterRegistrationFailed(ServiceException exception);
}
