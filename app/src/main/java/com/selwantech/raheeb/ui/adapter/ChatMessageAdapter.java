package com.selwantech.raheeb.ui.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.selwantech.raheeb.databinding.CellChatItemBinding;
import com.selwantech.raheeb.databinding.CellLoadMoreBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.ChatMessageRecyclerClick;
import com.selwantech.raheeb.model.ChatObject;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.ui.base.BaseViewHolder;
import com.selwantech.raheeb.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;
    private final int VIEW_TYPING = 2;
    private Context mContext;
    private ArrayList<ChatObject> chatObjectArrayList;
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
                              ChatMessageRecyclerClick mRecyclerClick, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.chatObjectArrayList = new ArrayList<>();
        this.mRecyclerClick = mRecyclerClick;

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
        return chatObjectArrayList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            CellChatItemBinding cellBinding = CellChatItemBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ChatCellViewHolder(cellBinding);
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

    public class ChatCellViewHolder extends BaseViewHolder {

        private final CellChatItemBinding mBinding;

        public ChatCellViewHolder(CellChatItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            ChatObject chatObject = chatObjectArrayList.get(position);
            if (chatObject.getSender().id == User.getInstance().getUserID()) {
                mBinding.cardResend.setVisibility(View.GONE);
                if (!chatObject.isSent() && chatObject.getId() <= 0) {
                    mBinding.cardResend.setVisibility(View.VISIBLE);
                }
                if (chatObject.getMessage_type().equals("text")) {
                    mBinding.tvOutMessage.setText(chatObject.getMessage());
                    mBinding.tvOutDate.setText(chatObject.getDate());
                    mBinding.tvOutMessage.setVisibility(View.VISIBLE);
                    mBinding.tvOutMessage.setVisibility(View.VISIBLE);
                } else if (chatObject.getMessage_type().equals(AppConstants.MESSAGE_TYPE.OFFER)) {
                    mBinding.tvOutMessage.setText(chatObject.getMessage());
                    mBinding.tvOutDate.setText(chatObject.getDate());
                    mBinding.linearOutMsg.setVisibility(View.VISIBLE);
                    mBinding.tvOutMessage.setVisibility(View.VISIBLE);
                }
                GeneralFunction.loadImage(mContext, chatObject.getSender().getAvatar(), mBinding.imgMyAvatar);
                mBinding.linearInMsgTxt.setVisibility(View.GONE);
            } else {
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
                mBinding.linearOutMsg.setVisibility(View.GONE);
            }

            mBinding.relativeCellChatItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!chatObject.isSent() && chatObject.getId() <= 0) {
                        mRecyclerClick.onClick(chatObject, position, true);
                    } else {
                        mRecyclerClick.onClick(chatObject, position, false);
                    }
                }
            });
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
