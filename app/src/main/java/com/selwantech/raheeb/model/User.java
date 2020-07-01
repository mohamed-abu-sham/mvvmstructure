package com.selwantech.raheeb.model;

import android.view.View;

import com.google.gson.annotations.SerializedName;
import com.selwantech.raheeb.App;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.utils.AppConstants;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static User objUser = null;

    @SerializedName("id")
    private long userID;
    @SerializedName("name")
    private String name = "";
    @SerializedName("phone_number")
    private String phone = "";
    @SerializedName("email")
    private String email = "";
    @SerializedName("avatar")
    String avatar = "";
    @SerializedName("cover_image")
    String cover_image;
    @SerializedName("id_image")
    String id_image;
    @SerializedName("login_with")
    String login_with;
    @SerializedName("is_valid")
    boolean is_valid;
    @SerializedName("is_blocked")
    boolean is_blocked;
    @SerializedName("count_followers")
    int count_followers;
    @SerializedName("count_following")
    int count_following;
    @SerializedName("rate")
    private double rate;

    @SerializedName("social_id")
    private String social_id;
    @SerializedName("password")
    private String password = "";
    @SerializedName("password_confirmation")
    private String password_confirmation = "";
    @SerializedName("token")
    String token;
    @SerializedName("firebase_token")
    String firebase_token;
    @SerializedName("platform")
    String platform = "android";

    @SerializedName("is_send_email")
    boolean emailNotification;
    @SerializedName("is_send_notifications")
    boolean pushNotification;

    @SerializedName("show_rate_invite")
    boolean show_rate_invite;

    @SerializedName("twitter_frinds")
    ArrayList<String> twitter_frinds;

    public User() {

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

    public String getRateString() {
        return String.valueOf(rate);
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

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getId_image() {
        return id_image;
    }

    public void setId_image(String id_image) {
        this.id_image = id_image;
    }

    public String getLogin_with() {
        return login_with;
    }

    public boolean isLoggedInWithTwitter() {
        return getLogin_with().equals(AppConstants.LOGGED_IN_TYPE.TWITTER);
    }
    public void setLogin_with(String login_with) {
        this.login_with = login_with;
    }

    public boolean isIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public int getCount_followers() {
        return count_followers;
    }

    public void setCount_followers(int count_followers) {
        this.count_followers = count_followers;
    }

    public int getCount_following() {
        return count_following;
    }

    public void setCount_following(int count_following) {
        this.count_following = count_following;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public boolean isPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public int isShow_rate_invite() {
        return show_rate_invite ? View.VISIBLE : View.GONE;
    }

    public void setShow_rate_invite(boolean show_rate_invite) {
        this.show_rate_invite = show_rate_invite;
    }

    public ArrayList<String> getTwitter_frinds() {
        return twitter_frinds;
    }

    public void setTwitter_frinds(ArrayList<String> twitter_frinds) {
        this.twitter_frinds = twitter_frinds;
    }

    public int isTwitterFriends() {
        return twitter_frinds != null && twitter_frinds.size() > 0 ? View.VISIBLE : View.GONE;
    }

    public String getFirstTwitterFriend() {
        return twitter_frinds != null && twitter_frinds.size() > 0 ? twitter_frinds.get(0) : "";
    }

    public String getTwitterFriendsNumber() {
        return twitter_frinds != null && twitter_frinds.size() > 0 ?
                twitter_frinds.size() + " " +
                        App.getInstance().getResources().getString(R.string.of_your_twitter_friends_are_on_Raheeb)
                : "";
    }
}
