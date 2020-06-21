
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.SeekBar;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.CellChatItemReceivedBinding;
import com.selwantech.raheeb.interfaces.ChatMessageRecyclerClick;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.utils.AppConstants;

import java.io.IOException;

import androidx.databinding.BaseObservable;


public class ItemChatMessageReceivedViewModel extends BaseObservable {

    private final Context context;
    ChatMessageRecyclerClick mRecyclerClick;
    private ChatObject messages;
    private int position;

    CellChatItemReceivedBinding cellChatItemReceivedBinding;
    MediaPlayer mMediaPlayer ;
    public ItemChatMessageReceivedViewModel(Context context, ChatObject messages,
                                            int position,
                                            CellChatItemReceivedBinding cellChatItemReceivedBinding,
                                            ChatMessageRecyclerClick mRecyclerClick) {
        this.context = context;
        this.messages = messages;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
        this.cellChatItemReceivedBinding = cellChatItemReceivedBinding;
        this.mRecyclerClick = mRecyclerClick;
        cellChatItemReceivedBinding.seekAudioIn.setEnabled(false);
//        this.mMediaPlayer = mediaPlayer ;
//        if(messages.getMessage_type().equals(AppConstants.MESSAGE_TYPE.VOICE)){
//            setUpAudio();
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

//        if (!mMediaPlayer.isPlaying()) {
//            play();
//        } else {
//            mMediaPlayer.pause();
//            cellChatItemReceivedBinding.play.setImageResource(R.drawable.ic_play);
//        }
    }

    private void play() {
        cellChatItemReceivedBinding.play.setImageResource(R.drawable.ic_pause);
       // cellChatItemReceivedBinding.seekAudioIn.removeCallbacks(moveSeekBarThread);
//        seekHandler.postDelayed(moveSeekBarThread, 100);
//
//        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                if (seekHandler != null && moveSeekBarThread != null) {
//                    seekHandler.removeCallbacks(moveSeekBarThread);
//                }
//                cellChatItemReceivedBinding.play.setImageResource(R.drawable.ic_play);
//                cellChatItemReceivedBinding.seekAudioIn.setProgress(0);
//            }
//        });

        mMediaPlayer.start();
      //  mMediaPlayer
    }


    private void setUpAudio() {
        cellChatItemReceivedBinding.seekAudioIn.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMediaPlayer != null) {
                    if (fromUser) {
                        mMediaPlayer.seekTo(progress);
                    }
                    if (progress == mMediaPlayer.getDuration()) {

                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            mMediaPlayer.reset();
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer.setDataSource(messages.getMessage());
            mMediaPlayer.prepare();
//            cellChatItemReceivedBinding.tvSeekDurationIn.setText(TimeUtils.formatTime(mMediaPlayer.getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                && messages.getOffer().getStatus().equals(AppConstants.OFFER_STATUS.WAITING)
                ? View.VISIBLE : View.GONE;
    }

}
