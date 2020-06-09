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
import com.selwantech.raheeb.databinding.ButtomSheetFilterByBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.FilterBy;
import com.selwantech.raheeb.model.FilterProduct;
import com.selwantech.raheeb.ui.adapter.FilterByAdapter;
import com.selwantech.raheeb.utils.AppConstants;

public class FilterByFragmentDialog extends BottomSheetDialogFragment implements RecyclerClick<FilterBy> {

    FilterByCallBack filterByCallBack;
    ButtomSheetFilterByBinding buttomSheetFilterByBinding;

    FilterByAdapter filterByAdapter;

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
        buttomSheetFilterByBinding = DataBindingUtil.inflate(inflater, R.layout.buttom_sheet_filter_by, null, false);
        buttomSheetFilterByBinding.setViewModel(this);
        setCancelable(false);
        setUp();
        return buttomSheetFilterByBinding.getRoot();
    }

    private void setUp() {
        setUpRecycler();
    }

    private void setUpRecycler() {
        buttomSheetFilterByBinding.recyclerView.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        buttomSheetFilterByBinding.recyclerView.recyclerView.setItemAnimator(new DefaultItemAnimator());
        filterByAdapter = new FilterByAdapter(getContext(), this);
        buttomSheetFilterByBinding.recyclerView.recyclerView.setAdapter(filterByAdapter);
        getData();
    }

    private void getData() {
        filterByAdapter.addItems(FilterProduct.getInstance().getArraylist());
        if (filterByAdapter.getItemCount() == 0) {
            buttomSheetFilterByBinding.tvNotFilter.setVisibility(View.VISIBLE);
        } else {
            buttomSheetFilterByBinding.btnApply.setVisibility(View.VISIBLE);
        }
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

    public BottomSheetDialogFragment setMethodCallBack(FilterByCallBack filterByCallBack) {
        this.filterByCallBack = filterByCallBack;
        return this;
    }

    private void notifyAdapter() {
        buttomSheetFilterByBinding.recyclerView.recyclerView.post(new Runnable() {
            @Override
            public void run() {
                filterByAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(FilterBy filterBy, int position) {
        filterByAdapter.getItem(position).setAvailable(false);
        notifyAdapter();
        if (filterByAdapter.getAvailableItemCount() == 0) {
            buttomSheetFilterByBinding.tvNotFilter.setText(getContext().getResources().getString(R.string.apply_to_clear_filters));
            buttomSheetFilterByBinding.tvNotFilter.setVisibility(View.VISIBLE);
            buttomSheetFilterByBinding.recyclerView.recyclerView.setVisibility(View.GONE);
        }
    }

    public void onApplyClicked() {
        updateFilterProduct();
        dismiss();
        filterByCallBack.callBack();
    }

    private void updateFilterProduct() {
        if (filterByAdapter.getAvailableItemCount() == 0) {
            FilterProduct.getInstance().clearData();
            return;
        }
        for (int i = 0; i < filterByAdapter.getItems().size(); i++) {
            applyFilter(filterByAdapter.getItem(i));
        }
    }

    private void applyFilter(FilterBy filterBy) {
        if (!filterBy.isAvailable()) {
            if (filterBy.getKey().equals(AppConstants.FILTER_BY_KEYS.LOCATION)) {
                FilterProduct.getInstance().setLat(0.0);
                FilterProduct.getInstance().setLon(0.0);
            }
            if (filterBy.getKey().equals(AppConstants.FILTER_BY_KEYS.PRICE)) {
                FilterProduct.getInstance().setPrice_min(0.0);
                FilterProduct.getInstance().setPrice_max(0.0);
            }
            if (filterBy.getKey().equals(AppConstants.FILTER_BY_KEYS.DISTANCE)) {
                FilterProduct.getInstance().setDistance(0);
            }
            if (filterBy.getKey().equals(AppConstants.FILTER_BY_KEYS.CATEGORY)) {
                FilterProduct.getInstance().setCategory(null);
            }
            if (filterBy.getKey().equals(AppConstants.FILTER_BY_KEYS.ORDER_BY)) {
                FilterProduct.getInstance().setOrdering("");
            }
        }
    }

    public void onCancleClicked() {
        dismiss();
    }

    public interface FilterByCallBack {
        void callBack();
    }

    public static class Builder {

        public FilterByFragmentDialog build() {
            Bundle args = new Bundle();
            FilterByFragmentDialog fragment = new FilterByFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
