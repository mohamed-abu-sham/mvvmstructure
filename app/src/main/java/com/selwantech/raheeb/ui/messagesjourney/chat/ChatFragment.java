package com.selwantech.raheeb.ui.messagesjourney.chat;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentChatBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.chatdata.Chat;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatViewModel>
        implements ChatNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private ChatViewModel mViewModel;
    private FragmentChatBinding mViewBinding;

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.destroySocket();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mViewModel.chat != null){
            mViewModel.initiateSocket();
            mViewModel.joinRoom(mViewModel.chat.getId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.destroySocket();
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public boolean hideBottomSheet() {
        return true;
    }

    @Override
    public boolean isNeedActivityResult() {
        return false;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return null;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public ChatViewModel getViewModel() {
        mViewModel = (ChatViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ChatViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        if (getChatId() == -1) {
            mViewBinding.setData(getChat());
            setUpToolbar(getChat());
        }
        mViewModel.setUp();
    }

    @Override
    public void setUpToolbar(Chat chat){
        setUpToolbar(mViewBinding.toolbar, "", chat.getUser().getName());
    }
    @Override
    public Chat getChat() {
        return (Chat) getArguments().getSerializable(AppConstants.BundleData.CHAT);
    }

    @Override
    public int getChatId() {
        return getArguments().getInt(AppConstants.BundleData.CHAT_ID, -1);
    }


}