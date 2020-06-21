package com.selwantech.raheeb.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.DialogAudioPlayerBinding;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.utils.DownloadUtil;
import com.selwantech.raheeb.utils.TimeUtils;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class AudioPlayerDialog extends Dialog {

    DialogAudioPlayerBinding dialogAudioPlayerBinding;

    MediaPlayer mMediaPlayer;
    ChatObject chatObject;
    Handler seekHandler = new Handler();

    private Runnable moveSeekBarThread = new Runnable() {
        public void run() {
            if (mMediaPlayer != null) {
                int mediaMax_new = mMediaPlayer.getDuration();
                int mediaPos_new = mMediaPlayer.getCurrentPosition();
                int mediaTime = mMediaPlayer.getDuration() - mMediaPlayer.getCurrentPosition();
                dialogAudioPlayerBinding.seekAudioIn.setProgress(mediaPos_new);
                dialogAudioPlayerBinding.seekAudioIn.setMax(mediaMax_new);
                dialogAudioPlayerBinding.seekAudioIn.setProgress(mediaPos_new);
                dialogAudioPlayerBinding.seekAudioIn.setMax(mediaMax_new);
                dialogAudioPlayerBinding.tvSeekDurationIn.setText(TimeUtils.formatTime(Math.abs(mediaTime)));
//                dialogAudioPlayerBinding.tvVoiceDuration.setText(TimeUtils.formatTime(mediaMax_new));
                seekHandler.postDelayed(this, 1000); //Looping the thread after 0.1 second
            }
        }
    };


    public AudioPlayerDialog(@NonNull Context context, ChatObject chatObject) {
        super(context);
        this.chatObject = chatObject;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogAudioPlayerBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_audio_player, null, false);
        setContentView(dialogAudioPlayerBinding.getRoot());

        dialogAudioPlayerBinding.setViewModel(this);
//        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
//        InsetDrawable inset = new InsetDrawable(back, 10);
//        setCancelable(true);
//        getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        getWindow().setBackgroundDrawable(inset);
//        getWindow().setGravity(Gravity.CENTER);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                AudioPlayerDialog.this.onCancel(dialog);
            }
        });
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                initMediaPlayer();
            }
        });
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        setUpAudio();
    }

    private void setUpAudio() {
        dialogAudioPlayerBinding.seekAudioIn.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(chatObject.getMessage());
            mMediaPlayer.prepare();
            dialogAudioPlayerBinding.tvSeekDurationIn.setText(TimeUtils.formatTime(mMediaPlayer.getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onDownloadClick(View view) {
        DownloadUtil downloadUtil = new DownloadUtil(getContext());
        downloadUtil.downloadFile(chatObject.getMessage());
    }

    public void onPlayClick() {
        if (!mMediaPlayer.isPlaying()) {
            play();
        } else {
            mMediaPlayer.pause();
            dialogAudioPlayerBinding.play.setImageResource(R.drawable.ic_play);
        }
    }

    private void play() {
        dialogAudioPlayerBinding.play.setImageResource(R.drawable.ic_pause);
        dialogAudioPlayerBinding.seekAudioIn.removeCallbacks(moveSeekBarThread);
        seekHandler.postDelayed(moveSeekBarThread, 1000);

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (seekHandler != null && moveSeekBarThread != null) {
                    seekHandler.removeCallbacks(moveSeekBarThread);
                }
                dialogAudioPlayerBinding.play.setImageResource(R.drawable.ic_play);
                dialogAudioPlayerBinding.seekAudioIn.setProgress(0);
                dialogAudioPlayerBinding.tvSeekDurationIn.setText(TimeUtils.formatTime(mMediaPlayer.getDuration()));
            }
        });

        mMediaPlayer.start();

    }

    public void onCancel(@NonNull DialogInterface dialog) {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }




}
