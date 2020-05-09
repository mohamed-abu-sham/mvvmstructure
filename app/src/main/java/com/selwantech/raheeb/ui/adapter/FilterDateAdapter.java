package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.CellFilterDateBinding;
import com.selwantech.raheeb.databinding.CellSettingItemBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.interfaces.RecyclerClickNoData;
import com.selwantech.raheeb.model.FilterDate;
import com.selwantech.raheeb.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FilterDateAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    ArrayList<FilterDate> filterDateArrayList;
    Context mContext;
    RecyclerClick mRecyclerClick;

    int selectedItem = -1 ;
    public FilterDateAdapter(Context mContext, RecyclerClick mRecyclerClick) {
        this.filterDateArrayList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;
    }

    @Override
    public int getItemCount() {
        if (!filterDateArrayList.isEmpty()) {
            return filterDateArrayList.size();
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
        CellFilterDateBinding cellFilterDateBinding = CellFilterDateBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FilterDateItemViewHolder(cellFilterDateBinding);
    }

    public void addItems(List<FilterDate> filterDateList) {
        filterDateArrayList.addAll(filterDateList);
    }

    public void addItem(FilterDate filterDate){
        filterDateArrayList.add(filterDate);
        notifyDataSetChanged();
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public class FilterDateItemViewHolder extends BaseViewHolder {

        private final CellFilterDateBinding mBinding;

        public FilterDateItemViewHolder(CellFilterDateBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            mBinding.textView.setText(filterDateArrayList.get(position).getName());
            if(filterDateArrayList.get(position).isSelected()){
                selectedItem = position ;
                GeneralFunction.tintImageView(mContext,mBinding.imgArrow,R.color.green);
            }else{
                GeneralFunction.tintImageView(mContext,mBinding.imgArrow,R.color.black);
            }
            mBinding.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterDateArrayList.get(selectedItem).setSelected(false);
                    filterDateArrayList.get(position).setSelected(true);
                    selectedItem = position ;
                    mRecyclerClick.onClick(filterDateArrayList.get(position),position);
                }
            });
        }
    }

}