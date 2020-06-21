package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.selwantech.raheeb.databinding.CellSettingItemBinding;
import com.selwantech.raheeb.interfaces.RecyclerClickNoData;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemSettingViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SettingsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    ArrayList<String> arrayListText;
    Context mContext;
    RecyclerClickNoData mRecyclerClick;

    public SettingsAdapter(Context mContext, RecyclerClickNoData mRecyclerClick, List<String> text) {
        this.arrayListText = new ArrayList<>(text);
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }

    @Override
    public int getItemCount() {
        if (!arrayListText.isEmpty()) {
            return arrayListText.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CellSettingItemBinding cellSettingBinding = CellSettingItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SettingsItemViewHolder(cellSettingBinding);
    }

    public void addItems(List<String> text) {
        arrayListText.addAll(text);
    }


    public class SettingsItemViewHolder extends BaseViewHolder {

        private final CellSettingItemBinding mBinding;

        public SettingsItemViewHolder(CellSettingItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemSettingViewModel(mContext, mBinding, arrayListText.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setText(arrayListText.get(position));
            }
        }

    }

}