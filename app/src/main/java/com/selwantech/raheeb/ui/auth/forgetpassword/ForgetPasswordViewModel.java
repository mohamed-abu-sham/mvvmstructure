package com.selwantech.raheeb.ui.auth.forgetpassword;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityForgetPasswordBinding;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.auth.otpverifier.OtpVerifierActivity;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;

public class ForgetPasswordViewModel extends BaseViewModel<ActivityForgetPasswordBinding> {

    public <V extends ViewDataBinding, N> ForgetPasswordViewModel(Context mContext, DataManager dataManager, V viewDataBinding, Intent intent) {
        super(mContext, dataManager, intent, (ActivityForgetPasswordBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {

    }

    public void sendCode() {
        if (isValidate()) {
            sendOtp();
        }
    }

    public void sendOtp() {
        getDataManager().getAuthService().sendOtpForgetPassword(getMyContext(), true, getViewBinding().edPhoneNumber.getText().toString(), new APICallBack<VerifyPhoneResponse>() {
            @Override
            public void onSuccess(VerifyPhoneResponse response) {
                getBaseActivity().startActivity(OtpVerifierActivity
                        .getStartIntent(getMyContext(),
                                PhoneNumberTypes.FORGET_PASSWORD.getValue()
                                , response.getToken(), ""));
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok),
                        new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }

    private boolean isValidate() {
        if (getViewBinding().edPhoneNumber.getText().toString().trim().isEmpty()) {
            getViewBinding().edPhoneNumber.setError(getMyContext().getString(R.string.please_fill_your_phone_number));
            return false;
        }
        return true;
    }
}
