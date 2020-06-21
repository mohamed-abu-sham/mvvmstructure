package com.selwantech.raheeb.ui.auth.createpassword;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityCreatePasswordBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.CustomObserverResponse;
import com.selwantech.raheeb.ui.auth.login.LoginActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;

import androidx.databinding.ViewDataBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreatePasswordViewModel extends BaseViewModel<CreatePasswordNavigator, ActivityCreatePasswordBinding> {

    String token = "";

    public <V extends ViewDataBinding, N extends BaseNavigator> CreatePasswordViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (CreatePasswordNavigator) navigation, (ActivityCreatePasswordBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        token = getNavigator().getToken();
    }

    public boolean isValid() {
        int error = 0;
        if (getViewBinding().edNewPassword.getText().toString().isEmpty()) {
            error = +1;
            getViewBinding().edNewPassword.setError(getMyContext().getString(R.string.password_is_required));
        }

        if (!getViewBinding().edNewPassword.getText().toString().equals(getViewBinding().edConfirmPassword.getText().toString())) {
            error = +1;
            getViewBinding().edNewPassword.setError(getMyContext().getString(R.string.does_not_match));
            getViewBinding().edConfirmPassword.setError(getMyContext().getString(R.string.does_not_match));
        }
        return error == 0;
    }

    public void createPassword() {
        if (isValid()) {
            getDataManager().getAuthService().getDataApi().createPassword(getViewBinding().edNewPassword.getText().toString(),
                    getViewBinding().edConfirmPassword.getText().toString(), token)
                    .toObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CustomObserverResponse<String>(getMyContext(), true, new APICallBack<String>() {
                        @Override
                        public void onSuccess(String response) {
                            SessionManager.logoutUser();
                            new OnLineDialog(getMyContext()) {
                                @Override
                                public void onPositiveButtonClicked() {
                                    getBaseActivity().finishAffinity();
                                    getBaseActivity().startActivity(new Intent(getMyContext(),
                                            LoginActivity.class));
                                }

                                @Override
                                public void onNegativeButtonClicked() {

                                }
                            }.showConfirmationDialog(DialogTypes.OK, getMyContext().getString(R.string.update_successfully),
                                    getMyContext().getResources().getString(R.string.you_can_login_now));

                        }

                        @Override
                        public void onError(String error, int errorCode) {
                            showToast(error);
                        }
                    }));
        }
    }


}
