package com.selwantech.raheeb.utils;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.selwantech.raheeb.BuildConfig;
import com.selwantech.raheeb.ui.main.MainActivity;

import java.io.File;

public class DownloadUtil {

    private static final String VOICE_FOLDER = "RAHEEB/Voice";
    protected static String state = "";
    static String mFilePath = "";
    Context mContext;
    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {

        }
    };

    public DownloadUtil(Context context) {
        this.mContext = context;
    }

    public void downloadFile(String url) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((MainActivity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1020);
        } else {
            mContext.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            try {
                String mBaseFolderPath = getBaseFolderPath();
                if (!new File(mBaseFolderPath).exists()) {
                    new File(mBaseFolderPath).mkdir();
                }

//                state = getFilename(url);
//                mFilePath = "file://" + state;


                Uri downloadUri = Uri.parse(url);
                Uri file = FileProvider.getUriForFile(mContext,
                        BuildConfig.APPLICATION_ID + ".fileprovider",
                        getFilename(url));
                DownloadManager.Request req = new DownloadManager.Request(downloadUri);
                req.setDestinationUri(file);

//                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(req);

            } catch (Exception e) {
                Log.d("", e.getLocalizedMessage());
            }
        }
    }

    private String getNameOfFile(String url) {
        String[] urlSplit = url.split("/");
        return urlSplit[urlSplit.length - 1];
    }

    private String getBaseFolderPath() {
        String mBaseFolderPath = android.os.Environment
                .getExternalStorageDirectory()
                + File.separator
                + "RAHEEB/VOICE" + File.separator;
        return mBaseFolderPath;
    }

    private File getFilename(String url) {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, VOICE_FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file.getAbsolutePath() + "/" + getNameOfFile(url));
    }

    protected String getState() {
        return state;
    }

    protected void setState(String state) {
        state = state;
    }

    protected String getmFilePath() {
        return mFilePath;
    }

    protected void setmFilePath(String mFilePath) {
        mFilePath = mFilePath;
    }
}
