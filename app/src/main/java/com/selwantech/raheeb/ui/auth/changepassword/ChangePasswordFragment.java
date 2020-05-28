package com.selwantech.raheeb.ui.auth.changepassword;

import android.content.Context;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentChangePasswordBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class ChangePasswordFragment extends BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel> implements ChangePasswordNavigator {

    private static final String TAG = ChangePasswordFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ChangePasswordViewModel mChangePasswordViewModel;
    private FragmentChangePasswordBinding mViewBinding;


    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        return R.layout.fragment_change_password;
    }

    @Override
    public ChangePasswordViewModel getViewModel() {
        mChangePasswordViewModel = (ChangePasswordViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ChangePasswordViewModel.class, getViewDataBinding(), this);
        return mChangePasswordViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mChangePasswordViewModel.setUp();
        setUpToolbar(mViewBinding.toolbar, TAG,R.string.change_password);
    }
}
