package com.selwantech.raheeb.model.notificationsdata;

import com.google.gson.annotations.SerializedName;

public class NotifyData {

    @SerializedName("acction_id")
    private int acctionId;

    @SerializedName("type")
    private String type;

    public int getAcctionId() {
        return acctionId;
    }

    public String getType() {
        return type;
    }
}