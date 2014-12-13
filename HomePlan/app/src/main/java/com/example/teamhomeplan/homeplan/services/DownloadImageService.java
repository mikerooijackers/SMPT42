package com.example.teamhomeplan.homeplan.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.teamhomeplan.homeplan.callback.ImageDownloadedCallback;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.ServiceException;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Niek on 12/13/2014.
 *
 * DownloadImageService
 */
public class DownloadImageService extends AsyncTask<Void, Void, Bitmap> {
    private ImageDownloadedCallback callback;
    private ServiceException lastException;
    private String requestUrl;

    public DownloadImageService(ImageDownloadedCallback callback, String requestUrl)
    {
        this.callback = callback;
        this.requestUrl = requestUrl;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(this.requestUrl).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch(Exception e)
        {
            this.lastException = new AsyncTaskException(e);
        }

        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(this.lastException == null)
        {
            this.callback.onAfterImageDownloaded(bitmap);
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, this.lastException.toString());
            this.callback.onAfterDownloadFailed(this.lastException);
        }
    }
}
