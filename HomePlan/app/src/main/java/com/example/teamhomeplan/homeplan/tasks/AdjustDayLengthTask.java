package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;
import com.example.teamhomeplan.homeplan.callback.AdjustDayLengthCallback;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.JsonException;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Constants;
import com.example.teamhomeplan.homeplan.helper.GsonFactory;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.google.gson.Gson;
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
 * Created by Edwin on 19-Jan-15.
 */
public class AdjustDayLengthTask extends AsyncTask<Void, Void, Long> {

    private ServiceException serviceException;
    private AdjustDayLengthCallback adjustDayLengthCallback;
    private Long length;

    public AdjustDayLengthTask(AdjustDayLengthCallback adjustDayLengthCallback, Long length) {
        if (this.adjustDayLengthCallback == null) {
            throw new IllegalArgumentException("callback");
        }
        this.serviceException = null;
        this.adjustDayLengthCallback = adjustDayLengthCallback;
        this.length = length;
    }

    @Override
    protected Long doInBackground(Void... params) {
        String requestURL = Constants.webservicebase + "AuthenticationService.svc/AdjustDayLength";

        Long lengthh = 0L;

        try {
            HttpClient client = new DefaultHttpClient();
            Gson gson = GsonFactory.createGson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("userId", gson.toJsonTree(Session.authenticatedUser.getUserId()));
            jsonObject.add("length", gson.toJsonTree(this.length));

            HttpPost post = new HttpPost(requestURL);
            String s = jsonObject.toString();
            StringEntity se = new StringEntity(s, "UTF-8");
            se.setContentType("application/json");
            post.setEntity(se);

            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);

            String respj = out.toString();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                lengthh = gson.fromJson(respj, Long.class);
            } else {
                this.serviceException = gson.fromJson(respj, JsonException.class);
            }
        } catch (Exception ex) {
            this.serviceException = new AsyncTaskException(ex);
        }

        return lengthh;
    }
}
