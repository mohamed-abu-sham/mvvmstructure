package com.selwantech.raheeb.ui.accountjourney.settings;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSettingsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class SettingsFragment extends BaseFragment<FragmentSettingsBinding, SettingsViewModel> implements SettingsNavigator, ActivityResultCallBack {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private SettingsViewModel mOffersViewModel;
    private FragmentSettingsBinding mViewBinding;

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
        return true;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return this::callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public SettingsViewModel getViewModel() {
        mOffersViewModel = (SettingsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(SettingsViewModel.class, getViewDataBinding(), this);
        return mOffersViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.settings);
        mOffersViewModel.setUp();
    }

    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        mOffersViewModel.getTwitterAuthClient().onActivityResult(requestCode, resultCode, data);
    }
}
