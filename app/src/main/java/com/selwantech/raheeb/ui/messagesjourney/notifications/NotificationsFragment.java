package com.selwantech.raheeb.ui.messagesjourney.notifications;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentNotificationsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class NotificationsFragment extends BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>
        implements NotificationsNavigator {

    private static final String TAG = NotificationsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private NotificationsViewModel mHomeViewModel;
    private FragmentNotificationsBinding mViewBinding;


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
    public int getLayoutId() {
        return R.layout.fragment_notifications;
    }

    @Override
    public NotificationsViewModel getViewModel() {
        mHomeViewModel = (NotificationsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(NotificationsViewModel.class, getViewDataBinding(), this);
        return mHomeViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }


    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mHomeViewModel.setUp();
    }

}
