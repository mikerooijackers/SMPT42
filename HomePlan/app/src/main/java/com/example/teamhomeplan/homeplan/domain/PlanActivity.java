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
    private double startTimeMillis;

    @SerializedName("EndTimeMillis")
    private double endTimeMillis;

}
