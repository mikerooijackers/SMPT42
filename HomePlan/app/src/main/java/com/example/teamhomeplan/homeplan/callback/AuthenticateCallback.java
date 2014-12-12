package com.example.teamhomeplan.homeplan.callback;

import com.example.teamhomeplan.homeplan.domain.User;

/**
 * Created by Niek on 12/12/14.
 *
 * Callback for when the user is authenticated
 */
public interface AuthenticateCallback {

    void afterSuccesfullyAuthenticated(User user);

    void afterAuthenticationFailed(Exception error);

}
