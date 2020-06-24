package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selwantech.raheeb.databinding.CellChatBinding;
import com.selwantech.raheeb.databinding.CellLoadMoreBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.chatdata.Chat;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.viewmodel.ItemChatViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final List<Chat> chatList;
    Context mContext;
    RecyclerClick mRecyclerClick;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener loadMoreListener;

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
//
//    DiffUtil.DiffResult<Chat> chatDiffResult = new DiffUtil.DiffResult()



    public ChatsAdapter(Context mContext, RecyclerClick mRecyclerClick, RecyclerView recyclerView) {
        this.chatList = new ArrayList<>();
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

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.loadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        if (!chatList.isEmpty()) {
            return chatList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            CellChatBinding cellBinding = CellChatBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ChatCellViewHolder(cellBinding);
        } else {
            CellLoadMoreBinding cellLoadMoreBinding = CellLoadMoreBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProgressCellViewHolder(cellLoadMoreBinding);
        }
    }

    public void addItems(List<Chat> repoList) {
        chatList.addAll(repoList);
    }

    public void addItem(Chat messages) {
        chatList.add(messages);
    }

    public void clearItems() {
        chatList.clear();
    }

    public void remove(int position) {
        chatList.remove(position);
    }

    public List<Chat> getArrayList() {
        return chatList;
    }

    public Chat getItem(int position) {
        return chatList.get(position);
    }
    public class ChatCellViewHolder extends BaseViewHolder {

        private final CellChatBinding mBinding;

        public ChatCellViewHolder(CellChatBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemChatViewModel(mContext, chatList.get(position), position, mRecyclerClick));
            } else {
                mBinding.getViewModel().setMessages(chatList.get(position));
            }
            mBinding.cardOfferCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerClick.onClick(chatList.get(position), position);
                }
            });
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