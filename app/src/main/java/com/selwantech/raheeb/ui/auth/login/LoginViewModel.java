package com.selwantech.raheeb.ui.auth.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityLoginBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.DataExample;
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
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.LanguageUtils;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginNavigator, ActivityLoginBinding> {

    private static final String TAG = "LoginViewModel";
    protected static int GOOGLE_SIGN_IN = 1;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<DataExample>> dataExampleList;

    private LoginButton facebookLoginButton;
    private CallbackManager facebookCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private TwitterAuthClient twitterAuthClient;
    private TwitterLoginButton twitterLoginButton;

    public <V extends ViewDataBinding, N extends BaseNavigator> LoginViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (LoginNavigator) navigation, (ActivityLoginBinding) viewDataBinding);
        isLoading = new MutableLiveData<>();
        dataExampleList = new MutableLiveData<>();
        initFacebook();
        initGoogle();
    }

    @Override
    protected void setUp() {
        String lang = LanguageUtils.getLanguage(getMyContext());
        if (lang.equals("ar")) {
            getViewBinding().tvLanguage.setText(getMyContext().getResources().getString(R.string.arabic));
        } else {
            getViewBinding().tvLanguage.setText(getMyContext().getResources().getString(R.string.english));
        }

//        generatFCM();
        getViewBinding().linearLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLineDialog.showPopupLanguage(getBaseActivity());
            }
        });
    }

    private void generatFCM() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        SessionManager.saveFireBaseToken(token);
                    }
                });
    }

    public void signUpClick() {
        getMyContext().startActivity(PhoneNumberActivity.newIntent(getMyContext(), PhoneNumberTypes.REGISTER.getValue()));
    }

    public void loginClick() {
        if (isValidate()) {
            getDataManager().getAuthService().getDataApi().loginUser(getViewBinding().edPhoneNumber.getText().toString().trim(),
                    getViewBinding().edPassword.getText().toString())
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
                            getDataManager().getAuthService().reInit();
                            getDataManager().getAuthService().updateFirebaseToken(getMyContext(), true, new APICallBack() {
                                @Override
                                public void onSuccess(Object response) {
                                    getBaseActivity().finishAffinity();
//                                    getBaseActivity().startActivity(MainActivity.newIntent(getMyContext()));
                                }

                                @Override
                                public void onError(String error, int errorCode) {
                                    showToast(error);
                                }
                            });
                        }

                        @Override
                        public void onError(String error, int errorCode) {
                            showToast(error);
                        }
                    }));
        }
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

//  Google Login issues

    public void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getMyContext().getString(R.string.client_api_google))
                .requestEmail()
                .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(getMyContext(), gso);
    }

    public void googleClick() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getMyContext());
        if (account == null) {
            ((LoginActivity) getMyContext()).startActivityForResult(this.mGoogleSignInClient.getSignInIntent(), GOOGLE_SIGN_IN);
        } else {
            this.mGoogleSignInClient.signOut();
        }
    }

    protected void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            User user = User.getInstance();
            user.setName(account.getGivenName());
            user.setName(user.getUserName() + " " + account.getFamilyName());
            user.setEmail(account.getEmail());
            user.setAvatar(String.valueOf(account.getPhotoUrl()));
            user.setSocial_id(account.getId());

            SocialLogin socialLogin = new SocialLogin(account.getEmail(), account.getId(), account.getIdToken(), AppConstants.GOOGLE);
            loginWithSocial(socialLogin);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            ((LoginActivity) getMyContext()).showToast("signInResult:failed code=" + e.getStatusCode());
        }
    }

//  Facebook Login issues

    public void initFacebook() {
        FacebookSdk.sdkInitialize(getMyContext());
        this.facebookCallbackManager = CallbackManager.Factory.create();
        this.facebookLoginButton = new LoginButton(getMyContext());
        this.facebookLoginButton.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        this.facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        registerFacebookCallback();
    }

    public void registerFacebookCallback() {
        this.facebookLoginButton.registerCallback(this.facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accesstoken = loginResult.getAccessToken().getToken();
                GraphRequest g = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("face", object.toString());
                        User user = getUserFromFacebook(object);
                        SocialLogin socialLogin = new SocialLogin(user.getEmail(),
                                user.getSocial_id(), accesstoken, AppConstants.FACEBOOK);
                        loginWithSocial(socialLogin);
                    }
                });

                Bundle para = new Bundle();
                para.putString("fields", "id,first_name,last_name,email,gender");
                g.setParameters(para);
                g.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException error) {

            }

        });

    }

    public void facebookClick() {
        facebookLoginButton.performClick();
    }

    public User getUserFromFacebook(JSONObject object) {
        User user = User.getInstance();
        try {
            if (object.has("first_name")) {
                user.setName(object.getString("first_name"));
            }
            if (object.has("last_name")) {
                user.setName(user.getUserName() + " " + object.getString("last_name"));
            }
            if (object.has("email")) {
                user.setEmail(object.getString("email"));
            }

            if (object.has("id")) {
                user.setSocial_id(object.getString("id"));
            }
            URL imageURL = new URL("https://graph.facebook.com/" + user.getSocial_id() + "/picture?type=large");
            user.setAvatar(imageURL.toString());
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            return user;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return user;
        }
    }

    public CallbackManager getFacebookCallbackManager() {
        return facebookCallbackManager;
    }

}
