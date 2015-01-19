package com.example.teamhomeplan.homeplan.tasks;

import android.os.AsyncTask;
import com.example.teamhomeplan.homeplan.callback.AdjustDayLengthCallback;
import com.example.teamhomeplan.homeplan.exception.AsyncTaskException;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.helper.Constants;

/**
 * Created by Edwin on 19-Jan-15.
 */
public class AdjustDayLengthTask extends AsyncTask<Void, Void, Long> {

    private ServiceException serviceException;
    private AdjustDayLengthCallback adjustDayLengthCallback;
    private Long length;

    public AdjustDayLengthTask(AdjustDayLengthCallback adjustDayLengthCallback, Long length) {
        if (this.adjustDayLengthCallback == null) {
            throw new IllegalArgumentException("callback");
        }
        this.serviceException = null;
        this.adjustDayLengthCallback = adjustDayLengthCallback;
        this.length = length;
    }

    @Override
    protected Long doInBackground(Void... params) {
        String requestURL = Constants.webservicebase + "";
        length = 0L;

        try {

        } catch (Exception ex) {
            this.serviceException = new AsyncTaskException(ex);
        }

        return this.length;
    }
}
