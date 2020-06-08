package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BuyNow implements Serializable {

    @SerializedName("shipping_method")
    String shipping_method = "cach";
    @SerializedName("address")
    Address address;

    public String getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
