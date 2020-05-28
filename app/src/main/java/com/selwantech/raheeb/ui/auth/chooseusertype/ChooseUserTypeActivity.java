package com.selwantech.raheeb.ui.auth.chooseusertype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityChooseUserTypeBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class ChooseUserTypeActivity extends BaseActivity<ActivityChooseUserTypeBinding, ChooseUserTypeViewModel>
        implements ChooseUserTypeNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private ChooseUserTypeViewModel mViewModel;
    private ActivityChooseUserTypeBinding mViewBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ChooseUserTypeActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_user_type;
    }

    @Override
    public ChooseUserTypeViewModel getViewModel() {
        mViewModel = (ChooseUserTypeViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ChooseUserTypeViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();
    }
}