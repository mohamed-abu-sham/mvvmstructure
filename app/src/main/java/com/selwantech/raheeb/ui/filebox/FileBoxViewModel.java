package com.selwantech.raheeb.ui.filebox;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;
import androidx.databinding.ViewDataBinding;

import com.selwantech.raheeb.BuildConfig;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityFileBoxBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;

import java.io.File;

public class FileBoxViewModel extends BaseViewModel<FileBoxNavigator, ActivityFileBoxBinding> {

    protected static String state = "";
    static String mFilePath = "";

    public <V extends ViewDataBinding, N extends BaseNavigator> FileBoxViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (FileBoxNavigator) navigation, (ActivityFileBoxBinding) viewDataBinding);

    }

    public void openFile(File file) {
        Uri uri = FileProvider.getUriForFile(getMyContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (file.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if (file.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (file.toString().contains(".wav") || file.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (file.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (file.toString().contains(".jpg") || file.toString().contains(".jpeg") || file.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (file.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (file.toString().contains(".3gp") || file.toString().contains(".mpg") || file.toString().contains(".mpeg") || file.toString().contains(".mpe") || file.toString().contains(".mp4") || file.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((FileBoxActivity) getMyContext()).finish();
        try {
            getMyContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void downloadFile(String url) {
        try {
            String mBaseFolderPath = getBaseFolderPath();
            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }

            state = mBaseFolderPath + "/" + getNameOfFile(url);
            mFilePath = "file://" + mBaseFolderPath + "/" + getNameOfFile(url);

            if (!new File(state).exists()) {
                Uri downloadUri = Uri.parse(url);
                DownloadManager.Request req = new DownloadManager.Request(downloadUri);
                req.setDestinationUri(Uri.parse(mFilePath));
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                DownloadManager dm = (DownloadManager) getMyContext().getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(req);
                ((FileBoxActivity) getMyContext()).showLoading();
            } else {
                ((FileBoxActivity) getMyContext()).hideLoading();
                openFile(new File(state));
            }
        } catch (Exception e) {
            ((FileBoxActivity) getMyContext()).hideLoading();
            new OnLineDialog(getMyContext()) {
                @Override
                public void onPositiveButtonClicked() {
                    dismiss();
                    ((FileBoxActivity) getMyContext()).finish();
                }

                @Override
                public void onNegativeButtonClicked() {

                }
            }.showConfirmationDialog(DialogTypes.OK, getMyContext().getString(R.string.download),
                    getMyContext().getString(R.string.downloading_failed));
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
                + "Khadmat/Image" + File.separator;
        return mBaseFolderPath;
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

    @Override
    protected void setUp() {

    }
}
