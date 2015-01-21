package com.example.teamhomeplan.homeplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.teamhomeplan.homeplan.domain.Plan;
import com.example.teamhomeplan.homeplan.domain.PlanActivity;
import com.example.teamhomeplan.homeplan.helper.Session;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Niek on 19-1-2015
 * <p/>
 * Activity for creating the charts and keeping track of the current day of work.
 */
public class DayOverviewActivity extends Activity implements OnChartValueSelectedListener {

    private Plan loadedPlan;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_overview);

        //The current loaded and generated plan.
        this.loadedPlan = Session.currentLoadedPlan;

        this.pieChart = (PieChart) findViewById(R.id.day_overview_chart);

        pieChart.setHoleColor(getResources().getColor(android.R.color.black));
        pieChart.setHoleRadius(60f);
        pieChart.setCenterText("TODAY: TIME REMAINING");
        pieChart.setDrawYValues(false);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);
        pieChart.setDrawXValues(true);

        pieChart.setUsePercentValues(true);


        setChartData();

        pieChart.animateXY(1500, 1500);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);


        Button photoButton = (Button) findViewById(R.id.day_overview_share_photo);
        photoButton.setOnClickListener(onSharePhotoClickListener);
    }


    private void setChartData() {

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<>();

        //Total amount of milliseconds for the plan
        double totalMillis = loadedPlan.getEndDateTimeMillis() - loadedPlan.getStartDateTimeMillis();

        for (int i = 0; i < loadedPlan.getPlannedActivities().size(); i++) {
            PlanActivity pa = loadedPlan.getPlannedActivities().get(i);
            String message;
            switch (pa.getType()) {
                case PERSONAL:
                    message = pa.getUserActivity().getName();
                    break;
                case INCIDENTAL:
                    message = "Incident";
                    break;
                default:
                    message = "Work";
                    break;
            }

            xVals.add(message);

            //Duration of this PlanActivity
            double durationMillis = pa.getEndTimeMillis() - pa.getStartTimeMillis();

            yVals.add(new Entry((float) (durationMillis / totalMillis) * 100, i, pa));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        PieDataSet dataSet = new PieDataSet(yVals, "Day planning");
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        pieChart.setData(data);


        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    private View.OnClickListener onSharePhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DayOverviewActivity.this, CapturePhotoActivity.class);
            startActivity(intent);
        }
    };


    @Override
    public void onValueSelected(Entry entry, int i) {
        //TODO: show more info about the selected item
    }

    @Override
    public void onNothingSelected() {
        //TODO: hide the extra info we got from selecting part of the pie chart.
    }
}
