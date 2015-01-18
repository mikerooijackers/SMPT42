package com.example.teamhomeplan.homeplan.domain;

import android.os.Parcel;
import android.os.Parcelable;

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
public class UserActivity implements Parcelable {

    @SerializedName("UserActivityID")
    private String userActivityId;

    @SerializedName("UserID")
    private String userId;

    @SerializedName("Name")
    private String name;

    @SerializedName("IconType")
    private UserActivityIconType iconType;

    @SerializedName("PlannedDurationMilliseconds")
    private long plannedDuration; //Planned duration in milliseconds

    public UserActivity()
    {

    }

    protected UserActivity(Parcel in)
    {
        userActivityId = in.readString();
        userId = in.readString();
        name = in.readString();
        iconType = (UserActivityIconType) in.readSerializable();
        plannedDuration = in.readLong();

    }

    public String getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(String userActivityId) {
        this.userActivityId = userActivityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userActivityId);
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeSerializable(iconType);
        dest.writeLong(plannedDuration);
    }

    public static final Parcelable.Creator<UserActivity> CREATOR = new Parcelable.Creator<UserActivity>() {
        @Override
        public UserActivity createFromParcel(Parcel in) {
            return new UserActivity(in);
        }

        @Override
        public UserActivity[] newArray(int size) {
            return new UserActivity[size];
        }
    };

}
