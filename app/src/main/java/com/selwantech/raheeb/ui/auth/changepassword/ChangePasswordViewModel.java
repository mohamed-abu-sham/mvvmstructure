package com.selwantech.raheeb.ui.auth.changepassword;

import android.content.Context;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentChangePasswordBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;

public class ChangePasswordViewModel extends BaseViewModel<ChangePasswordNavigator, FragmentChangePasswordBinding> {

    public <V extends ViewDataBinding, N extends BaseNavigator> ChangePasswordViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ChangePasswordNavigator) navigation, (FragmentChangePasswordBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {

    }

    public void changePasswordClicked() {
        if (isValid()) {
            getDataManager().getAuthService().updatePassword(getMyContext(), true,
                    getViewBinding().edNewPassword.getText().toString(),
                    getViewBinding().edConfirmNewPassword.getText().toString(),
                    getViewBinding().edOldPassword.getText().toString(), new APICallBack<String>() {
                        @Override
                        public void onSuccess(String response) {
                            new OnLineDialog(getMyContext()) {
                                @Override
                                public void onPositiveButtonClicked() {
                                    dismiss();
                                    popUp();
                                }

                                @Override
                                public void onNegativeButtonClicked() {

                                }
                            }.showConfirmationDialog(DialogTypes.OK, getMyContext().getResources().getString(R.string.update_successfully),
                                    getMyContext().getResources().getString(R.string.your_password_has_been_updated_successfully));
                        }

                        @Override
                        public void onError(String error, int errorCode) {
                            showSnackBar(getMyContext().getString(R.string.error),
                                    error, getMyContext().getResources().getString(R.string.OK),
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

    public boolean isValid() {
        int error = 0;

        if (getViewBinding().edOldPassword.getText().toString().isEmpty()) {
            error = +1;
            getViewBinding().edOldPassword.setError(getMyContext().getString(R.string.old_password_is_required));
        }

        if (getViewBinding().edNewPassword.getText().toString().isEmpty()) {
            error = +1;
            getViewBinding().edNewPassword.setError(getMyContext().getString(R.string.password_is_required));
        }

        if (!getViewBinding().edNewPassword.getText().toString().equals(getViewBinding().edConfirmNewPassword.getText().toString())) {
            error = +1;
            getViewBinding().edNewPassword.setError(getMyContext().getString(R.string.does_not_match));
            getViewBinding().edConfirmNewPassword.setError(getMyContext().getString(R.string.does_not_match));
        }
        return error == 0;
    }


}
