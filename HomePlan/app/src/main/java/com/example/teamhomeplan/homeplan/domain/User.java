package com.example.teamhomeplan.homeplan.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.internal.as;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by Niek on 12/12/14.
 *
 * User class as received by the backend
 */
public class User implements Parcelable {

    //Default constructor for JSON
    public User(){};

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    @SerializedName("UserId")
    private String userId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private String password; //uglyyy

    @SerializedName("AvatarUrl")
    private String avatarUrl;


    public String getUserId() {
        return userId;
    }

    public void setUserId(UUID String) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    protected User(Parcel in) {
        userId = in.readString();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        avatarUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(avatarUrl);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}