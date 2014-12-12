package com.example.teamhomeplan.homeplan.domain;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by Niek on 12/12/14.
 *
 * User class as received by the backend
 */
public class User {

    @SerializedName("UserId")
    private UUID userId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private String password; //uglyyy

    @SerializedName("AvatarUrl")
    private String avatarUrl;


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
}
