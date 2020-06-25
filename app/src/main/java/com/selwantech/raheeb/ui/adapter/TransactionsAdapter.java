package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.selwantech.raheeb.databinding.CellLoadMoreBinding;
import com.selwantech.raheeb.databinding.CellTransactionBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Transaction;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemTransactionViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final List<Transaction> transactionList;
    Context mContext;
    RecyclerClick mRecyclerClick;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener loadMoreListener;

    public TransactionsAdapter(Context mContext, RecyclerClick mRecyclerClick, RecyclerView recyclerView) {
        this.transactionList = new ArrayList<>();
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
//                    totalItemCount = linearLayoutManager.getItemCount();
//                    lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                    if (!loading && totalItemCount - 1 == (lastVisibleItem)) {
//                        if (loadMoreListener != null) {
//                            loadMoreListener.onLoadMore();
//                        }
//                        loading = true;
//                    }
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
//
//    DiffUtil.DiffResult<Chat> chatDiffResult = new DiffUtil.DiffResult()

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.loadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        if (!transactionList.isEmpty()) {
            return transactionList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return transactionList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            CellTransactionBinding cellBinding = CellTransactionBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new TransactionCellViewHolder(cellBinding);
        } else {
            CellLoadMoreBinding cellLoadMoreBinding = CellLoadMoreBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProgressCellViewHolder(cellLoadMoreBinding);
        }
    }

    public void addItems(List<Transaction> repoList) {
        transactionList.addAll(repoList);
    }

    public void addItem(Transaction transaction) {
        transactionList.add(transaction);
    }

    public void clearItems() {
        transactionList.clear();
    }

    public void remove(int position) {
        transactionList.remove(position);
    }

    public List<Transaction> getArrayList() {
        return transactionList;
    }

    public Transaction getItem(int position) {
        return transactionList.get(position);
    }

    public class TransactionCellViewHolder extends BaseViewHolder {

        private final CellTransactionBinding mBinding;

        public TransactionCellViewHolder(CellTransactionBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemTransactionViewModel(mContext, transactionList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setTransaction(transactionList.get(position));
            }
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