package com.selwantech.raheeb.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.DialogAlertBinding;
import com.selwantech.raheeb.databinding.DialogInputOneFieledBinding;
import com.selwantech.raheeb.databinding.DialogLanguageBinding;
import com.selwantech.raheeb.databinding.DialogLogoutBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.interfaces.InputDialogCallback;
import com.selwantech.raheeb.utils.LanguageUtils;

import androidx.databinding.DataBindingUtil;

public abstract class OnLineDialog extends Dialog {

    public Context mContext;

    public OnLineDialog(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }


    public static void showPopupLanguage(Activity mContext) {
        final Dialog dialog = new Dialog(mContext);
        DialogLanguageBinding dialogLanguageBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_language, null, false);
        dialog.setContentView(dialogLanguageBinding.getRoot());


        String lang = LanguageUtils.getLanguage(mContext);

        if (lang.equals("ar")) {
            dialogLanguageBinding.ar.setChecked(true);
        } else {
            dialogLanguageBinding.en.setChecked(true);
        }

        dialogLanguageBinding.OK.setOnClickListener(view -> {
            if (dialogLanguageBinding.ar.isChecked()) {
                LanguageUtils.updateLanguage(mContext, "ar");
            } else {
                LanguageUtils.updateLanguage(mContext, "en");
            }
        });
        dialogLanguageBinding.Cancel.setOnClickListener(view -> dialog.dismiss());
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 10);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    public static void showCustomViewInputDialog(Context mContext, String mTitle, InputDialogCallback inputDialogCallback) {
        final Dialog inputDialog = new Dialog(mContext);
        DialogInputOneFieledBinding dialogInputOneFieledBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_input_one_fieled, null, false);
        inputDialog.setContentView(dialogInputOneFieledBinding.getRoot());

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 10);
        inputDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        inputDialog.getWindow().setBackgroundDrawable(inset);
        inputDialog.getWindow().setGravity(Gravity.CENTER);
        inputDialog.show();

        dialogInputOneFieledBinding.tvTitle.setText(mTitle);

        dialogInputOneFieledBinding.tvOK.setOnClickListener(v -> {
            if (dialogInputOneFieledBinding.edInput.getText().toString().isEmpty()) {
                dialogInputOneFieledBinding.edInput.setError(mContext.getResources().getString(R.string.this_fieled_is_required));
                dialogInputOneFieledBinding.edInput.requestFocus();
            } else {
                inputDialogCallback.onFinished(dialogInputOneFieledBinding.edInput.getText().toString());
                inputDialog.dismiss();
            }
        });
        dialogInputOneFieledBinding.tvCancel.setOnClickListener(v -> inputDialog.dismiss());
        inputDialog.setCancelable(false);
        inputDialog.show();
    }

    public abstract void onPositiveButtonClicked();

    public abstract void onNegativeButtonClicked();

    //
//    public static void showDefaultViewInputDialog(Context context, String title, int inputType, InputDialogCallback inputDialogCallback) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(title);
//        final EditText input = new EditText(context);
//        input.setHint(title);
//        input.setInputType(inputType);
//
//        final EditText input2 = new EditText(context);
//        input2.setHint(context.getResources().getString(R.string.price));
//        input2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//
//        LinearLayout lay = new LinearLayout(context);
//        lay.setOrientation(LinearLayout.VERTICAL);
//        lay.addView(input);
//        lay.addView(input2);
//        builder.setView(lay);
//        builder.setPositiveButton(context.getResources().getString(R.string.OK),
//                (dialog, which) -> inputDialogCallback.onFinished(
//                        -1, Double.parseDouble(input2.getText().toString().trim())));
//        builder.setNegativeButton(context.getResources().getString(R.string.Cancel), (dialog, which) -> dialog.cancel());
//        builder.show();
//    }
//
    public void showConfirmationDialog(DialogTypes type, String mTitle, String mMessage) {
        final Dialog dialog = this;

        DialogAlertBinding dialogAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_alert, null, false);
        dialog.setContentView(dialogAlertBinding.getRoot());
        switch (type) {
            case OK:
                dialogAlertBinding.tvOK.setVisibility(View.VISIBLE);
                dialogAlertBinding.tvOK.setOnClickListener(v -> onPositiveButtonClicked());
                break;
            case OK_CANCEL:
                dialogAlertBinding.tvOK.setVisibility(View.VISIBLE);
                dialogAlertBinding.tvCancel.setVisibility(View.VISIBLE);
                dialogAlertBinding.tvOK.setOnClickListener(v -> onPositiveButtonClicked());
                dialogAlertBinding.tvCancel.setOnClickListener(v -> onNegativeButtonClicked());
                break;
        }

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 10);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        dialogAlertBinding.tvTitle.setText(mTitle);
        dialogAlertBinding.tvMessage.setText(mMessage);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showYesNoDialog(String mTitle) {
        final Dialog dialog = this;

        DialogAlertBinding dialogAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_alert, null, false);
        dialog.setContentView(dialogAlertBinding.getRoot());
        dialogAlertBinding.tvOK.setText(getContext().getResources().getString(R.string.yes));
        dialogAlertBinding.tvCancel.setText(getContext().getResources().getString(R.string.no));
        dialogAlertBinding.tvOK.setVisibility(View.VISIBLE);
        dialogAlertBinding.tvCancel.setVisibility(View.VISIBLE);
        dialogAlertBinding.tvOK.setOnClickListener(v -> onPositiveButtonClicked());
        dialogAlertBinding.tvCancel.setOnClickListener(v -> onNegativeButtonClicked());

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 10);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        dialogAlertBinding.tvTitle.setText(mTitle);
        dialogAlertBinding.tvMessage.setVisibility(View.GONE);
        dialog.setCancelable(false);
        dialog.show();
    }


    public void showLogoutDialog() {

        final Dialog dialog = this;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogLogoutBinding dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.dialog_logout, null, false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(dialogLogoutBinding.getRoot());

//        Dialog dialog = new Dialog(mContext, R.style.DialogThemeforview);

        dialogLogoutBinding.btnLogoutYes.setOnClickListener(v -> {
            onPositiveButtonClicked();
        });
        dialogLogoutBinding.btnLogoutNo.setOnClickListener(view -> {
            dialog.dismiss();
            onNegativeButtonClicked();
        });
        dialog.show();
    }

}