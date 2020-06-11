package com.selwantech.raheeb.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ButtomSheetConfirmSoldOutBinding;

public class ConfirmSoldFragmentDialog extends BottomSheetDialogFragment {

    ConfirmSoldCallBack confirmSoldCallBack;
    ButtomSheetConfirmSoldOutBinding buttomSheetConfirmSoldOutBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buttomSheetConfirmSoldOutBinding = DataBindingUtil.inflate(inflater, R.layout.buttom_sheet_confirm_sold_out, null, false);
        buttomSheetConfirmSoldOutBinding.setViewModel(this);
        setCancelable(false);
        return buttomSheetConfirmSoldOutBinding.getRoot();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public BottomSheetDialogFragment setMethodCallBack(ConfirmSoldCallBack filterByCallBack) {
        this.confirmSoldCallBack = filterByCallBack;
        return this;
    }

    public void onApplyClicked() {
        dismiss();
        confirmSoldCallBack.confirmed();
    }

    public void onCancleClicked() {
        dismiss();
    }

    public interface ConfirmSoldCallBack {
        void confirmed();
    }

    public static class Builder {

        public ConfirmSoldFragmentDialog build() {
            Bundle args = new Bundle();
            ConfirmSoldFragmentDialog fragment = new ConfirmSoldFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
