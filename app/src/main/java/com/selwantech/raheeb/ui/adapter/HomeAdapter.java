package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.selwantech.raheeb.databinding.CellLoadMoreGridBinding;
import com.selwantech.raheeb.databinding.CellProductBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final List<Product> mProductList;
    Context mContext;
    RecyclerClick mRecyclerClick;
    private int lastVisibleItem, totalItemCount, visibleItem;
    private boolean loading;
    private OnLoadMoreListener loadMoreListener;

    public HomeAdapter(Context mContext, RecyclerClick mRecyclerClick, RecyclerView recyclerView) {
        this.mProductList = new ArrayList<>();
        this.mContext = mContext;
        this.mRecyclerClick = mRecyclerClick;

        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {

            final StaggeredGridLayoutManager linearLayoutManager = (StaggeredGridLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
//                    ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).invalidateSpanAssignments();
                    if (dy > 0) {
                        visibleItem = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
//                        lastVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPositions(null)[0];

//                        int[] lastPositions = new int[linearLayoutManager.getSpanCount()];
                        int[] lastPositions = linearLayoutManager.findLastCompletelyVisibleItemPositions(null);
                        lastVisibleItem = getLastVisibleItem(lastPositions);
                        if (!loading && lastVisibleItem != RecyclerView.NO_POSITION && (lastVisibleItem) == (totalItemCount - 1)) {
                            if (loadMoreListener != null) {
                                loadMoreListener.onLoadMore();
                            }
                            loading = true;
                        }
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

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.loadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        if (!mProductList.isEmpty()) {
            return mProductList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mProductList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            CellProductBinding cellBinding = CellProductBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new HomeCellViewHolder(cellBinding);
        } else {
            CellLoadMoreGridBinding cellLoadMoreBinding = CellLoadMoreGridBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProgressCellViewHolder(cellLoadMoreBinding);
        }
    }

    public void addItems(List<Product> repoList) {
        mProductList.addAll(repoList);
        notifyDataSetChanged();
    }

    public void addItem(Product product) {
        mProductList.add(product);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mProductList.clear();
    }

    public void remove(int position) {
        mProductList.remove(position);
    }

    public List<Product> getArrayList() {
        return mProductList;
    }

    public class HomeCellViewHolder extends BaseViewHolder {

        private final CellProductBinding mBinding;

        public HomeCellViewHolder(CellProductBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemProductViewModel(mContext, mProductList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setProduct(mProductList.get(position));
            }
        }

    }

    public class ProgressCellViewHolder extends BaseViewHolder {

        private final CellLoadMoreGridBinding mBinding;

        public ProgressCellViewHolder(CellLoadMoreGridBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) mBinding.cardFavoriteCell.getLayoutParams();
            layoutParams.setFullSpan(true);
            mBinding.cardFavoriteCell.setLayoutParams(layoutParams);
            mBinding.progressBar.setIndeterminate(true);
        }
    }

}