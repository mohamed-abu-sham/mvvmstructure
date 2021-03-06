package com.selwantech.raheeb.ui.auth.forgetpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityForgetPasswordBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class ForgetPasswordActivity extends BaseActivity<ActivityForgetPasswordBinding, ForgetPasswordViewModel> {

    int type;
    @Inject
    ViewModelProviderFactory factory;
    private ForgetPasswordViewModel mPhoneNumberViewModel;
    private ActivityForgetPasswordBinding mViewBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgetPasswordActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {
        setUpToolbar(getViewDataBinding().toolbar.toolbar, R.string.forget_password, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public ForgetPasswordViewModel getViewModel() {
        mPhoneNumberViewModel = (ForgetPasswordViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ForgetPasswordViewModel.class, getViewDataBinding(), getIntent());
        return mPhoneNumberViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();
        mPhoneNumberViewModel.setUp();
    }
}