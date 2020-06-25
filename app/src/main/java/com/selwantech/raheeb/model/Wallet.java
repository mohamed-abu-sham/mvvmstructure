package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Wallet implements Serializable {

    @SerializedName("balance")
    Price balance;
    @SerializedName("transactions")
    ArrayList<Transaction> transactionArrayList;

    public Price getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactionArrayList() {
        return transactionArrayList;
    }
}
