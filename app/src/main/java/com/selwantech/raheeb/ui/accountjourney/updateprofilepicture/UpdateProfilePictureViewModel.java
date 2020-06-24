package com.selwantech.raheeb.ui.accountjourney.updateprofilepicture;

import android.content.Context;
import android.net.Uri;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUpdateProfilePictureBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.enums.PickImageTypes;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.ui.dialog.PickImageFragmentDialog;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;

public class UpdateProfilePictureViewModel extends BaseViewModel<UpdateProfilePictureNavigator, FragmentUpdateProfilePictureBinding> {

    private Uri reportImageUri;

    public <V extends ViewDataBinding, N extends BaseNavigator> UpdateProfilePictureViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (UpdateProfilePictureNavigator) navigation, (FragmentUpdateProfilePictureBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {

    }

    public String userPicture() {
        return User.getInstance().getAvatar();
    }

    public void setReportImageUri(Uri reportImageUri) {
        this.reportImageUri = reportImageUri;
    }

    public void pickPictureClick() {
        PickImageFragmentDialog pickImageFragmentDialog = new PickImageFragmentDialog.Builder().build();
        pickImageFragmentDialog.setMethodCallBack(new PickImageFragmentDialog.methodClick() {
            @Override
            public void onMethodBack(int type) {
                if (type == PickImageTypes.GALLERY.getIntValue()) {
                    PickImageUtility.selectImage(getBaseActivity());
                } else {
                    PickImageUtility.TakePictureIntent(getBaseActivity());
                }
            }
        });
        pickImageFragmentDialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }

    public void onSaveClicked() {
        if (isValid()) {
            getDataManager().getAccountService().updateAvatar(getMyContext(), true,
                    GeneralFunction.getImageMultipart(reportImageUri.getPath(), "avatar"), new APICallBack<User>() {
                        @Override
                        public void onSuccess(User response) {
                            response.setToken(User.getInstance().getToken());
                            User.getInstance().setObjUser(response);
                            SessionManager.createUserLoginSession();
                            showSuccessDialog();
                        }

                        @Override
                        public void onError(String error, int errorCode) {
                            showErrorSnackBar(error);
                        }
                    });
        }
    }

    private void showSuccessDialog() {
        new OnLineDialog(getMyContext()) {
            @Override
            public void onPositiveButtonClicked() {
                dismiss();
                popUp();
            }

            @Override
            public void onNegativeButtonClicked() {
                dismiss();
            }
        }.showConfirmationDialog(DialogTypes.OK, getMyContext().getResources().getString(R.string.success),
                getMyContext().getResources().getString(R.string.upload_successful));
    }

    private void showErrorSnackBar(String error) {
        showSnackBar(getMyContext().getResources().getString(R.string.error),
                error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                    @Override
                    public void onActionClick(Snackbar snackbar) {
                        snackbar.dismiss();
                    }
                });
    }

    private boolean isValid() {
        int error = 0;
        if (reportImageUri == null) {
            showErrorSnackBar(getMyContext().getResources().getString(R.string.please_pick_the_id_picture));
            error++;
        }
        return error == 0;
    }

}
