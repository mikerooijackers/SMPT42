package com.example.teamhomeplan.homeplan.domain;

import com.example.teamhomeplan.homeplan.enumerations.UserActivityIconType;

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

    @SerializedName("PlannedDuration")
    private String plannedDuration; //TODO: Make this a timespan or long ticks.

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

    public String getPlannedDuration() {
        return plannedDuration;
    }

    public void setPlannedDuration(String plannedDuration) {
        this.plannedDuration = plannedDuration;
    }
}
