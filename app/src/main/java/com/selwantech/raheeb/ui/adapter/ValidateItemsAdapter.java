package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.selwantech.raheeb.databinding.CellValidateItemBinding;
import com.selwantech.raheeb.interfaces.RecyclerClickNoData;
import com.selwantech.raheeb.model.ValidateItem;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemValidateViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ValidateItemsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    ArrayList<ValidateItem> validateItems;
    Context mContext;
    RecyclerClickNoData recyclerClickNoData;

    public ValidateItemsAdapter(Context mContext, RecyclerClickNoData recyclerClickNoData) {
        this.validateItems = new ArrayList<>();
        this.mContext = mContext;
        this.recyclerClickNoData = recyclerClickNoData;
    }

    @Override
    public int getItemCount() {
        if (!validateItems.isEmpty()) {
            return validateItems.size();
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
        CellValidateItemBinding cellBinding = CellValidateItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ValidateItemViewHolder(cellBinding);
    }

    public void addItems(List<ValidateItem> text) {
        validateItems.addAll(text);
    }


    public class ValidateItemViewHolder extends BaseViewHolder {

        private final CellValidateItemBinding mBinding;

        public ValidateItemViewHolder(CellValidateItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemValidateViewModel(mContext, validateItems.get(position), position, recyclerClickNoData));
            } else {
                mBinding.getViewModel().setValidateItem(validateItems.get(position));
            }
        }

    }

}