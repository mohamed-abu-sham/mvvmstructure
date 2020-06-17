package com.selwantech.raheeb.ui.accountjourney.settings;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSettingsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class SettingsFragment extends BaseFragment<FragmentSettingsBinding, SettingsViewModel> implements SettingsNavigator {

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
        mViewBinding.setUser(
                User.getInstance());
        mOffersViewModel.setUp();
    }
}
