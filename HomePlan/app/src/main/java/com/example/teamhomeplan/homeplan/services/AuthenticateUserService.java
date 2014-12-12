package com.example.teamhomeplan.homeplan.services;

import android.os.AsyncTask;

import com.example.teamhomeplan.homeplan.callback.AuthenticateCallback;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.helper.Constants;

/**
 * Created by Niek on 12/12/14.
 *
 * Service for authenticating the use
 */
public class AuthenticateUserService extends AsyncTask<Void, Void, User> {

    private AuthenticateCallback callback;
    private User userToRegister;

    public AuthenticateUserService(AuthenticateCallback callback, User userToRegister)
    {
        this.callback = callback;
        this.userToRegister= userToRegister;
    }

    @Override
    protected User doInBackground(Void... params) {
        String requestUrl = Constants.webservicebase; //TODO:!!!

        return null;
        //TODO: implement.
    }


    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);

        //TODO implement.
    }
}
