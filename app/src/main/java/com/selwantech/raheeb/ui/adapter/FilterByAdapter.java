package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.databinding.CellFilterByBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.FilterBy;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemFilterByViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilterByAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    ArrayList<FilterBy> filterByArrayList;
    Context mContext;
    RecyclerClick mRecyclerClick;

    public FilterByAdapter(Context mContext, RecyclerClick mRecyclerClick) {
        this.filterByArrayList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }

    @Override
    public int getItemCount() {
        if (!filterByArrayList.isEmpty()) {
            return filterByArrayList.size();
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
        CellFilterByBinding cellFilterDateBinding = CellFilterByBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FilterByItemViewHolder(cellFilterDateBinding);
    }

    public void addItems(List<FilterBy> filterByList) {
        filterByArrayList.addAll(filterByList);
    }

    public void addItem(FilterBy filterBy) {
        filterByArrayList.add(filterBy);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        filterByArrayList.remove(position);
    }

    public ArrayList<FilterBy> getItems() {
        return filterByArrayList;
    }

    public FilterBy getItem(int position) {
        return filterByArrayList.get(position);
    }

    public int getAvailableItemCount() {
        int count = 0;
        for (FilterBy filterBy : filterByArrayList) {
            if (filterBy.isAvailable())
                count++;
        }
        return count;
    }

    public class FilterByItemViewHolder extends BaseViewHolder {

        private final CellFilterByBinding mBinding;

        public FilterByItemViewHolder(CellFilterByBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (!filterByArrayList.get(position).isAvailable()) {
                mBinding.itemView.setVisibility(View.GONE);
                mBinding.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            } else {
                mBinding.itemView.setVisibility(View.VISIBLE);
                mBinding.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemFilterByViewModel(mContext, filterByArrayList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setFilterBy(filterByArrayList.get(position));
            }
        }
    }

}