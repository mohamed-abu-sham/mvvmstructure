package com.selwantech.raheeb.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;

import androidx.core.app.ActivityCompat;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;

import java.io.File;
import java.io.IOException;

public class AudioRecorder {

    public final static int RECORD_REQUEST_CODE = 1111;
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "RAHEEB/AudioRecorder";
    RecordCallBack recordCallBack;
    Activity activity;
    Vibrator vibrator;
    String recordPath = "";
    private MediaRecorder recorder;

    private int currentFormat = 0;
    private int[] output_formats = {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};
    private String[] file_exts = {AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP};

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            //AppLog.logString("Error: " + what + ", " + extra);
        }
    };
    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            //AppLog.logString("Warning: " + what + ", " + extra);
        }
    };

    public AudioRecorder(Activity activity, RecordCallBack recordCallBack) {
        this.activity = activity;
        this.recordCallBack = recordCallBack;
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);
        recorder.setAudioSamplingRate(16000);
        recordPath = getFilename();
        recorder.setOutputFile(recordPath);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long getDuration(String recordPath) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(recordPath);
        String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return Long.parseLong(durationStr);
    }

    private void stopRecording() {
        if (null != recorder) {
            try {
                recorder.stop();
                recorder.reset();
                recorder.release();
                if(TimeUtils.millisecondSec(getDuration(recordPath)) >= 2){
                    new OnLineDialog(activity) {
                        @Override
                        public void onPositiveButtonClicked() {
                            recordCallBack.callback(recordPath);
                            dismiss();
                        }

                        @Override
                        public void onNegativeButtonClicked() {
                            removeRecord();
                            dismiss();
                        }
                    }.showYesNoDialog(activity.getResources().getString(R.string.are_you_sure_to_send_this_vois_message));
                }else{
                    removeRecord();
                    recordCallBack.callback(null);
                }


            } catch (RuntimeException stopException) {
                removeRecord();
            }
        }
    }

    private void removeRecord() {
        if (new File(recordPath).exists()) {
            new File(recordPath).delete();
        }
        recordPath = "";
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }


    public boolean checkRecorderPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void recordAction(MotionEvent event) {
        if (checkRecorderPermission())
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(200);
                    }
                    startRecording();
                    break;
                case MotionEvent.ACTION_UP:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(200);
                    }
                    stopRecording();
                    break;
            }
    }

    public interface RecordCallBack {
        void callback(String recordPath);
    }

}
