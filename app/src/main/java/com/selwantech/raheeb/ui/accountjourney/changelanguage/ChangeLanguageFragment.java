package com.selwantech.raheeb.ui.accountjourney.changelanguage;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentChangeLanguageBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class ChangeLanguageFragment extends BaseFragment<FragmentChangeLanguageBinding, ChangeLanguageViewModel> {

    private static final String TAG = ChangeLanguageFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ChangeLanguageViewModel mChangeLanguageViewModel;
    private FragmentChangeLanguageBinding mViewBinding;

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
        return R.layout.fragment_change_language;
    }

    @Override
    public ChangeLanguageViewModel getViewModel() {
        mChangeLanguageViewModel = (ChangeLanguageViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ChangeLanguageViewModel.class, getViewDataBinding(), new Intent().putExtras(getArguments()));
        return mChangeLanguageViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mChangeLanguageViewModel.setUp();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.change_language);
    }


}
