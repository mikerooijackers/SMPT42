package com.example.teamhomeplan.homeplan.exception;

/**
 * Created by Niek on 12/12/2014.
 *
 * ServiceException
 */
public interface ServiceException {
    String getExceptionMessage();
    String getExceptionStackTrace();
    String getExceptionType();
}
