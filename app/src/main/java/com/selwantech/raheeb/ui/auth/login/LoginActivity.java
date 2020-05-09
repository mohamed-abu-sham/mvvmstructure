package com.selwantech.raheeb.ui.auth.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.ViewModelProviderFactory;
import com.selwantech.raheeb.databinding.ActivityLoginBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;

import javax.inject.Inject;

import static com.selwantech.raheeb.ui.auth.login.LoginViewModel.GOOGLE_SIGN_IN;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel>
        implements LoginNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mViewBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
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
        mLoginViewModel.getFacebookCallbackManager().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            mLoginViewModel.handleGoogleSignInResult(task);
        }
    }
}