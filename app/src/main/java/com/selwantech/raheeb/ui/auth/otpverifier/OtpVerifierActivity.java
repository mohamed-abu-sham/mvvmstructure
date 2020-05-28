package com.selwantech.raheeb.ui.auth.otpverifier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityOtpVerifierBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class OtpVerifierActivity extends BaseActivity<ActivityOtpVerifierBinding, OtpVerifierViewModel>
        implements OtpVerifierNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private OtpVerifierViewModel mOtpViewModel;
    private ActivityOtpVerifierBinding mViewBinding;

    public static Intent getStartIntent(Context context, int type) {
        Intent intent = new Intent(context, OtpVerifierActivity.class).putExtra(AppConstants.BundleData.TYPE, type);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {
        setUpToolbar(getViewDataBinding().toolbar.toolbar ,R.string.verification_code, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp_verifier;
    }

    @Override
    public OtpVerifierViewModel getViewModel() {
        mOtpViewModel = (OtpVerifierViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(OtpVerifierViewModel.class, getViewDataBinding(), this);
        return mOtpViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();
        mOtpViewModel.setType(getIntent().getIntExtra(AppConstants.BundleData.TYPE, 0));
        mOtpViewModel.setUp();
    }

}