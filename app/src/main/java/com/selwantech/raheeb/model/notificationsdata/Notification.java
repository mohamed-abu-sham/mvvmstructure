package com.selwantech.raheeb.model.notificationsdata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notification implements Serializable {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("notify_data")
    private NotifyData notifyData;

    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("notify")
    private Notify notify;

    @SerializedName("result_firebase")
    private String resultFirebase;

    @SerializedName("date")
    private String date;

    public int getUserId() {
        return userId;
    }

    public NotifyData getNotifyData() {
        return notifyData;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Notify getNotify() {
        return notify;
    }

    public String getDate() {
        return date;
    }

    public String getResultFirebase() {
        return resultFirebase;
    }
}