package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PriceDetails implements Serializable {

    @SerializedName("item_price")
    Price item_price;

    @SerializedName("shipping")
    Price shipping_price;

    @SerializedName("sale_tax")
    Price sale_tax;

    @SerializedName("total_price")
    Price total_price;

    public Price getItem_price() {
        return item_price;
    }

    public Price getShipping_price() {
        return shipping_price;
    }

    public Price getSale_tax() {
        return sale_tax;
    }

    public Price getTotal_price() {
        return total_price;
    }
}
