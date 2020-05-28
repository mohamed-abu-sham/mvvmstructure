package com.selwantech.raheeb.ui.auth.phonenumber;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityPhoneNumberBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class PhoneNumberActivity extends BaseActivity<ActivityPhoneNumberBinding, PhoneNumberViewModel>
        implements PhoneNumberNavigator {

    int type;
    @Inject
    ViewModelProviderFactory factory;
    private PhoneNumberViewModel mPhoneNumberViewModel;
    private ActivityPhoneNumberBinding mViewBinding;

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, PhoneNumberActivity.class).putExtra("type", type);
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {
        setUpToolbar(getViewDataBinding().toolbar.toolbar,
                R.string.phone_number, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_number;
    }

    @Override
    public PhoneNumberViewModel getViewModel() {
        mPhoneNumberViewModel = (PhoneNumberViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(PhoneNumberViewModel.class, getViewDataBinding(), this);
        return mPhoneNumberViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();
        type = getIntent().getIntExtra("type", 0);
        mPhoneNumberViewModel.setType(type);

    }

}