package com.example.teamhomeplan.homeplan.domain;

import com.example.teamhomeplan.homeplan.enumerations.PlanActivityType;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Niek on 19/01/15.
 *
 * Domain class for PlanActivity as received by the backend
 */
public class PlanActivity {

    @SerializedName("PlanActivityId")
    private String planActivityId;

    @SerializedName("Type")
    private PlanActivityType type;

    @SerializedName("StartTimeMillis")
    private long startTimeMillis;

    @SerializedName("EndTimeMillis")
    private long endTimeMillis;

    @SerializedName("UserActivity")
    private UserActivity userActivity;

    public String getPlanActivityId() {
        return planActivityId;
    }

    public void setPlanActivityId(String planActivityId) {
        this.planActivityId = planActivityId;
    }

    public PlanActivityType getType() {
        return type;
    }

    public void setType(PlanActivityType type) {
        this.type = type;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public long getEndTimeMillis() {
        return endTimeMillis;
    }

    public void setEndTimeMillis(long endTimeMillis) {
        this.endTimeMillis = endTimeMillis;
    }

    public UserActivity getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(UserActivity userActivity) {
        this.userActivity = userActivity;
    }
}
