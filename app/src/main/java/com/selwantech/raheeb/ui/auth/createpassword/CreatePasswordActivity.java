package com.selwantech.raheeb.ui.auth.createpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityCreatePasswordBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class CreatePasswordActivity extends BaseActivity<ActivityCreatePasswordBinding, CreatePasswordViewModel> {

    @Inject
    ViewModelProviderFactory factory;
    private CreatePasswordViewModel mCreatePasswordViewModel;
    private ActivityCreatePasswordBinding mViewBinding;

    public static Intent newIntent(Context context, String token) {
        return new Intent(context, CreatePasswordActivity.class).putExtra("token", token);
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {
        setUpToolbar(getViewDataBinding().toolbar.toolbar, R.string.create_password, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_password;
    }

    @Override
    public CreatePasswordViewModel getViewModel() {
        mCreatePasswordViewModel = (CreatePasswordViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(CreatePasswordViewModel.class, getViewDataBinding(),getIntent());
        return mCreatePasswordViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();
        mCreatePasswordViewModel.setUp();
    }

}