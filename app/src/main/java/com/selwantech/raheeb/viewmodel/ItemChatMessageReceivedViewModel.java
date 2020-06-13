
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.databinding.CellChatItemReceivedBinding;
import com.selwantech.raheeb.interfaces.ChatMessageRecyclerClick;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.utils.AppConstants;


public class ItemChatMessageReceivedViewModel extends BaseObservable {

    private final Context context;
    ChatMessageRecyclerClick mRecyclerClick;
    private ChatObject messages;
    private int position;


    CellChatItemReceivedBinding cellChatItemReceivedBinding;

    public ItemChatMessageReceivedViewModel(Context context, ChatObject messages,
                                            int position,
                                            CellChatItemReceivedBinding cellChatItemSentBinding,
                                            ChatMessageRecyclerClick mRecyclerClick) {
        this.context = context;
        this.messages = messages;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
        this.cellChatItemReceivedBinding = cellChatItemSentBinding;
        this.mRecyclerClick = mRecyclerClick;
//        if(messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.VOICE)){
//            try {
//                MediaPlayer mMediaPlayer = new MediaPlayer();
//                mMediaPlayer.setDataSource("https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3");
//                mMediaPlayer.prepare();
//                cellChatItemSentBinding.tvSeekDurationIn.setText(TimeUtils.formatTime(mMediaPlayer.getDuration()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
    }


    public ChatObject getMessages() {
        return messages;
    }

    public void setMessages(ChatObject messages) {
        this.messages = messages;
        notifyChange();
    }

    public void onItemClick(View view) {
//        mRecyclerClick.onClick(messages, position);
    }

    public void onPlayClick() {
        mRecyclerClick.onClick(messages, position, false);
    }

    public void onAcceptOfferClick(View view) {
        mRecyclerClick.onClick(messages, position, false);
    }

    public int isTextOrOffer() {
        return messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER) ||
                messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.TEXT)
                ? View.VISIBLE : View.GONE;
    }

    public int isVoice() {
        return messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.VOICE)
                ? View.VISIBLE : View.GONE;
    }

    public int isOffer() {
        return messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER)
                ? View.VISIBLE : View.GONE;
    }

}
