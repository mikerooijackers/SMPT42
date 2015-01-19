package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;

import com.example.teamhomeplan.homeplan.callback.CapturePhotoCallback;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Constants;

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
            // TODO fill in thissssssssssssssssssssssss
        } catch (Exception ex) {
            this.serviceException = new AsyncTaskException(ex);
        }

        return image;
    }
}
