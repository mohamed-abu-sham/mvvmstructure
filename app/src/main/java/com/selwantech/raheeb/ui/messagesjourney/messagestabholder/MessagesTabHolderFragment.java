package com.selwantech.raheeb.ui.messagesjourney.messagestabholder;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentMessagesTabHolderBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import androidx.fragment.app.FragmentManager;


public class MessagesTabHolderFragment extends
        BaseFragment<FragmentMessagesTabHolderBinding, MessagesTabHolderViewModel>
        implements MessagesTabHolderNavigator {

    private static final String TAG = MessagesTabHolderFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private MessagesTabHolderViewModel mViewModel;
    private FragmentMessagesTabHolderBinding mViewBinding;

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public boolean hideBottomSheet() {
        return false;
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
    public void onResume() {
        super.onResume();
        mViewModel.onResume();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_messages_tab_holder;
    }

    @Override
    public MessagesTabHolderViewModel getViewModel() {
        mViewModel = (MessagesTabHolderViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(MessagesTabHolderViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mViewModel.setUp();
    }

    @Override
    public FragmentManager getChildFragment() {
        return getChildFragmentManager();
    }

}
