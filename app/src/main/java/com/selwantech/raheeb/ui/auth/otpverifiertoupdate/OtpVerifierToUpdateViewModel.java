package com.selwantech.raheeb.ui.auth.otpverifiertoupdate;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentOtpVerifierToUpdateBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.utils.SnackViewBulider;
import com.selwantech.raheeb.utils.TimeUtils;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;

public class OtpVerifierToUpdateViewModel extends BaseViewModel<OtpVerifierToUpdateNavigator, FragmentOtpVerifierToUpdateBinding> {

    int type;
    long milliToFinish = 90000;
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

    public <V extends ViewDataBinding, N extends BaseNavigator> OtpVerifierToUpdateViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (OtpVerifierToUpdateNavigator) navigation, (FragmentOtpVerifierToUpdateBinding) viewDataBinding);
        setOtpTextWatcher();
        countDownTimer.start();
    }

    @Override
    protected void setUp() {
    }

    public void verifyCode() {
        if (isValidate()) {
            verifyOtpToUpdate();
        }
    }

    private void verifyOtpToUpdate() {
        getDataManager().getAuthService().verifyOtpToUpdate(getMyContext(),
                true, getNavigator().getToken(), getOtp(), new APICallBack<User>() {
                    @Override
                    public void onSuccess(User response) {
                        showSuccessDialog();
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

    private void showSuccessDialog() {
        new OnLineDialog(getMyContext()) {
            @Override
            public void onPositiveButtonClicked() {
                dismiss();
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_otpVerifierToUpdateFragment_to_settingsFragment);
            }

            @Override
            public void onNegativeButtonClicked() {
                dismiss();
            }
        }.showConfirmationDialog(DialogTypes.OK, getMyContext().getResources().getString(R.string.success),
                getMyContext().getResources().getString(R.string.update_successfully));
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