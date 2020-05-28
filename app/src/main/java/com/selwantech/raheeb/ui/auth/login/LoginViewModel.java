package com.selwantech.raheeb.ui.auth.login;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityLoginBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.LoginObject;
import com.selwantech.raheeb.model.RegisterResponse;
import com.selwantech.raheeb.model.SocialLogin;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.CustomObserverResponse;
import com.selwantech.raheeb.ui.auth.phonenumber.PhoneNumberActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.utils.LanguageUtils;
import com.selwantech.raheeb.utils.SnackViewBulider;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginNavigator, ActivityLoginBinding> {

    private static final String TAG = "LoginViewModel";
    protected static int GOOGLE_SIGN_IN = 1;


    private TwitterAuthClient twitterAuthClient;
    private TwitterLoginButton twitterLoginButton;

    public <V extends ViewDataBinding, N extends BaseNavigator> LoginViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (LoginNavigator) navigation, (ActivityLoginBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        String lang = LanguageUtils.getLanguage(getMyContext());
        if (lang.equals("ar")) {
            getViewBinding().tvLanguage.setText(getMyContext().getResources().getString(R.string.arabic));
        } else {
            getViewBinding().tvLanguage.setText(getMyContext().getResources().getString(R.string.english));
        }

        getViewBinding().linearLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLineDialog.showPopupLanguage(getBaseActivity());
            }
        });
    }


    public void signUpClick() {
        getMyContext().startActivity(PhoneNumberActivity.newIntent(getMyContext(), PhoneNumberTypes.REGISTER.getValue()));
    }


    public void loginClick() {
        if (isValidate()) {
            getDataManager().getAuthService().login(getMyContext(), true, getLoginObject(), new APICallBack<RegisterResponse>() {
                @Override
                public void onSuccess(RegisterResponse response) {
                    User user = response.getUser();
                    user.setToken(response.getJwt_token());
                    User.getInstance().setObjUser(user);
                    SessionManager.createUserLoginSession();
                    getDataManager().getAuthService().reInit();
                    getBaseActivity().finishAffinity();
                    getBaseActivity().startActivity(MainActivity.newIntent(getMyContext()));
                    showSnackBar(getMyContext().getString(R.string.error),
                            getMyContext().getResources().getString(R.string.welcome),
                            getMyContext().getResources().getString(R.string.ok),
                            new SnackViewBulider.SnackbarCallback() {
                                @Override
                                public void onActionClick(Snackbar snackbar) {
                                    snackbar.dismiss();
                                }
                            });
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

    private LoginObject getLoginObject() {
        return new LoginObject(getViewBinding().edPhoneNumber.getText().toString(),
                getViewBinding().edPassword.getText().toString(),
                SessionManager.getKeyFirebaseToken());

    }

    private boolean isValidate() {

        int error = 0;
        if (getViewBinding().edPhoneNumber.getText().toString().isEmpty()) {
            getViewBinding().edPhoneNumber.setError(getMyContext().getString(R.string.the_phone_number_is_required));
            error = +1;
        }
        if (getViewBinding().edPassword.getText().toString().isEmpty()) {
            getViewBinding().edPassword.setError(getMyContext().getString(R.string.this_fieled_is_required));
            error = +1;
        }

        return error == 0;
    }

    public void forgetPasswordClick() {
        getMyContext().startActivity(PhoneNumberActivity.newIntent(getMyContext(),
                PhoneNumberTypes.FORGET_PASSWORD.getValue()));
    }

    private void loginWithSocial(SocialLogin socialLogin) {
        getDataManager().getAuthService().getDataApi().loginWithSocial(socialLogin)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<RegisterResponse>(getMyContext(), true, new APICallBack<RegisterResponse>() {
                    @Override
                    public void onSuccess(RegisterResponse response) {
                        User user = response.getUser();
                        user.setToken(response.getJwt_token());
                        User.getInstance().setObjUser(user);
                        SessionManager.createUserLoginSession();
                        getDataManager().getAuthService().updateFirebaseToken(getMyContext(), true, new APICallBack() {
                            @Override
                            public void onSuccess(Object response) {
                                getBaseActivity().finishAffinity();
//                                getBaseActivity().startActivity(MainActivity.newIntent(getMyContext()));
                            }

                            @Override
                            public void onError(String error, int errorCode) {
                                showToast(error);
                            }
                        });
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        if (errorCode == 404) {
                            getBaseActivity().startActivity(PhoneNumberActivity.newIntent(getMyContext(), PhoneNumberTypes.REGISTER_SOCIAL.getValue()));
                        } else {
                            showErrorSocialDialog();
                        }
                    }
                }));

    }

    private void showErrorSocialDialog() {
        new OnLineDialog(getMyContext()) {
            @Override
            public void onPositiveButtonClicked() {
                dismiss();
                SessionManager.logoutUser();
            }

            @Override
            public void onNegativeButtonClicked() {

            }
        }.showConfirmationDialog(DialogTypes.OK,
                getMyContext().getResources().getString(R.string.error),
                getMyContext().getResources().getString(R.string.error_to_login_with_socail_please_try_again));
    }

}
