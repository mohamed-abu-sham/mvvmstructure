package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.CellSettingItemBinding;
import com.selwantech.raheeb.interfaces.RecyclerClickNoData;
import com.selwantech.raheeb.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    ArrayList<Integer> arrayListText;
    ArrayList<Integer> arrayListImages;
    Context mContext;
    RecyclerClickNoData mRecyclerClick;

    public SettingsAdapter(Context mContext, RecyclerClickNoData mRecyclerClick, List<Integer> images, List<Integer> text) {
        this.arrayListImages = new ArrayList<>(images);
        this.arrayListText = new ArrayList<>(text);
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }

    @Override
    public int getItemCount() {
        if (!arrayListImages.isEmpty()) {
            return arrayListImages.size();
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

    public void addItems(List<Integer> images, List<Integer> text) {
        arrayListImages.addAll(images);
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
            mBinding.textView.setText(arrayListText.get(position));
            mBinding.imageView.setImageResource(arrayListImages.get(position));
            mBinding.itemView.setOnClickListener(v -> mRecyclerClick.onClick(position));
            if (position == arrayListImages.size() - 1) {
                mBinding.imgArrow.setVisibility(View.GONE);
                mBinding.textView.setTextColor(mContext.getResources().getColor(R.color.red));
            }
        }

    }

}