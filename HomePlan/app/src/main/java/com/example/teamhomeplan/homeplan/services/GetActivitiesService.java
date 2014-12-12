package com.example.teamhomeplan.homeplan.services;

import android.os.AsyncTask;

import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.callback.UserActivitiesLoadedCallback;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.JsonException;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.helper.Constants;
import com.example.teamhomeplan.homeplan.helper.GsonFactory;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niek on 12/12/14.
 * <p/>
 * Get all the UserActivities of the current user.
 */
public class GetActivitiesService extends AsyncTask<Void, Void, List<UserActivity>> {

    private UserActivitiesLoadedCallback callback;
    private ServiceException lastException = null;

    public GetActivitiesService(UserActivitiesLoadedCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<UserActivity> doInBackground(Void... params) {
        String requestUrl = Constants.webservicebase + "UserActivities.svc/GetUserActivities";

        HttpClient httpClient = new DefaultHttpClient();

        List<UserActivity> userActivities;
        Type respType = new TypeToken<ArrayList<UserActivity>>() {
        }.getType();

        List<UserActivity> returnValue = null;
        try {
            Gson gson = GsonFactory.createGson();

            String json = gson.toJson(Session.authenticatedUser, User.class);
            HttpPost post = new HttpPost(requestUrl);
            StringEntity se = new StringEntity(json, "HTTP.UTF-8");
            se.setContentType("application/json");

            post.setEntity(se);


            //Get the http response
            HttpResponse response = httpClient.execute(post);
            StatusLine statusLine = response.getStatusLine();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);

            String respJson = out.toString();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                userActivities = gson.fromJson(respJson, respType);

                returnValue = userActivities;

            } else {
                this.lastException = gson.fromJson(respJson, JsonException.class);
            }
        } catch (Exception ex) {
            this.lastException = new AsyncTaskException(ex);
        }

        return returnValue;
    }

    @Override
    protected void onPostExecute(List<UserActivity> userActivities) {
        super.onPostExecute(userActivities);

        if (this.lastException != null) {
            this.callback.onUserActivitiesLoadedException(this.lastException);
            //TODO: Handle the exception
            return;
        }


        this.callback.onUserActivitiesLoaded(userActivities);

        super.onPostExecute(userActivities);
    }
}
