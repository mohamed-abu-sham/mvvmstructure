package com.selwantech.raheeb.ui.accountjourney.updateemail;

import android.content.Context;
import android.util.Patterns;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUpdateEmailBinding;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;

public class UpdateEmailViewModel extends BaseViewModel<UpdateEmailNavigator, FragmentUpdateEmailBinding> {

    int type;

    public <V extends ViewDataBinding, N extends BaseNavigator> UpdateEmailViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (UpdateEmailNavigator) navigation, (FragmentUpdateEmailBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {

    }

    public void updateEmailClicked() {
        if (isValidate()) {
            updateEmail();
        }
    }

    public void updateEmail() {
        getDataManager().getAuthService().updateEmail(getMyContext(), true, getViewBinding().edEmail.getText().toString(), new APICallBack<User>() {
            @Override
            public void onSuccess(User response) {
                response.setToken(User.getInstance().getToken());
                User.getInstance().setObjUser(response);
                SessionManager.createUserLoginSession();
                popUp();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }


    private boolean isValidate() {
        if (getViewBinding().edEmail.getText().toString().trim().isEmpty()) {
            getViewBinding().edEmail.setError(getMyContext().getString(R.string.please_fill_your_email));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(getViewBinding().edEmail.getText().toString()).matches()) {
            getViewBinding().edEmail.setError(getMyContext().getString(R.string.please_enter_valid_email));
            return false;
        }
        return true;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String email() {
        return User.getInstance().getEmail() != null &&
                User.getInstance().getEmail().isEmpty() ? "" : User.getInstance().getEmail();
    }
}
