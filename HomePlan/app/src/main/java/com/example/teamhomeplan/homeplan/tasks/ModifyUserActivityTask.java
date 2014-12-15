package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.teamhomeplan.homeplan.callback.ModifiedUserActivityCallback;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.JsonException;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Constants;
import com.example.teamhomeplan.homeplan.helper.GsonFactory;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;

/**
 * Created by Niek on 12/15/2014.
 *
 * Task for adding or modifying a user activity.
 */
public class ModifyUserActivityTask extends AsyncTask<Void, Void, UserActivity> {
    private final static String requestUrl = Constants.webservicebase + "UserActivitiersService.svc/Mutate";

    private final UserActivity userActivityToUpdate;
    private final ModifiedUserActivityCallback callback;
    private ServiceException lastException;

    public ModifyUserActivityTask(UserActivity userActivityToUpdate, ModifiedUserActivityCallback callback)
    {

        this.userActivityToUpdate = userActivityToUpdate;
        this.callback = callback;
    }

    @Override
    protected UserActivity doInBackground(Void... params) {
        UserActivity resultActivity= null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            Gson gson = GsonFactory.createGson();

            JsonObject rootObject = new JsonObject();
            JsonElement userElement = gson.toJsonTree(Session.authenticatedUser);
            rootObject.add("user", userElement);
            rootObject.add("userActivity", gson.toJsonTree(userActivityToUpdate));

            HttpPost post = new HttpPost(requestUrl);
            String s = rootObject.toString();
            StringEntity se = new StringEntity(s, "UTF-8");
            se.setContentType("application/json");
            post.setEntity(se);

            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);

            String respJson = out.toString();

            if(statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
                resultActivity = gson.fromJson(respJson, UserActivity.class);
            } else {
                this.lastException = gson.fromJson(respJson, JsonException.class);
            }
        }catch(Exception ex)
        {
            this.lastException = new AsyncTaskException(ex);
        }

        return resultActivity;
    }

    @Override
    protected void onPostExecute(UserActivity userActivity) {
        super.onPostExecute(userActivity);

        if(this.lastException == null && userActivity != null)
        {
            this.callback.onAfterUserActivityModified(userActivity);
        } else if(this.lastException != null)
        {
            Log.e("TaskError", lastException.toString());
            this.callback.onAfterUserActivityModifiedException(this.lastException);
        } else {
            Log.wtf("UNEXPECTED", "No exception, no result. WTF");
        }
    }
}
