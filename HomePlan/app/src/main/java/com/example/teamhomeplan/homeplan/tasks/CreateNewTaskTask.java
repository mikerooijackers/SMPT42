package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;
import android.util.Log;
import com.example.teamhomeplan.homeplan.callback.CreateTaskCallback;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.JsonException;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Constants;
import com.example.teamhomeplan.homeplan.helper.GsonFactory;
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
 * Created by Edwin on 06-Jan-15.
 */
public class CreateNewTaskTask extends AsyncTask<Void, Void, UserActivity> {

    private ServiceException serviceException;
    private CreateTaskCallback createTaskCallback;
    private UserActivity userActivity;
    private CreateNewTaskTask context = this;

    public CreateNewTaskTask(CreateTaskCallback callback, UserActivity activity) {
        if (createTaskCallback == null)
            throw new IllegalArgumentException("callback");
        if (userActivity == null)
            throw new IllegalArgumentException("userActivity");

        this.serviceException = null;
        this.createTaskCallback = callback;
        this.userActivity = activity;
    }

    @Override
    protected UserActivity doInBackground(Void... params) {
        String requestURL = Constants.webservicebase + "AuthenticationService.svc/CreateTask";

        UserActivity activity = null;

        try {
            HttpClient client = new DefaultHttpClient();
            Gson gson = GsonFactory.createGson();
            JsonObject rootObject = new JsonObject();
            JsonElement jsonElement = gson.toJsonTree(this.userActivity);
            rootObject.add("activity", jsonElement);

            HttpPost post = new HttpPost(requestURL);
            String s = rootObject.toString();
            StringEntity se = new StringEntity(s, "UTF-8");
            se.setContentType("application/json");
            post.setEntity(se);

            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);

            String respJson = out.toString();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                activity = gson.fromJson(respJson, UserActivity.class);
            } else {
                this.serviceException = gson.fromJson(respJson, JsonException.class);
            }
        } catch (Exception ex) {
            this.serviceException = new AsyncTaskException(ex);
        }

        return activity;
    }

    @Override
    protected void onPostExecute(UserActivity activity) {
        super.onPostExecute(activity);

        if (this.serviceException != null) {
            Log.e("TaskException", this.serviceException.toString());
            this.createTaskCallback.afterCreateTaskFailed(this.serviceException);
        } else {
            this.createTaskCallback.afterCreateTaskSuccessful(activity);
        }
    }
}
