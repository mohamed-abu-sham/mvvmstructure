package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.databinding.CellProductImageBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.ImagesItem;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ProductImageViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductImagesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final List<ImagesItem> imagesItemList;

    Context mContext;
    RecyclerClick mRecyclerClick;
    int selectedItemPosition = 0;

    public ProductImagesAdapter(Context mContext, RecyclerClick mRecyclerClick) {
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
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CellProductImageBinding cellBinding = CellProductImageBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductImageCellViewHolder(cellBinding);
    }

    public void addItems(List<ImagesItem> repoList) {
        imagesItemList.addAll(repoList);
    }

    public void addItem(ImagesItem imagesItem) {
        imagesItemList.add(imagesItem);
        notifyDataSetChanged();
    }

    public void clearItems() {
        imagesItemList.clear();
    }

    public void remove(int position) {
        imagesItemList.remove(position);
    }

    public class ProductImageCellViewHolder extends BaseViewHolder {

        private final CellProductImageBinding mBinding;

        public ProductImageCellViewHolder(CellProductImageBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ProductImageViewModel(mContext, imagesItemList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setImagesItem(imagesItemList.get(position));
            }
            mBinding.cardCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedItemPosition != position) {
                        imagesItemList.get(selectedItemPosition).setSelected(false);
                        imagesItemList.get(position).setSelected(true);
                        selectedItemPosition = position;
                        notifyDataSetChanged();
                        mRecyclerClick.onClick(imagesItemList.get(position), position);
                    }
                }
            });
            if (imagesItemList.get(position).isSelected()) {
                mBinding.imgPicture.setAlpha((float) 0.5);
            } else {
                mBinding.imgPicture.setAlpha((float) 1);
            }
        }

    }


}