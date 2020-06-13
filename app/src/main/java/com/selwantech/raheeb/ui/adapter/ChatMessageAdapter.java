package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.databinding.CellChatItemReceivedBinding;
import com.selwantech.raheeb.databinding.CellChatItemSentBinding;
import com.selwantech.raheeb.databinding.CellLoadMoreBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.ChatMessageRecyclerClick;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ItemChatMessageReceivedViewModel;
import com.selwantech.raheeb.viewmodel.ItemChatMessageSentViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM_SENT = 1;
    private final int VIEW_ITEM_RECEIVED = 2;
    private Context mContext;
    private ArrayList<ChatObject> chatObjectArrayList;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer = new CountDownTimer(30000, 30000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (chatObjectArrayList != null && chatObjectArrayList.size() > 0) {
                notifyDataSetChanged();
            }
            countDownTimer.start();
        }
    };
    private ChatMessageRecyclerClick mRecyclerClick;
    private int firstVisiblesItems;
    private boolean loading = false;
    private OnLoadMoreListener loadMoreListener;

    public ChatMessageAdapter(Context mContext,
                              ChatMessageRecyclerClick mRecyclerClick, RecyclerView recyclerView,
                              MediaPlayer mediaPlayer) {
        this.mContext = mContext;
        this.chatObjectArrayList = new ArrayList<>();
        this.mRecyclerClick = mRecyclerClick;
        this.mediaPlayer = mediaPlayer;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    firstVisiblesItems = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (!loading && (firstVisiblesItems == 0)) {
                        if (loadMoreListener != null) {
                            loadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
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

    public boolean isLoading() {
        return loading;
    }

    public ChatObject getItem(int position) {
        return chatObjectArrayList.get(position);
    }

    public void addItems(List<ChatObject> chatObjectList) {
        chatObjectArrayList.addAll(chatObjectList);
        notifyDataSetChanged();
    }

    public void addItem(ChatObject chatObject) {
        chatObjectArrayList.add(chatObject);
        notifyDataSetChanged();
    }

    public void addItem(int position, ChatObject chatObject) {
        chatObjectArrayList.add(position, chatObject);
        notifyDataSetChanged();
    }

    public void clearItems() {
        chatObjectArrayList.clear();
    }

    public void remove(int position) {
        chatObjectArrayList.remove(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (chatObjectArrayList.get(position) == null) {
            return VIEW_PROG;
        } else if (chatObjectArrayList.get(position).getSender().id != User.getInstance().getUserID()) {
            return VIEW_ITEM_RECEIVED;
        } else {
            return VIEW_ITEM_SENT;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM_SENT) {
            CellChatItemSentBinding cellBinding = CellChatItemSentBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ChatCellSentViewHolder(cellBinding);
        }
        if (viewType == VIEW_ITEM_RECEIVED) {
            CellChatItemReceivedBinding cellBinding = CellChatItemReceivedBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ChatCellReceivedViewHolder(cellBinding);
        } else {
            CellLoadMoreBinding cellLoadMoreBinding = CellLoadMoreBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProgressCellViewHolder(cellLoadMoreBinding);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return chatObjectArrayList.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public class ChatCellSentViewHolder extends BaseViewHolder {

        private final CellChatItemSentBinding mBinding;

        public ChatCellSentViewHolder(CellChatItemSentBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            ChatObject chatObject = chatObjectArrayList.get(position);
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemChatMessageSentViewModel(mContext, chatObjectArrayList.get(position),
                        position, mBinding, mRecyclerClick, mediaPlayer));
            } else {
                mBinding.getViewModel().setMessages(chatObjectArrayList.get(position));
            }
        }
    }

    public class ChatCellReceivedViewHolder extends BaseViewHolder {

        private final CellChatItemReceivedBinding mBinding;

        public ChatCellReceivedViewHolder(CellChatItemReceivedBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            ChatObject chatObject = chatObjectArrayList.get(position);
            if (mBinding.getViewModel() == null) {
                mBinding.setViewModel(new ItemChatMessageReceivedViewModel(mContext, chatObjectArrayList.get(position),
                        position, mBinding, mRecyclerClick));
            } else {
                mBinding.getViewModel().setMessages(chatObjectArrayList.get(position));
            }
            if (chatObject.getMessage_type().equals("text")) {
                mBinding.tvInTxtDate.setText(chatObject.getDate());
                mBinding.linearInMsgTxt.setVisibility(View.VISIBLE);
                mBinding.tvInMessage.setVisibility(View.VISIBLE);
            } else if (chatObject.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER)) {
                mBinding.tvInTxtDate.setText(chatObject.getDate());
                mBinding.tvInMessage.setText(chatObject.getMessage());
                mBinding.linearInMsgTxt.setVisibility(View.VISIBLE);
                mBinding.tvInMessage.setVisibility(View.VISIBLE);
            }
            GeneralFunction.loadImage(mContext, chatObject.getSender().getAvatar(), mBinding.imgOtherSideAvatar);

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
