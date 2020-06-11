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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ButtomSheetOrderByBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.FilterDate;
import com.selwantech.raheeb.ui.adapter.OrderByAdapter;

public class OrderByFragmentDialog extends BottomSheetDialogFragment implements RecyclerClick<FilterDate> {

    OrderByCallBack orderByCallBack;
    ButtomSheetOrderByBinding buttomSheetOrderByBinding;

    OrderByAdapter orderByAdapter;

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
        buttomSheetOrderByBinding = DataBindingUtil.inflate(inflater, R.layout.buttom_sheet_order_by, null, false);
        buttomSheetOrderByBinding.setViewModel(this);
        setCancelable(false);
        setUp();
        return buttomSheetOrderByBinding.getRoot();
    }

    private void setUp() {
        setUpRecycler();
    }

    private void setUpRecycler() {
        buttomSheetOrderByBinding.recyclerView.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        buttomSheetOrderByBinding.recyclerView.recyclerView.setItemAnimator(new DefaultItemAnimator());
        orderByAdapter = new OrderByAdapter(getContext(), this);
        buttomSheetOrderByBinding.recyclerView.recyclerView.setAdapter(orderByAdapter);
        getData();
    }

    private void getData() {
        orderByAdapter.addItem(new FilterDate(getContext().getResources().getString(R.string.newest), true));
        orderByAdapter.addItem(new FilterDate(getContext().getResources().getString(R.string.closest), false));
        orderByAdapter.addItem(new FilterDate(getContext().getResources().getString(R.string.low_to_high), false));
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

    public BottomSheetDialogFragment setMethodCallBack(OrderByCallBack pickDateCallBack) {
        this.orderByCallBack = pickDateCallBack;
        return this;
    }

    @Override
    public void onClick(FilterDate filterDate, int position) {
        notifyAdapter();
    }


    private void notifyAdapter() {
        buttomSheetOrderByBinding.recyclerView.recyclerView.post(new Runnable() {
            @Override
            public void run() {
                orderByAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onApplyClicked(){
        dismiss();
        if (orderByAdapter.getSelectedItem() > -1) {
            orderByCallBack.callBack(orderByAdapter.getSelectedItem());
        }
    }

    public interface OrderByCallBack {
        void callBack(int filterId);
    }

    public void onCancleClicked(){
        dismiss();
    }

    public static class Builder {

        public OrderByFragmentDialog build() {
            Bundle args = new Bundle();
            OrderByFragmentDialog fragment = new OrderByFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
