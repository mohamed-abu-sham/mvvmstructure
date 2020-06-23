package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Offer implements Serializable {

	@SerializedName("price")
	private String price;

	@SerializedName("status")
	private String status;

	public String getPrice(){
		return price;
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}