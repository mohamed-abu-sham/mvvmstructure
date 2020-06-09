package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.databinding.CellAddImageBinding;
import com.selwantech.raheeb.databinding.CellImageBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemAddImageViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddImagesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int ADD_ITEM = 0;
    private final List<String> imagesItemList;

    Context mContext;
    RecyclerClick mRecyclerClick;

    public AddImagesAdapter(Context mContext, RecyclerClick mRecyclerClick) {
        this.imagesItemList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }


    @Override
    public int getItemCount() {
        if (!imagesItemList.isEmpty()) {
            return imagesItemList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return imagesItemList.get(position) != null ? VIEW_ITEM : ADD_ITEM;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_ITEM) {
            CellAddImageBinding cellBinding = CellAddImageBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CellImageViewHolder(cellBinding);
        } else {
            CellImageBinding cellBinding = CellImageBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CellAddImageViewHolder(cellBinding);
        }


    }

    public void addItems(List<String> repoList) {
        imagesItemList.addAll(repoList);
    }

    public void addItem(String imagesItem) {
        imagesItemList.add(imagesItem);
        notifyDataSetChanged();
    }

    public void clearItems() {
        imagesItemList.clear();
    }

    public void remove(int position) {
        imagesItemList.remove(position);
    }

    public List getArrayList() {
        return imagesItemList;
    }

    public String getItem(int position) {
        return imagesItemList.get(position);
    }

    public class CellImageViewHolder extends BaseViewHolder {

        private final CellAddImageBinding mBinding;

        public CellImageViewHolder(CellAddImageBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemAddImageViewModel(mContext, imagesItemList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setImagesItem(imagesItemList.get(position));
            }
        }

    }

    public class CellAddImageViewHolder extends BaseViewHolder {

        private final CellImageBinding mBinding;

        public CellAddImageViewHolder(CellImageBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemAddImageViewModel(mContext, imagesItemList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setImagesItem(imagesItemList.get(position));
            }
        }

    }

}