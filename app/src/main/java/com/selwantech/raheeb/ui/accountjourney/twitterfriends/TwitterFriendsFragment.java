package com.selwantech.raheeb.ui.accountjourney.twitterfriends;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentTwitterFriendsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class TwitterFriendsFragment extends BaseFragment<FragmentTwitterFriendsBinding, TwitterFriendsViewModel>
        implements TwitterFriendsNavigator {

    private static final String TAG = TwitterFriendsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private TwitterFriendsViewModel mViewModel;
    private FragmentTwitterFriendsBinding mViewBinding;


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
        return R.layout.fragment_twitter_friends;
    }

    @Override
    public TwitterFriendsViewModel getViewModel() {
        mViewModel = (TwitterFriendsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(TwitterFriendsViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }


    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, getMyContext().getResources().getString(R.string.twitter_friends));
        mViewModel.setUp();
    }


}
