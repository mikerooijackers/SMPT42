package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;
import android.util.Log;
import com.example.teamhomeplan.homeplan.callback.CreateTaskCallback;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.exception.ServiceException;

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
        return null;
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
