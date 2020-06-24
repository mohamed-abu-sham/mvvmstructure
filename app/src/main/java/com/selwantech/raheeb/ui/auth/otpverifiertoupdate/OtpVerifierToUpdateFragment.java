package com.selwantech.raheeb.ui.auth.otpverifiertoupdate;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentOtpVerifierToUpdateBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class OtpVerifierToUpdateFragment extends BaseFragment<FragmentOtpVerifierToUpdateBinding, OtpVerifierToUpdateViewModel>
        implements OtpVerifierToUpdateNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private OtpVerifierToUpdateViewModel mOtpViewModel;
    private FragmentOtpVerifierToUpdateBinding mViewBinding;

    public static Intent getStartIntent(Context context, int type, String token, String inviteToken) {
        Intent intent = new Intent(context, OtpVerifierToUpdateFragment.class)
                .putExtra(AppConstants.BundleData.TYPE, type)
                .putExtra(AppConstants.BundleData.TOKEN, token)
                .putExtra(AppConstants.BundleData.INVITE_TOKEN, inviteToken);
        return intent;
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
        return R.layout.fragment_otp_verifier_to_update;
    }

    @Override
    public OtpVerifierToUpdateViewModel getViewModel() {
        mOtpViewModel = (OtpVerifierToUpdateViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(OtpVerifierToUpdateViewModel.class, getViewDataBinding(), this);
        return mOtpViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mOtpViewModel.setUp();
        setUpToolbar(mViewBinding.toolbar, "", R.string.verification_code);
    }


    @Override
    public String getToken() {
        return getArguments().getString(AppConstants.BundleData.TOKEN);
    }


}