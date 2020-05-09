package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.databinding.CellCategoryBinding;
import com.selwantech.raheeb.databinding.CellHomeBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Category;
import com.selwantech.raheeb.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final List<Category> mCategoryList;

    Context mContext;
    RecyclerClick mRecyclerClick;

    public CategoryAdapter(Context mContext, RecyclerClick mRecyclerClick) {
        this.mCategoryList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }


    @Override
    public int getItemCount() {
        if (!mCategoryList.isEmpty()) {
            return mCategoryList.size();
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
        CellCategoryBinding cellHomeBinding = CellCategoryBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryCellViewHolder(cellHomeBinding);
    }

    public void addItems(List<Category> repoList) {
        mCategoryList.addAll(repoList);
    }

    public void addItem(Category category) {
        mCategoryList.add(category);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mCategoryList.clear();
    }

    public void remove(int position) {
        mCategoryList.remove(position);
    }

    public class CategoryCellViewHolder extends BaseViewHolder {

        private final CellCategoryBinding mBinding;

        public CategoryCellViewHolder(CellCategoryBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
//            this.mBinding.tvName.setText(mCategoryList.get(position).getName());
//            GeneralFunction.loadImage(mContext, mCategoryList.get(position).getImage(), this.mBinding.imgPicture);
//            this.mBinding.cardFavoriteCell.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mRecyclerClick.onClick(mCategoryList.get(position), position);
//                }
//            });
        }

    }


}