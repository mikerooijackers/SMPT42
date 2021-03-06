package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.teamhomeplan.homeplan.callback.RegistrationCallback;
import com.example.teamhomeplan.homeplan.domain.User;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Niek on 12/12/2014.
 *
 * Async task for registering a user
 */
public class RegistrationTask extends AsyncTask<Void, Void, User> {
    private ServiceException serviceException;

    private RegistrationCallback callback;
    private User userToRegister;
    private String encodedImage;
    private RegistrationTask context;

    public RegistrationTask(RegistrationCallback callback, User userToRegister, String encodedImage)
    {
        if(callback == null) throw new IllegalArgumentException("callback");
        if(userToRegister == null) throw new IllegalArgumentException("userToRegister");

        this.serviceException = null;
        this.callback = callback;
        this.encodedImage = encodedImage;
        this.userToRegister = userToRegister;
        this.context = this;
    }
    @Override
    protected User doInBackground(Void... params) {
        String requestUrl = Constants.webservicebase + "AuthenticationService.svc/Register";

        User registeredUser = null;

        try {
            HttpClient client = new DefaultHttpClient();
            Gson gson = GsonFactory.createGson();

            JsonObject rootObject = new JsonObject();
            JsonElement userElement = gson.toJsonTree(this.userToRegister);
            rootObject.add("user", userElement);
            if(this.encodedImage != null)
            {
                rootObject.add("profileImage", gson.toJsonTree(encodedImage));
            }

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
                registeredUser = gson.fromJson(respJson, User.class);
            } else {
                this.serviceException = gson.fromJson(respJson, JsonException.class);
            }
        } catch(Exception ex)
        {
            this.serviceException = new AsyncTaskException(ex);
        }

        return registeredUser;
    }

    @Override
    protected void onPostExecute(User registeredUser) {
        super.onPostExecute(registeredUser);

        if(this.serviceException != null)
        {
            Log.e("TaskException", this.serviceException.toString());
            this.callback.afterRegistrationFailed(this.serviceException);
        } else {
            this.callback.afterRegistrationSuccessful(registeredUser);
        }
    }
}
