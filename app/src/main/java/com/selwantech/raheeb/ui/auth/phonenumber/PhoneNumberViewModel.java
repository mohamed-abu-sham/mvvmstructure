package com.selwantech.raheeb.ui.auth.phonenumber;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityPhoneNumberBinding;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.CustomObserverResponse;
import com.selwantech.raheeb.ui.auth.otpverifier.OtpVerifierActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.DeviceUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhoneNumberViewModel extends BaseViewModel<PhoneNumberNavigator, ActivityPhoneNumberBinding> {

    int type;

    public <V extends ViewDataBinding, N extends BaseNavigator> PhoneNumberViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (PhoneNumberNavigator) navigation, (ActivityPhoneNumberBinding) viewDataBinding);
        getViewBinding().ccp.setCountryForNameCode(DeviceUtils.getDeviceCountryCode(getMyContext()));

    }

    public void loginClicked() {
        ((PhoneNumberActivity) getMyContext()).finish();
    }

    public void sendCode() {
        if (isValidate()) {
            if (type == PhoneNumberTypes.REGISTER.getValue() || type == PhoneNumberTypes.REGISTER_SOCIAL.getValue()) {
                checkPhoneForRegister();
            } else if (type == PhoneNumberTypes.FORGET_PASSWORD.getValue()) {
                checkPhoneForForgetPassword();
            }
        }
    }

    public void checkPhoneForRegister() {
        getDataManager().getAuthService().getDataApi().verifyPhone(getViewBinding().ccp.getSelectedCountryCode() + getViewBinding().edPhoneNumber.getText().toString().trim())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<VerifyPhoneResponse>(getMyContext(), true, new APICallBack<VerifyPhoneResponse>() {
                    @Override
                    public void onSuccess(VerifyPhoneResponse response) {
//                        getMyContext().startActivity(OtpVerifierActivity.getStartIntent(getMyContext(),
//                                getViewBinding().ccp.getSelectedCountryCode() +
//                                        getViewBinding().edPhoneNumber.getText().toString().trim(),
//                                type, response.getToken()));
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        showToast(error);
                    }
                }));
    }

    public void checkPhoneForForgetPassword() {
        getDataManager().getAuthService().getDataApi().forgetPassword(getViewBinding().ccp.getSelectedCountryCode() + getViewBinding().edPhoneNumber.getText().toString().trim())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<VerifyPhoneResponse>(getMyContext(), true, new APICallBack<VerifyPhoneResponse>() {
                    @Override
                    public void onSuccess(VerifyPhoneResponse response) {
//                        getMyContext().startActivity(OtpVerifierActivity.getStartIntent(getMyContext(),
//                                getViewBinding().ccp.getSelectedCountryCode() +
//                                        getViewBinding().edPhoneNumber.getText().toString().trim(), type, response.getToken()));
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        showToast(error);
                    }
                }));
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

    @Override
    protected void setUp() {

    }
}
