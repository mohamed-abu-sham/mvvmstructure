package com.selwantech.raheeb.ui.auth.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityLoginBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel>
        implements LoginNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mViewBinding;

    public static Intent newIntent(Context context, String inviteToken) {
        return new Intent(context, LoginActivity.class).putExtra(AppConstants.BundleData.INVITE_TOKEN, inviteToken);
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {
        setUpToolbar(getViewDataBinding().toolbar.toolbar,
                R.string.login,true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel = (LoginViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(LoginViewModel.class, getViewDataBinding(), this);
        return mLoginViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();
        mLoginViewModel.setUp();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginViewModel.getTwitterAuthClient().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getInviteToken() {
        return getIntent().getStringExtra(AppConstants.BundleData.INVITE_TOKEN);
    }
}