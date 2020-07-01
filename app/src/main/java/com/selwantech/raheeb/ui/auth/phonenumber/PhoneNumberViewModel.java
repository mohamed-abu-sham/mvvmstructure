package com.selwantech.raheeb.ui.auth.phonenumber;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUpdatePhoneBinding;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;

public class PhoneNumberViewModel extends BaseViewModel<PhoneNumberNavigator, FragmentUpdatePhoneBinding> {

    int type;

    public <V extends ViewDataBinding, N extends BaseNavigator> PhoneNumberViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (PhoneNumberNavigator) navigation, (FragmentUpdatePhoneBinding) viewDataBinding);

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
        getDataManager().getAuthService().sendOtp(getMyContext(), true, getViewBinding().edPhoneNumber.getText().toString(), new APICallBack<VerifyPhoneResponse>() {
            @Override
            public void onSuccess(VerifyPhoneResponse response) {

//                getMyContext().startActivity(OtpVerifierActivity.getStartIntent(getMyContext()
//                        , PhoneNumberTypes.CHANGE_PHONE_NUMBER.getValue()));

                Bundle data = new Bundle();
                data.putString(AppConstants.BundleData.TOKEN, response.getToken());
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_phoneNumberFragment_to_otpVerifierToUpdateFragment, data);
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

    public void setType(int type) {
        this.type = type;
    }

    public String phoneNumber() {
        return User.getInstance().getPhone() != null &&
                User.getInstance().getPhone().isEmpty() ? "" : User.getInstance().getPhone();
    }
}
