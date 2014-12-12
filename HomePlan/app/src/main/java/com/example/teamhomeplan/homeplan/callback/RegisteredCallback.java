package com.example.teamhomeplan.homeplan.callback;

import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.exception.ServiceException;

/**
 * Created by Niek on 12/12/14.
 *
 * Callback used for when registering the user
 */
public interface RegisteredCallback {

    void afterUserRegistered(User user);

    void afterRegistrationFailed(ServiceException ex);

}
