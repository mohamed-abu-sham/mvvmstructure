package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.databinding.CellSoldToBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.SoldTo;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemSoldToViewModel;

import java.util.ArrayList;
import java.util.List;

public class SoldToAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final List<SoldTo> soldToList;

    Context mContext;
    RecyclerClick mRecyclerClick;
    int selectedItemPosition = -1;

    public SoldToAdapter(Context mContext, RecyclerClick mRecyclerClick) {
        this.soldToList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }


    @Override
    public int getItemCount() {
        if (!soldToList.isEmpty()) {
            return soldToList.size();
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
        CellSoldToBinding cellBinding = CellSoldToBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShippingBoxCellViewHolder(cellBinding);
    }

    public void addItems(List<SoldTo> repoList) {
        soldToList.addAll(repoList);
    }

    public void addItem(SoldTo soldTo) {
        soldToList.add(soldTo);
        notifyDataSetChanged();
    }

    public void clearItems() {
        soldToList.clear();
    }

    public void remove(int position) {
        soldToList.remove(position);
    }

    public SoldTo getSelectedItem() {
        return soldToList.get(selectedItemPosition);
    }

    public void setSoldElse() {
        if (selectedItemPosition != -1) {
            soldToList.get(selectedItemPosition).setSelected(false);
            selectedItemPosition = -1;
        }
    }

    public void setSelectedItemPosition(int position) {
        this.selectedItemPosition = position;
    }

    public class ShippingBoxCellViewHolder extends BaseViewHolder {

        private final CellSoldToBinding mBinding;

        public ShippingBoxCellViewHolder(CellSoldToBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemSoldToViewModel(mContext, soldToList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setSoldTo(soldToList.get(position));
            }
            mBinding.cardCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedItemPosition != position) {
                        if (selectedItemPosition != -1) {
                            soldToList.get(selectedItemPosition).setSelected(false);
                        }
                        soldToList.get(position).setSelected(true);
                        selectedItemPosition = position;
                        notifyDataSetChanged();
                        mRecyclerClick.onClick(soldToList.get(position), position);
                    }
                }
            });
        }

    }


}