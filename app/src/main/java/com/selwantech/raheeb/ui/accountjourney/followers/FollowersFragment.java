package com.selwantech.raheeb.ui.accountjourney.followers;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentFollowersBinding;
import com.selwantech.raheeb.databinding.FragmentFollowingBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class FollowersFragment extends BaseFragment<FragmentFollowersBinding, FollowersViewModel>
        implements FollowersNavigator {

    private static final String TAG = FollowersFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private FollowersViewModel mViewModel;
    private FragmentFollowersBinding mViewBinding;


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
        return R.layout.fragment_followers;
    }

    @Override
    public FollowersViewModel getViewModel() {
        mViewModel = (FollowersViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(FollowersViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }


    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar,TAG,getMyContext().getResources().getString(R.string.following));
        mViewModel.setUp();
    }


}
