package com.selwantech.raheeb.ui.auth.otpverifier;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityOtpVerifierBinding;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.RegisterResponse;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.auth.createpassword.CreatePasswordActivity;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;
import com.selwantech.raheeb.utils.TimeUtils;

import androidx.databinding.ViewDataBinding;

public class OtpVerifierViewModel extends BaseViewModel<ActivityOtpVerifierBinding> {

    int type;
    long milliToFinish = 90000;

    public <V extends ViewDataBinding, N> OtpVerifierViewModel(Context mContext, DataManager dataManager, V viewDataBinding, Intent intent) {
        super(mContext, dataManager, intent, (ActivityOtpVerifierBinding) viewDataBinding);
        setOtpTextWatcher();
        countDownTimer.start();
    }

    CountDownTimer countDownTimer = new CountDownTimer(milliToFinish, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getViewBinding().tvTimeToSecond.setText(TimeUtils.millisecondToMinAndSec(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            milliToFinish = 0;
            getViewBinding().tvResend.setTextColor(getMyContext().getResources().getColor(R.color.colorPrimaryDark));
        }
    };

    @Override
    protected void setUp() {
    }

    public void verifyCode() {
        if (isValidate()) {
            if (type == PhoneNumberTypes.REGISTER.getValue()) {
                verifyOtp();
            } else if (type == PhoneNumberTypes.FORGET_PASSWORD.getValue()) {
                verifyOtp();
            }
        }
    }

    private void verifyOtp() {
        getDataManager().getAuthService().verifyOtp(getMyContext(),
                true, getIntent().getStringExtra(AppConstants.BundleData.TOKEN), getOtp(), new APICallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        if (type == PhoneNumberTypes.REGISTER.getValue()) {
                            registerUser();
                        } else if (type == PhoneNumberTypes.FORGET_PASSWORD.getValue()) {
                            getBaseActivity().startActivity(CreatePasswordActivity.newIntent(getMyContext(), getIntent().getStringExtra(AppConstants.BundleData.TOKEN)));
                        }
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

    public void registerUser() {
        if (isValidate()) {
            getDataManager().getAuthService().registerUser(getMyContext(),
                    true, User.getInstance(), getIntent().getStringExtra(AppConstants.BundleData.INVITE_TOKEN), new APICallBack<RegisterResponse>() {
                        @Override
                        public void onSuccess(RegisterResponse response) {
                            User user = response.getUser();
                            user.setToken(response.getJwt_token());
                            User.getInstance().setObjUser(user);
                            SessionManager.createUserLoginSession();
                            getDataManager().getAuthService().reInit();
                            getBaseActivity().finishAffinity();
                            getBaseActivity().startActivity(MainActivity.newIntent(getMyContext(), null));
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
    }

    public boolean isValidate() {
        int error = 0;
        if (getViewBinding().userOtp1.getText().toString().isEmpty()) {
            getViewBinding().userOtp1.setHintTextColor(getMyContext().getResources().getColor(R.color.Rose));
            error = +1;
        }
        if (getViewBinding().userOtp2.getText().toString().isEmpty()) {
            getViewBinding().userOtp2.setHintTextColor(getMyContext().getResources().getColor(R.color.Rose));
            error = +1;
        }
        if (getViewBinding().userOtp3.getText().toString().isEmpty()) {
            getViewBinding().userOtp3.setHintTextColor(getMyContext().getResources().getColor(R.color.Rose));
            error = +1;
        }
        if (getViewBinding().userOtp4.getText().toString().isEmpty()) {
            getViewBinding().userOtp4.setHintTextColor(getMyContext().getResources().getColor(R.color.Rose));
            error = +1;
        }
        return error == 0;
    }

    public void setOtpTextWatcher() {
        getViewBinding().userOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 1) {
                    getViewBinding().userOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getViewBinding().userOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 1) {
                    getViewBinding().userOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getViewBinding().userOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 1) {
                    getViewBinding().userOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public String getOtp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getViewBinding().userOtp1.getText().toString());
        stringBuilder.append(getViewBinding().userOtp2.getText().toString());
        stringBuilder.append(getViewBinding().userOtp3.getText().toString());
        stringBuilder.append(getViewBinding().userOtp4.getText().toString());
        return stringBuilder.toString();
    }

    public void setType(int type) {
        this.type = type;
    }

    public void resendCode() {
        if (milliToFinish == 0) {
            getDataManager().getAuthService().resendOtp(getMyContext(),
                    true, User.getInstance().getToken(), new APICallBack<VerifyPhoneResponse>() {
                        @Override
                        public void onSuccess(VerifyPhoneResponse response) {
                            User.getInstance().setToken(response.getToken());
                            milliToFinish = 90000;
                            countDownTimer.start();
                            getViewBinding().tvResend.setTextColor(getMyContext().getResources().getColor(R.color.black));
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
    }

}