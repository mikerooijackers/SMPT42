package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.teamhomeplan.homeplan.callback.PlanGenerationCallback;
import com.example.teamhomeplan.homeplan.domain.Plan;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.*;
import com.example.teamhomeplan.homeplan.helper.*;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niek on 19/01/15.
 * <p/>
 * Task for generating a plan from the backend.
 */
public class GeneratePlanTask extends AsyncTask<Void, Void, Plan> {

    private PlanGenerationCallback callback;
    private ServiceException serviceException;
    private double startAtMillis;
    private double endAtMillis;
    private List<String> userActivityIds; //JSON in backend will serialize them back to GUID


    public GeneratePlanTask(PlanGenerationCallback callback,
                            double startAtMillis,
                            double endAtMillis,
                            List<UserActivity> userActivities) {
        if (callback == null) {
            throw new IllegalArgumentException("callback was null");
        }

        this.callback = callback;
        this.startAtMillis = startAtMillis;
        this.endAtMillis = endAtMillis;

        userActivityIds = new ArrayList<>();
        for (UserActivity userActivity : userActivities) {
            userActivityIds.add(userActivity.getUserActivityId());
        }
    }

    @Override
    protected Plan doInBackground(Void... params) {
        String requestUrl = Constants.webservicebase + "PlanService.svc/GeneratePlan";

        Plan generatedPlan = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            Gson gson = GsonFactory.createGson();

            JsonObject rootObject = new JsonObject();
            JsonElement userElement = gson.toJsonTree(Session.authenticatedUser);
            rootObject.add("loggedInUser", userElement);
            rootObject.add("startAtMillis", gson.toJsonTree(this.startAtMillis));
            rootObject.add("endAtMillis", gson.toJsonTree(this.endAtMillis));
            rootObject.add("activities", gson.toJsonTree(this.userActivityIds));

            HttpPost post = new HttpPost(requestUrl);
            String json = rootObject.toString();
            StringEntity se = new StringEntity(json, "UTF-8");
            se.setContentType("application/json");
            post.setEntity(se);

            HttpResponse response = httpClient.execute(post);
            StatusLine statusLine = response.getStatusLine();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);

            String respJson = out.toString();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                generatedPlan = gson.fromJson(respJson, Plan.class);
            } else {
                if (respJson.equals("")) {
                    throw new Exception("An unexpected error occurred");
                } else {
                    this.serviceException = gson.fromJson(respJson, JsonException.class);
                }
            }
        } catch (Exception ex) {
            this.serviceException = new AsyncTaskException(ex);
        }

        return generatedPlan;
    }

    @Override
    protected void onPostExecute(Plan plan) {
        super.onPostExecute(plan);

        if (this.serviceException == null) {
            //Execute the successful callback
            this.callback.afterSuccessfulGeneration(plan);
        } else {
            //Log the error
            Log.e("TaskException", this.serviceException.toString());
            this.callback.afterGenerationFailed(this.serviceException);
        }
    }
}
