
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

import com.selwantech.raheeb.databinding.CellChatItemSentBinding;
import com.selwantech.raheeb.interfaces.ChatMessageRecyclerClick;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.utils.AppConstants;

import androidx.databinding.BaseObservable;


public class ItemChatMessageSentViewModel extends BaseObservable {

    private final Context context;
    ChatMessageRecyclerClick mRecyclerClick;
    MediaPlayer mMediaPlayer;
    CellChatItemSentBinding cellChatItemSentBinding;
    private ChatObject messages;
    private int position;

//    MutableLiveData<ChatObject> chatObjectMutableLiveData = new MutableLiveData<>();

    public ItemChatMessageSentViewModel(Context context,
                                        ChatObject messages,
                                        int position,
                                        CellChatItemSentBinding cellChatItemSentBinding,
                                        ChatMessageRecyclerClick mRecyclerClick,
                                        MediaPlayer mediaPlayer) {
        this.context = context;
        this.messages = messages;
        this.position = position;
        this.cellChatItemSentBinding = cellChatItemSentBinding;
        this.mRecyclerClick = mRecyclerClick;
        this.mMediaPlayer = mediaPlayer;
        cellChatItemSentBinding.seekAudioOut.setEnabled(false);
//        if(messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.VOICE)){
//            if(URLUtil.isValidUrl(messages.getMessage()))
//                cellChatItemSentBinding.audioPlayer.setAudioTarget(messages.getMessage());
//            else
//                cellChatItemSentBinding.audioPlayer.setAudioTarget(Uri.parse(messages.getMessage()));
//        }
//            try {
//                MediaPlayer mMediaPlayer = new MediaPlayer();
//                mMediaPlayer.setDataSource("https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3");
//                mMediaPlayer.prepare();
//                cellChatItemSentBinding.tvSeekDurationOut.setText(TimeUtils.formatTime(mMediaPlayer.getDuration()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
        isSent();
        setView();
    }

    private void setView() {
        if (messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER) ||
                messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.TEXT)) {
            cellChatItemSentBinding.tvOutMessage.setVisibility(View.VISIBLE);
            cellChatItemSentBinding.linearPlayer.setVisibility(View.GONE);
        } else {
            cellChatItemSentBinding.linearPlayer.setVisibility(View.VISIBLE);
            cellChatItemSentBinding.tvOutMessage.setVisibility(View.GONE);
        }
    }


    public ChatObject getMessages() {
        return messages;
    }

    public void setMessages(ChatObject messages) {
        this.messages = messages;
        notifyChange();
        isSent();
        setView();
    }

    public void onItemClick(View view) {
        if (!messages.isSent() && messages.getId() <= 0) {
            mRecyclerClick.onClick(messages, position, true);
        }
    }

    public void onPlayClick() {
        mRecyclerClick.onClick(messages, position, false);
    }

    public void onAcceptOfferClick(View view) {

    }

    public int isOffer() {
        return messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER)
                ? View.VISIBLE : View.GONE;
    }

    public int isText() {
        return messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER)
                ? View.VISIBLE : View.GONE;
    }

    public int isVoice() {
        return messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.VOICE)
                ? View.VISIBLE : View.GONE;
    }

    public int isTextOrOffer() {
        return messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER) ||
                messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.TEXT)
                ? View.VISIBLE : View.GONE;
    }


    public void isSent() {
        if (!messages.isSent() && messages.getId() < 0)
            cellChatItemSentBinding.cardResend.setVisibility(View.VISIBLE);
        else cellChatItemSentBinding.cardResend.setVisibility(View.GONE);
    }

}
