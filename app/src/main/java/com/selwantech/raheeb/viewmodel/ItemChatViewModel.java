
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.chatdata.Chat;


public class ItemChatViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private Chat messages;
    private int position;

    public ItemChatViewModel(Context context, Chat messages, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.messages = messages;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public Chat getMessages() {
        return messages;
    }

    public void setMessages(Chat messages) {
        this.messages = messages;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(messages, position);
    }

}
