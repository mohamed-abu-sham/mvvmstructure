package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.CellShippingBoxBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.BoxSize;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemShippingBoxViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShippingBoxAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final List<BoxSize> boxSizeList;

    Context mContext;
    RecyclerClick mRecyclerClick;
    int selectedItemPosition = 0;

    public ShippingBoxAdapter(Context mContext, RecyclerClick mRecyclerClick) {
        this.boxSizeList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }


    @Override
    public int getItemCount() {
        if (!boxSizeList.isEmpty()) {
            return boxSizeList.size();
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
        CellShippingBoxBinding cellBinding = CellShippingBoxBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShippingBoxCellViewHolder(cellBinding);
    }

    public void addItems(List<BoxSize> repoList) {
        boxSizeList.addAll(repoList);
    }

    public void addItem(BoxSize boxSize) {
        boxSizeList.add(boxSize);
        notifyDataSetChanged();
    }

    public void clearItems() {
        boxSizeList.clear();
    }

    public void remove(int position) {
        boxSizeList.remove(position);
    }

    public BoxSize getSelectedItem() {
        return boxSizeList.get(selectedItemPosition);
    }

    public class ShippingBoxCellViewHolder extends BaseViewHolder {

        private final CellShippingBoxBinding mBinding;

        public ShippingBoxCellViewHolder(CellShippingBoxBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemShippingBoxViewModel(mContext, boxSizeList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setBoxSize(boxSizeList.get(position));
            }
            mBinding.relativeMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedItemPosition != position) {
                        boxSizeList.get(selectedItemPosition).setSelected(false);
                        boxSizeList.get(position).setSelected(true);
                        selectedItemPosition = position;
                        notifyDataSetChanged();
                        mRecyclerClick.onClick(boxSizeList.get(position), position);
                    }
                }
            });
            if (boxSizeList.get(position).isSelected()) {
                GeneralFunction.tintImageView(mContext, mBinding.imgPicture, R.color.color_blue);
                mBinding.linearMain.setBackground(mContext.getResources().getDrawable(R.drawable.shape_rounded_empty_blue));
                mBinding.tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_blue));
            } else {
                GeneralFunction.tintImageView(mContext, mBinding.imgPicture, R.color.text_gray);
                mBinding.linearMain.setBackground(mContext.getResources().getDrawable(R.drawable.shape_rounded_box_gray));
                mBinding.tvPrice.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            }
        }

    }


}