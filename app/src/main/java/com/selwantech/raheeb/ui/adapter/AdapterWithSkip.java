package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.databinding.CellBinding;
import com.selwantech.raheeb.databinding.CellLoadMoreBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.DataExample;
import com.selwantech.raheeb.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AdapterWithSkip extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final List<DataExample> mDataExampleList;
    Context mContext;
    RecyclerClick mRecyclerClick;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener loadMoreListener;

    public AdapterWithSkip(Context mContext, RecyclerClick mRecyclerClick, RecyclerView recyclerView) {
        this.mDataExampleList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if (!loading && totalItemCount - 1 == (lastVisibleItem)) {
                        if (loadMoreListener != null) {
                            loadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.loadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        if (!mDataExampleList.isEmpty()) {
            return mDataExampleList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataExampleList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            CellBinding cellHomeBinding = CellBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new HomeCellViewHolder(cellHomeBinding);
        } else {
            CellLoadMoreBinding cellLoadMoreBinding = CellLoadMoreBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProgressCellViewHolder(cellLoadMoreBinding);
        }
    }

    public void addItems(List<DataExample> repoList) {
        mDataExampleList.addAll(repoList);
    }

    public void addItem(DataExample dataExample) {
        mDataExampleList.add(dataExample);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mDataExampleList.clear();
    }

    public void remove(int position) {
        mDataExampleList.remove(position);
    }

    public class HomeCellViewHolder extends BaseViewHolder {

        private final CellBinding mBinding;

        public HomeCellViewHolder(CellBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            this.mBinding.tvName.setText(mDataExampleList.get(position).getTitle());
            GeneralFunction.loadImage(mContext, mDataExampleList.get(position).getImage(), this.mBinding.imgPicture);
            this.mBinding.ratingBarRate.setRating(mDataExampleList.get(position).getRate());
            this.mBinding.tvRate.setText(mDataExampleList.get(position).getRate() + "");
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