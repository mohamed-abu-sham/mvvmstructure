package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;
import com.selwantech.raheeb.utils.AppConstants;

public class SocialLogin {

    @SerializedName("email")
    String email;

    @SerializedName("user_id")
    String user_id;

    @SerializedName("token")
    String token;

    @SerializedName("provider")
    String provider;

    @SerializedName("platform")
    String platform;

    public SocialLogin(String email, String user_id, String token, String provider) {
        this.email = email;
        this.user_id = user_id;
        this.token = token;
        this.provider = provider;
        this.platform = AppConstants.PLATFORM;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
