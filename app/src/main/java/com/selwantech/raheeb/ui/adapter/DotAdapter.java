package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.CellImageCircleBinding;
import com.selwantech.raheeb.ui.base.BaseViewHolder;

import java.util.ArrayList;

public class DotAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    Context mContext;
    private ArrayList<Boolean> booleanArrayList;

    public DotAdapter(Context mContext, ArrayList<Boolean> booleanArrayList) {
        this.mContext = mContext;
        this.booleanArrayList = booleanArrayList;
    }

    @Override
    public int getItemCount() {
        if (!booleanArrayList.isEmpty()) {
            return booleanArrayList.size();
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
        CellImageCircleBinding cellImageCircleBinding = CellImageCircleBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeCellViewHolder(cellImageCircleBinding);
    }

    public class HomeCellViewHolder extends BaseViewHolder {

        private final CellImageCircleBinding mBinding;

        public HomeCellViewHolder(CellImageCircleBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (booleanArrayList.get(position)) {
                mBinding.imgDotImage.setImageResource(R.color.colorPrimaryDark);
            } else {
                mBinding.imgDotImage.setImageResource(R.color.tabButtonColorLight);
            }
        }

    }


}