package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifyPhoneResponse implements Serializable {

    @SerializedName("token")
    String token;

    public String getToken() {
        return token;
    }
}
