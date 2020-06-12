
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.utils.AppConstants;


public class ItemChatMessageViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private ChatObject messages;
    private int position;

    public ItemChatMessageViewModel(Context context, ChatObject messages, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.messages = messages;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public ChatObject getMessages() {
        return messages;
    }

    public void setMessages(ChatObject messages) {
        this.messages = messages;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(messages, position);
    }

    public void onAcceptOfferClick(View view) {

    }

    public int isOffer() {
        return messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER)
                ? View.VISIBLE : View.GONE;
    }
}
