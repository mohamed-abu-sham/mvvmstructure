package com.selwantech.raheeb.ui.auth.register;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityRegisterBinding;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.auth.otpverifier.OtpVerifierActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator, ActivityRegisterBinding> {

    public <V extends ViewDataBinding, N extends BaseNavigator> RegisterViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (RegisterNavigator) navigation, (ActivityRegisterBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {

    }

    public void privacyPolicyClicked() {

    }

    public void registerClicked() {
        if (isValid()) {
            if (!getViewBinding().checkboxReadTerm.isChecked()) {
                ((RegisterActivity) getMyContext()).showSnackBar(getViewBinding().getRoot(), R.drawable.ic_warning,
                        getMyContext().getResources().getString(R.string.privacy_policy),
                        getMyContext().getResources().getString(R.string.you_should_accpet_our_condition_and_terms),
                        getMyContext().getResources().getString(R.string.OK), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
                return;
            }
            generateUserObj();
            sendOtp();
        }
    }

    public void sendOtp() {
        getDataManager().getAuthService().sendOtp(getMyContext(), true, User.getInstance().getPhone(), new APICallBack<VerifyPhoneResponse>() {
            @Override
            public void onSuccess(VerifyPhoneResponse response) {
                User.getInstance().setToken(response.getToken());
                getMyContext().startActivity(OtpVerifierActivity.getStartIntent(getMyContext()
                        , PhoneNumberTypes.REGISTER.getValue()));
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

    private User generateUserObj() {
        User user = User.getInstance();
        user.setEmail(getViewBinding().edEmail.getText().toString().trim());
        user.setName(getViewBinding().edUserName.getText().toString());
        user.setPhone(getViewBinding().edPhoneNumber.getText().toString());
        user.setPassword(getViewBinding().edPassword.getText().toString());
        user.setPassword_confirmation(getViewBinding().edConfirmPassword.getText().toString());
        user.setFirebase_token(SessionManager.getKeyFirebaseToken());
        return user;
    }

    public boolean isValid() {
        int error = 0;


        if (getViewBinding().edUserName.getText().toString().isEmpty()) {
            error = +1;
            getViewBinding().edUserName.setError(getMyContext().getString(R.string.user_name_is_required));
        }

        if (getViewBinding().edUserName.getText().toString().isEmpty()) {
            error = +1;
            getViewBinding().edUserName.setError(getMyContext().getString(R.string.user_name_is_required));
        }

        if (getViewBinding().edPhoneNumber.getText().toString().isEmpty()) {
            error = +1;
            getViewBinding().edPhoneNumber.setError(getMyContext().getString(R.string.phone_number_is_required));
        }

        if (getViewBinding().edPassword.getText().toString().isEmpty()) {
            error = +1;
            getViewBinding().edPassword.setError(getMyContext().getString(R.string.password_is_required));
        }

        if (!getViewBinding().edPassword.getText().toString().equals(getViewBinding().edConfirmPassword.getText().toString())) {
            error = +1;
            getViewBinding().edPassword.setError(getMyContext().getString(R.string.does_not_match));
            getViewBinding().edConfirmPassword.setError(getMyContext().getString(R.string.does_not_match));
        }

        return error == 0;
    }


}
