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
import com.selwantech.raheeb.databinding.ButtomSheetOfferBinding;

public class OfferFragmentDialog extends BottomSheetDialogFragment {

    OfferCallBack offerCallBack;
    ButtomSheetOfferBinding buttomSheetOfferBinding;


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
        buttomSheetOfferBinding = DataBindingUtil.inflate(inflater, R.layout.buttom_sheet_offer, null, false);
        buttomSheetOfferBinding.setViewModel(this);
        setCancelable(false);
        return buttomSheetOfferBinding.getRoot();
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

    public BottomSheetDialogFragment setMethodCallBack(OfferCallBack pickDateCallBack) {
        this.offerCallBack = pickDateCallBack;
        return this;
    }

    public void onApplyClicked() {
        if (isValid()) {
            dismiss();
            offerCallBack.callBack(Double.valueOf(buttomSheetOfferBinding.edAmount.getText().toString()));
        }
    }

    public void onCancleClicked() {
        dismiss();
    }

    private boolean isValid() {
        int error = 0;
        if (buttomSheetOfferBinding.edAmount.getText().toString().isEmpty()) {
            buttomSheetOfferBinding.edAmount.setError(getContext().getResources().getString(R.string.this_fieled_is_required));
            error = +1;
        }

        if (buttomSheetOfferBinding.edAmount.getText().toString().equals("0")) {
            buttomSheetOfferBinding.edAmount.setError(getContext().getResources().getString(R.string.amount_must_be_more_than_0));
            error = +1;
        }

        return error == 0;
    }

    public interface OfferCallBack {
        void callBack(double amount);
    }

    public static class Builder {

        public OfferFragmentDialog build() {
            Bundle args = new Bundle();
            OfferFragmentDialog fragment = new OfferFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
