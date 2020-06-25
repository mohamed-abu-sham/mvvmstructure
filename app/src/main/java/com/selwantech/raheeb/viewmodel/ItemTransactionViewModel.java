
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Transaction;

import androidx.databinding.BaseObservable;


public class ItemTransactionViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private Transaction transaction;
    private int position;

    public ItemTransactionViewModel(Context context, Transaction transaction, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.transaction = transaction;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(transaction, position);
    }

}
