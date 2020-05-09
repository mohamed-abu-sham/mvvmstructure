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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ButtomSheetFilterDateBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.FilterDate;
import com.selwantech.raheeb.model.FilterPrice;
import com.selwantech.raheeb.ui.adapter.FilterDateAdapter;
import com.selwantech.raheeb.ui.adapter.HomeAdapter;
import com.selwantech.raheeb.utils.ItemTouchCallBack;
import com.selwantech.raheeb.utils.SpacesItemDecoration;

public class FilterDateFragmentDialog extends BottomSheetDialogFragment implements RecyclerClick<FilterDate> {

    FilterDateCallBack filterDateCallBack;
    ButtomSheetFilterDateBinding buttomSheetFilterDateBinding;

    FilterDateAdapter filterDateAdapter ;

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
        buttomSheetFilterDateBinding = DataBindingUtil.inflate(inflater, R.layout.buttom_sheet_filter_date, null, false);
        buttomSheetFilterDateBinding.setViewModel(this);
        setCancelable(false);
        setUp();
        return buttomSheetFilterDateBinding.getRoot();
    }

    private void setUp() {
        setUpRecycler();
    }

    private void setUpRecycler() {
        buttomSheetFilterDateBinding.recyclerView.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        buttomSheetFilterDateBinding.recyclerView.recyclerView.setItemAnimator(new DefaultItemAnimator());
        filterDateAdapter = new FilterDateAdapter(getContext(), this);
        buttomSheetFilterDateBinding.recyclerView.recyclerView.setAdapter(filterDateAdapter);
        getData();
    }

    private void getData() {
        filterDateAdapter.addItem(new FilterDate("saad",true));
        filterDateAdapter.addItem(new FilterDate("saad",false));
        filterDateAdapter.addItem(new FilterDate("saad",false));
        filterDateAdapter.addItem(new FilterDate("saad",false));
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

    public BottomSheetDialogFragment setMethodCallBack(FilterDateCallBack pickDateCallBack) {
        this.filterDateCallBack = pickDateCallBack;
        return this;
    }

    @Override
    public void onClick(FilterDate filterDate, int position) {
        notifyAdapter();
    }


    private void notifyAdapter() {
        buttomSheetFilterDateBinding.recyclerView.recyclerView.post(new Runnable() {
            @Override
            public void run() {
                filterDateAdapter.notifyDataSetChanged();
            }
        });
    }

    public interface FilterDateCallBack {
        void callBack(int filterId);
    }

    public void onApplyClicked(){
        dismiss();
        if(filterDateAdapter.getSelectedItem() > -1) {
            filterDateCallBack.callBack(filterDateAdapter.getSelectedItem());
        }
    }

    public void onCancleClicked(){
        dismiss();
    }


    public static class Builder {

        public FilterDateFragmentDialog build() {
            Bundle args = new Bundle();
            FilterDateFragmentDialog fragment = new FilterDateFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
