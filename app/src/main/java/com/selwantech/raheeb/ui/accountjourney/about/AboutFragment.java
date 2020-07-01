package com.selwantech.raheeb.ui.accountjourney.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAboutBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class AboutFragment extends BaseFragment<FragmentAboutBinding, AboutViewModel> {

    private static final String TAG = AboutFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private AboutViewModel mViewModel;
    private FragmentAboutBinding mViewBinding;


    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
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
        return R.layout.fragment_about;
    }

    @Override
    public AboutViewModel getViewModel() {
        mViewModel = (AboutViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(AboutViewModel.class, getViewDataBinding(), new Intent().putExtras(getArguments()));
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.about_raheeb);
        mViewModel.setUp();
    }

}
