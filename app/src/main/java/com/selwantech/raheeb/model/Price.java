package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Price implements Serializable {

    @SerializedName("amount")
    private String amount;

    @SerializedName("formatted")
    private String formatted;

    @SerializedName("currency")
    private String currency;

    public String getAmount() {
        return amount;
    }

    public String getFormatted() {
        return formatted;
    }

    public String getCurrency() {
        return currency;
    }
}