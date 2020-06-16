package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.selwantech.raheeb.databinding.CellFollowBinding;
import com.selwantech.raheeb.databinding.CellLoadMoreBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.ItemClickWithType;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemFollowViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FollowAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final List<ProductOwner> usersList;
    Context mContext;
    ItemClickWithType sellingItemClick;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener loadMoreListener;

    public FollowAdapter(Context mContext, ItemClickWithType itemClickWithType, RecyclerView recyclerView) {
        this.usersList = new ArrayList<>();
        this.mContext = mContext;
        this.sellingItemClick = itemClickWithType;

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
        if (!usersList.isEmpty()) {
            return usersList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return usersList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            CellFollowBinding cellBinding = CellFollowBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new FollowCellViewHolder(cellBinding);
        } else {
            CellLoadMoreBinding cellLoadMoreBinding = CellLoadMoreBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProgressCellViewHolder(cellLoadMoreBinding);
        }
    }

    public void addItems(List<ProductOwner> repoList) {
        usersList.addAll(repoList);
        notifyDataSetChanged();
    }

    public void addItem(ProductOwner user) {
        usersList.add(user);
        notifyDataSetChanged();
    }

    public void clearItems() {
        usersList.clear();
    }

    public void remove(int position) {
        usersList.remove(position);
    }

    public List<ProductOwner> getArrayList() {
        return usersList;
    }

    public ProductOwner getItem(int position) {
        return usersList.get(position);
    }

    public class FollowCellViewHolder extends BaseViewHolder {

        private final CellFollowBinding mBinding;

        public FollowCellViewHolder(CellFollowBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemFollowViewModel(mContext, usersList.get(position), position, sellingItemClick));
            } else {
                mBinding.getViewModel().setUser(usersList.get(position));
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