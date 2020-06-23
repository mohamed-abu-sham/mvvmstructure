package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChatObject implements Serializable {

    boolean sent = true;
    boolean showProgress = false;
    @SerializedName("message")
    private String message;
    @SerializedName("id")
    private int id;
    @SerializedName("message_type")
    private String message_type;
    @SerializedName("date")
    private String date;
    @SerializedName("sender")
    private Sender sender;
    @SerializedName("chat_id")
    private int chat_id;

    @SerializedName("offer_id")
    private int offer_id;
    @SerializedName("offer")
    Offer offer ;

    public ChatObject(int id, String message_type, String message, String date, Sender sender) {
        this.id = id;
        this.message_type = message_type;
        this.message = message;
        this.date = date;
        this.sender = sender;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
