package com.example.teamhomeplan.homeplan.exception;

import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Niek on 12/12/2014.
 *
 * Exception returned from the Json webservice
 */
public class JsonException implements ServiceException{

    @SerializedName("Message")
    private String message;
    @SerializedName("ExceptionType")
    private String exceptionType;
    @SerializedName("StackTrace")
    private String stackTrace;

    @Override
    public String getExceptionMessage() {
        return message;
    }

    @Override
    public String getExceptionStackTrace() {
        return stackTrace;
    }

    @Override
    public String getExceptionType() {
        return exceptionType;
    }

    @Override
    public String toString()
    {
        return this.getExceptionType() + this.getExceptionMessage() + "\n" + this.getExceptionStackTrace();
    }
}
