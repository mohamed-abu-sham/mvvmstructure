package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.selwantech.raheeb.databinding.CellLoadMoreBinding;
import com.selwantech.raheeb.databinding.CellObjectBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemObjectViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LoadMoreAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final List<Object> objectList;
    Context mContext;
    RecyclerClick mRecyclerClick;
    private boolean loading;
    private OnLoadMoreListener loadMoreListener;

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }



    public LoadMoreAdapter(Context mContext, RecyclerClick mRecyclerClick, RecyclerView recyclerView) {
        this.objectList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.loadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        if (!objectList.isEmpty()) {
            return objectList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return objectList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            CellObjectBinding cellBinding = CellObjectBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CellViewHolder(cellBinding);
        } else {
            CellLoadMoreBinding cellLoadMoreBinding = CellLoadMoreBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProgressCellViewHolder(cellLoadMoreBinding);
        }
    }

    public void addItems(List<Object> repoList) {
        objectList.addAll(repoList);
    }

    public void addItem(Object object) {
        objectList.add(object);
    }

    public void clearItems() {
        objectList.clear();
    }

    public void remove(int position) {
        objectList.remove(position);
    }

    public List<Object> getArrayList() {
        return objectList;
    }

    public Object getItem(int position) {
        return objectList.get(position);
    }
    public class CellViewHolder extends BaseViewHolder {

        private final CellObjectBinding mBinding;

        public CellViewHolder(CellObjectBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemObjectViewModel(mContext, objectList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setObject(objectList.get(position));
            }
            mBinding.cardOfferCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerClick.onClick(objectList.get(position), position);
                }
            });
            if (position >= getItemCount() - 3) {
                if (!loading) {
                    if (loadMoreListener != null) {
                        loadMoreListener.onLoadMore();
                    }
                    loading = true;
                }
            }
        }
    }

    public class ProgressCellViewHolder extends BaseViewHolder {

        private final CellLoadMoreBinding mBinding;

        public ProgressCellViewHolder(CellLoadMoreBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mBinding.progressBar.setIndeterminate(true);
        }
    }

}