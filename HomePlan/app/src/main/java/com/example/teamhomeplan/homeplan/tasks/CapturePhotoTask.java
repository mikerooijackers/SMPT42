package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;
import com.example.teamhomeplan.homeplan.callback.CapturePhotoCallback;
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
public class CapturePhotoTask extends AsyncTask<Void, Void, String> {

    private CapturePhotoCallback callback;
    private String encodedImage;
    private ServiceException serviceException;

    public CapturePhotoTask(CapturePhotoCallback capturePhotoCallback, String encodedImage) {
        this.callback = capturePhotoCallback;
        this.encodedImage = encodedImage;
    }

    @Override
    protected String doInBackground(Void... params) {
        String url = Constants.webservicebase + "AuthenticationService.svc/SavePhoto";

        String image = "";

        try {
            HttpClient client = new DefaultHttpClient();
            Gson gson = GsonFactory.createGson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("userId", gson.toJsonTree(Session.authenticatedUser.getUserId()));
            jsonObject.add("photo", gson.toJsonTree(this.encodedImage));

            HttpPost post = new HttpPost(url);
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
                image = gson.fromJson(respj, String.class);
            } else {
                this.serviceException = gson.fromJson(respj, JsonException.class);
            }
        } catch (Exception ex) {
            this.serviceException = new AsyncTaskException(ex);
        }

        return image;
    }
}
