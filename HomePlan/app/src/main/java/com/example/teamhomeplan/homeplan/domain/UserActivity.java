package com.example.teamhomeplan.homeplan.domain;

import com.example.teamhomeplan.homeplan.enumerations.UserActivityIconType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Niek on 12/12/14.
 *
 * UserActivity object as received by the service.
 */
public class UserActivity {

    @SerializedName("UserActivityID")
    private UUID userActivityId;

    @SerializedName("UserID")
    private UUID userId;

    @SerializedName("Name")
    private String name;

    @SerializedName("IconType")
    private UserActivityIconType iconType;

    @SerializedName("PlannedDurationMilliseconds")
    private long plannedDuration; //Planned duration in milliseconds

    public UUID getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(UUID userActivityId) {
        this.userActivityId = userActivityId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserActivityIconType getIconType() {
        return iconType;
    }

    public void setIconType(UserActivityIconType iconType) {
        this.iconType = iconType;
    }

    public long getPlannedDuration() {
        return plannedDuration;
    }

    public void setPlannedDuration(long plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    public String getPlannedDurationText()
    {
        long minute = (plannedDuration / (1000 * 60)) % 60;
        long hour = (plannedDuration / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d", hour, minute);
    }
}
