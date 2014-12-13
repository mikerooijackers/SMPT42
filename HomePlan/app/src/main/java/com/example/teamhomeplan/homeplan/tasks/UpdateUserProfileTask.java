package com.example.teamhomeplan.homeplan.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.teamhomeplan.homeplan.callback.RegistrationCallback;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.JsonException;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Constants;
import com.example.teamhomeplan.homeplan.helper.GsonFactory;
import com.example.teamhomeplan.homeplan.helper.Utilities;
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
 * Created by Niek on 12/13/2014.
 *
 * Task for updating the profile of a new user.
 */
public class UpdateUserProfileTask extends AsyncTask<Void, Void, User> {

    private final static String requestURL = Constants.webservicebase + "AuthenticationService.svc/EditUserProperties";
    private ServiceException taskException;
    private final RegistrationCallback callback;
    private final User userToEdit;
    private final Bitmap newImage;

    public UpdateUserProfileTask(RegistrationCallback callback, User userToEdit, Bitmap newImage)
    {

        this.callback = callback;
        this.userToEdit = userToEdit;
        this.newImage = newImage;
    }

    @Override
    protected User doInBackground(Void... params) {

        User updatedUser = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            Gson gson = GsonFactory.createGson();

            JsonObject rootObject = new JsonObject();
            JsonElement userElement = gson.toJsonTree(this.userToEdit);
            rootObject.add("user", userElement);
            if(this.newImage != null)
            {
                String encodedImage = Utilities.encodeTobase64(this.newImage);
                rootObject.add("newProfileImage", gson.toJsonTree(encodedImage));
            }

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

            if(statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
                updatedUser = gson.fromJson(respJson, User.class);
            } else {
                this.taskException = gson.fromJson(respJson, JsonException.class);
            }
        }catch(Exception ex)
        {
            this.taskException = new AsyncTaskException(ex);
        }

        return updatedUser;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);

        if(this.taskException == null)
        {
            this.callback.afterRegistrationSuccessful(user);
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, taskException.getExceptionStackTrace());
            this.callback.afterRegistrationFailed(taskException);
        }
    }
}
