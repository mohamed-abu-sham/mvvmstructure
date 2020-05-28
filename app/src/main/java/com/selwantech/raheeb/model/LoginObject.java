package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

public class LoginObject {

    @SerializedName("phone_number")
    String phone_number;

    @SerializedName("password")
    String password;

    @SerializedName("firebase_token")
    String firebase_token;

    @SerializedName("platform")
    String platform = "android";

    public LoginObject(String phone_number, String password, String firebase_token) {
        this.phone_number = phone_number;
        this.password = password;
        this.firebase_token = firebase_token;
    }
}
