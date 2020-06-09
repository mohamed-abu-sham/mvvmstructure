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
import com.selwantech.raheeb.databinding.ButtomSheetFilterPriceRangeBinding;
import com.selwantech.raheeb.model.FilterPrice;

public class FilterPriceFragmentDialog extends BottomSheetDialogFragment {

    FilterPriceCallBack filterPriceCallBack;
    ButtomSheetFilterPriceRangeBinding buttomSheetFilterPriceRangeBinding;


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
        buttomSheetFilterPriceRangeBinding = DataBindingUtil.inflate(inflater, R.layout.buttom_sheet_filter_price_range, null, false);
        buttomSheetFilterPriceRangeBinding.setViewModel(this);
        setCancelable(false);
        return buttomSheetFilterPriceRangeBinding.getRoot();
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

    public BottomSheetDialogFragment setMethodCallBack(FilterPriceCallBack pickDateCallBack) {
        this.filterPriceCallBack = pickDateCallBack;
        return this;
    }


    public interface FilterPriceCallBack {
        void callBack(FilterPrice dateAndTime);
    }

    public void onApplyClicked(){
        if(isValid()){
            dismiss();
            filterPriceCallBack.callBack(
                    new FilterPrice(Double.valueOf(buttomSheetFilterPriceRangeBinding.edMinPrice.getText().toString()),
                            Double.valueOf(buttomSheetFilterPriceRangeBinding.edMaxPrice.getText().toString())));
        }
    }

    public void onCancleClicked(){
        dismiss();
    }

    private boolean isValid() {
        int error = 0 ;
        if(buttomSheetFilterPriceRangeBinding.edMinPrice.getText().toString().isEmpty()){
            buttomSheetFilterPriceRangeBinding.edMinPrice.setError(getContext().getResources().getString(R.string.this_fieled_is_required));
            error = +1 ;
        }

        if(buttomSheetFilterPriceRangeBinding.edMaxPrice.getText().toString().isEmpty()){
            buttomSheetFilterPriceRangeBinding.edMaxPrice.setError(getContext().getResources().getString(R.string.this_fieled_is_required));
            error = +1 ;
        }
        if (Double.valueOf(buttomSheetFilterPriceRangeBinding.edMaxPrice.getText().toString()) <=
                Double.valueOf(buttomSheetFilterPriceRangeBinding.edMinPrice.getText().toString())) {
            buttomSheetFilterPriceRangeBinding.edMaxPrice.setError(getContext().getResources().getString(R.string.the_max_must_be_grater_than_min));
            error = +1;
        }

        return error == 0 ;
    }

    public static class Builder {

        public FilterPriceFragmentDialog build() {
            Bundle args = new Bundle();
            FilterPriceFragmentDialog fragment = new FilterPriceFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
