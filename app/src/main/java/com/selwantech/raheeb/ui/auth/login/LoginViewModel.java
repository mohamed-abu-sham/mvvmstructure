package com.selwantech.raheeb.ui.auth.login;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityLoginBinding;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.LoginObject;
import com.selwantech.raheeb.model.RegisterResponse;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.auth.phonenumber.PhoneNumberFragment;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.utils.LanguageUtils;
import com.selwantech.raheeb.utils.SnackViewBulider;
import com.selwantech.raheeb.utils.TwitterUtils;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import androidx.databinding.ViewDataBinding;

public class LoginViewModel extends BaseViewModel<LoginNavigator, ActivityLoginBinding> implements TwitterUtils.TwitterCallback {

    private static final String TAG = "LoginViewModel";
    protected static int GOOGLE_SIGN_IN = 1;

    TwitterUtils twitterUtils;



    public <V extends ViewDataBinding, N extends BaseNavigator> LoginViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (LoginNavigator) navigation, (ActivityLoginBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        twitterUtils = new TwitterUtils(getBaseActivity(), this::twitterUser);
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


    public void twitterClick() {
        twitterUtils.twitterClick();
    }


    @Override
    public void twitterUser(User user) {
        getDataManager().getAuthService().registerTwitterUser(getMyContext(),
                true, user, getNavigator().getInviteToken(), new APICallBack<RegisterResponse>() {
                    @Override
                    public void onSuccess(RegisterResponse response) {
                        User user = response.getUser();
                        user.setToken(response.getJwt_token());
                        User.getInstance().setObjUser(user);
                        SessionManager.createUserLoginSession();
                        getDataManager().getAuthService().reInit();
                        getBaseActivity().finishAffinity();
                        getBaseActivity().startActivity(MainActivity.newIntent(getMyContext()));
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

    public TwitterAuthClient getTwitterAuthClient() {
        return twitterUtils.getTwitterAuthClient();
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
        getMyContext().startActivity(PhoneNumberFragment.newIntent(getMyContext(),
                PhoneNumberTypes.FORGET_PASSWORD.getValue()));
    }
}
