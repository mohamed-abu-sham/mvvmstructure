package com.selwantech.raheeb.ui.accountjourney.following;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentFollowingBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class FollowingFragment extends BaseFragment<FragmentFollowingBinding, FollowingViewModel>
        implements FollowingNavigator {

    private static final String TAG = FollowingFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private FollowingViewModel mViewModel;
    private FragmentFollowingBinding mViewBinding;


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
        return R.layout.fragment_following;
    }

    @Override
    public FollowingViewModel getViewModel() {
        mViewModel = (FollowingViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(FollowingViewModel.class, getViewDataBinding(), this);
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
