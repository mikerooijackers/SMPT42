package com.example.teamhomeplan.homeplan.callback;

import com.example.teamhomeplan.homeplan.exception.ServiceException;

/**
 * Created by Edwin on 19-Jan-15.
 */
public interface CapturePhotoCallback {
    void afterPhotoCapturedSuccess();
    void afterPhotoCapturedFailed(ServiceException exception);
}
