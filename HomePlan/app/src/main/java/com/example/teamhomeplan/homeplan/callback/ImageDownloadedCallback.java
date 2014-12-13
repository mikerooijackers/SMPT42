package com.example.teamhomeplan.homeplan.callback;

import android.graphics.Bitmap;

import com.example.teamhomeplan.homeplan.exception.ServiceException;

/**
 * Created by Niek on 12/13/2014.
 *
 * Callback for downloading images.
 */
public interface ImageDownloadedCallback {
    void onAfterImageDownloaded(Bitmap image);

    void onAfterDownloadFailed(ServiceException ex);
}
