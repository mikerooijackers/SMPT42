package com.example.teamhomeplan.homeplan.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Niek on 19/01/15.
 * <p/>
 * Domain class for a plan received from the backend.
 */
public class Plan {


    @SerializedName("PlanId")
    private String planID;

    @SerializedName("StartDateTimeMillis")
    private double startDateTimeMillis;

    @SerializedName("EndDateTimeMillis")
    private double endDateTimeMillis;

    @SerializedName("PlannedActivities")
    private List<PlanActivity> plannedActivities;

    @SerializedName("ActualActivities")
    private List<PlanActivity> actualActivities;

    public Plan() {
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public double getStartDateTimeMillis() {
        return startDateTimeMillis;
    }

    public void setStartDateTimeMillis(double startDateTimeMillis) {
        this.startDateTimeMillis = startDateTimeMillis;
    }

    public double getEndDateTimeMillis() {
        return endDateTimeMillis;
    }

    public void setEndDateTimeMillis(double endDateTimeMillis) {
        this.endDateTimeMillis = endDateTimeMillis;
    }

    public List<PlanActivity> getPlannedActivities() {
        return plannedActivities;
    }

    public void setPlannedActivities(List<PlanActivity> plannedActivities) {
        this.plannedActivities = plannedActivities;
    }

    public List<PlanActivity> getActualActivities() {
        return actualActivities;
    }

    public void setActualActivities(List<PlanActivity> actualActivities) {
        this.actualActivities = actualActivities;
    }
}
