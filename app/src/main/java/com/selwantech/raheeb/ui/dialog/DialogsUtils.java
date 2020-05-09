package com.selwantech.raheeb.ui.dialog;


import android.app.ProgressDialog;
import android.content.Context;

import com.selwantech.raheeb.R;


public class DialogsUtils {

    private ProgressDialog progressDialog;

    public DialogsUtils(Context context, boolean withProgress) {
        if (withProgress) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(android.R.style.Theme_DeviceDefault_Light_DarkActionBar);
            progressDialog.setMessage(context.getResources().getString(R.string.Loading));
            startProgressDialog();
        }
    }

    public void startProgressDialog() {
        if (progressDialog != null) {
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    public void endProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

}
