package com.example.teamhomeplan.homeplan.exception;

import com.example.teamhomeplan.homeplan.exception.ServiceException;

/**
 * Created by Niek on 12/12/2014.
 */
public class AsyncTaskException extends Exception implements ServiceException  {
    private String exceptionType;

    public AsyncTaskException(Throwable exception)
    {
        super(exception);
        exceptionType = exception.getClass().getName();
    }


    @Override
    public String getExceptionMessage() {
        return this.getMessage();
    }

    @Override
    public String getExceptionStackTrace() {
        return this.getStackTrace().toString();
    }

    @Override
    public String getExceptionType() {
        return this.exceptionType;
    }
}
