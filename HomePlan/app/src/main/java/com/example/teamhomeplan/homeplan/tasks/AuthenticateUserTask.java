package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;

import com.example.teamhomeplan.homeplan.callback.AuthenticateCallback;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.JsonException;
import com.example.teamhomeplan.homeplan.domain.User;
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
 * Created by Niek on 12/12/14.
 * <p/>
 * Service for authenticating the user
 */
public class AuthenticateUserTask extends AsyncTask<Void, Void, User> {

    private AuthenticateCallback callback;
    private ServiceException lastException;
    private User usertoAuthenticate;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public AuthenticateUserTask(AuthenticateCallback callback, User userToAuthenticate) {
        if(callback == null)
        {
            throw new IllegalArgumentException("no callback was specified");
        }

        if(userToAuthenticate == null)
        {
            throw new IllegalArgumentException("No user to authenticate was specified");
        }

        this.callback = callback;
        this.usertoAuthenticate = userToAuthenticate;
    }

    @Override
    protected User doInBackground(Void... params) {
        HttpClient client = new DefaultHttpClient();

        User authenticatedUser = null;

        try {
            Gson gson = GsonFactory.createGson();

            JsonElement je = gson.toJsonTree(this.usertoAuthenticate);
            JsonObject jo = new JsonObject();
            jo.add("user", je);

            String requestUrl = Constants.webservicebase + "AuthenticationService.svc/Authenticate";
            HttpPost post = new HttpPost(requestUrl);
            StringEntity se = new StringEntity(jo.toString(), "UTF-8");
            se.setContentType("application/json");
            post.setEntity(se);

            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);

                String respJson = out.toString();

            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                authenticatedUser = gson.fromJson(respJson, User.class);
            } else{
                //Get the error.
                this.lastException = gson.fromJson(respJson, JsonException.class);
            }
        } catch (Exception ex) {
            this.lastException = new AsyncTaskException(ex);
        }

        return authenticatedUser;
    }


    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);

        if(this.lastException == null)
        {
           this.callback.afterSuccesfullyAuthenticated(user);
        } else {
            logger.log(Level.SEVERE, lastException.getExceptionMessage());
            this.callback.afterAuthenticationFailed(this.lastException);
        }
    }
}
