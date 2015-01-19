package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.teamhomeplan.homeplan.callback.AdjustDayLengthCallback;
import com.example.teamhomeplan.homeplan.exception.ServiceException;
import com.example.teamhomeplan.homeplan.tasks.AdjustDayLengthTask;

/**
 * Created by Edwin on 17-1-2015.
 */
public class AdjustDayLengthActivity extends Activity implements AdjustDayLengthCallback {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_day_length);

        TimePicker timePicker = (TimePicker) findViewById(R.id.tpAdjustDayLength);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(8);
        timePicker.setCurrentMinute(0);

        findViewById(R.id.btndaylengthSave).setOnClickListener(btnClick);
    }

    protected View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int hour = ((TimePicker) findViewById(R.id.tpAdjustDayLength)).getCurrentHour();
            int min = ((TimePicker) findViewById(R.id.tpAdjustDayLength)).getCurrentMinute();

            long length;

            if (hour == 0 && min == 0) {
                Toast.makeText(context, "Vul een langere duratie in", Toast.LENGTH_SHORT).show();
                return;
            } else {
                length = hour + min;
            }

            AdjustDayLengthTask adjustDayLengthTask = new AdjustDayLengthTask(AdjustDayLengthActivity.this, length);
            adjustDayLengthTask.execute();
        }
    };

    @Override
    public void afterAdjustSuccess() {
        this.finish();
    }

    @Override
    public void afterAdjustFail(ServiceException exception) {
        Toast.makeText(context, "Er ging iets fout met het verwerken", Toast.LENGTH_SHORT).show();
    }
}
