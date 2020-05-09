package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    private static User objUser = null;
    @SerializedName("token")
    String token;
    @SerializedName("avatar")
    String avatar = "";
    @SerializedName("is_blocked")
    boolean is_blocked;
    @SerializedName("")
    boolean isSocial;
    @SerializedName("id")
    private long userID;
    @SerializedName("email")
    private String email = "";
    @SerializedName("social_id")
    private String social_id;
    @SerializedName("phone_number")
    private String phone = "";
    @SerializedName("name")
    private String name = "";
    @SerializedName("password")
    private String password = "";
    @SerializedName("password_confirmation")
    private String password_confirmation = "";

    private User() {

    }

    public static User getInstance() {
        if (objUser == null)
            objUser = new User();
        return objUser;
    }

    public static User getObjUser() {
        return objUser;
    }

    public void setObjUser(User objUser) {
        User.objUser = objUser;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getUserName() {
        return getName();
    }


    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public boolean isIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public boolean isSocial() {
        return isSocial;
    }

    public void setSocial(boolean social) {
        isSocial = social;
    }
}
