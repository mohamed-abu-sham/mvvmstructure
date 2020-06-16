package com.selwantech.raheeb.ui.auth.editprofile;

import android.content.Context;
import android.net.Uri;
import android.util.Patterns;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentEditProfileBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.enums.PickImageTypes;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.ProfileResponse;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.CustomObserverResponse;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.CustomUploadingDialog;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.ui.dialog.PickImageFragmentDialog;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.utils.ProgressRequestBody;
import com.selwantech.raheeb.utils.SnackViewBulider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditProfileViewModel extends BaseViewModel<EditProfileNavigator, FragmentEditProfileBinding>
        implements ProgressRequestBody.UploadCallbacks {

    CustomUploadingDialog customUploadingDialog;

    public <V extends ViewDataBinding, N extends BaseNavigator> EditProfileViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (EditProfileNavigator) navigation, (FragmentEditProfileBinding) viewDataBinding);
    }

    public boolean isValid() {
        int error = 0;

        if (!Patterns.EMAIL_ADDRESS.matcher(getViewBinding().edEmail.getText().toString().trim()).matches()) {
            error = +1;
            getViewBinding().edEmail.setError(getMyContext().getString(R.string.email_is_required));
        }

        if (getViewBinding().edUserName.getText().toString().isEmpty()) {
            error = +1;
            getViewBinding().edUserName.setError(getMyContext().getString(R.string.user_name_is_required));
        }
        return error == 0;
    }

    public void onBtnClick() {
        if (isValid()) {
            getDataManager().getAuthService().getDataApi().updateProfile(
                    getViewBinding().edEmail.getText().toString(),
                    getViewBinding().edUserName.getText().toString())
                    .toObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new CustomObserverResponse<ProfileResponse>(getMyContext(), true, new APICallBack<ProfileResponse>() {
                        @Override
                        public void onSuccess(ProfileResponse response) {
                            response.getUser().setToken(User.getObjUser().getToken());
                            User.getInstance().setObjUser(response.getUser());
                            SessionManager.createUserLoginSession();
//                            ((MainActivity) getMyContext()).setToolbarUserName();
                            new OnLineDialog(getMyContext()) {
                                @Override
                                public void onPositiveButtonClicked() {
                                    dismiss();
                                    Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment).popBackStack();
                                }

                                @Override
                                public void onNegativeButtonClicked() {

                                }
                            }.showConfirmationDialog(DialogTypes.OK, getMyContext().getResources().getString(R.string.update_successfully),
                                    getMyContext().getResources().getString(R.string.your_profile_has_been_updated_successfully));
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
                    }));
        }
    }

    public void changePasswordClicked() {
//        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
//                .navigate(R.id.action_editProfileFragment_to_changePasswordFragment);
    }


    @Override
    protected void setUp() {
        getViewBinding().edUserName.setText(User.getInstance().getName());
        getViewBinding().edEmail.setText(User.getInstance().getEmail());
        getViewBinding().edPhone.setText(User.getInstance().getPhone());
        if (User.getInstance().getAvatar() != null && !User.getInstance().getAvatar().isEmpty()) {
            GeneralFunction.loadImage(getMyContext(), User.getInstance().getAvatar(), getViewBinding().imgPicture);
        }
        customUploadingDialog = new CustomUploadingDialog(getMyContext());
//        if (User.getInstance().isSocial()) {
//            getViewBinding().edEmail.setEnabled(false);
//        }
    }

    public void uploadProfilePicture(Uri uri) {
        customUploadingDialog.showProgress();
        getDataManager().getAuthService().getDataApi().updateProfilePicture(GeneralFunction.getImageMultiPartWithProgress(uri.getPath(), "avatar", this))
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ProfileResponse>(getMyContext(), false,
                        new APICallBack<ProfileResponse>() {
                            @Override
                            public void onSuccess(ProfileResponse response) {
                                response.getUser().setToken(User.getInstance().getToken());
                                User.getInstance().setObjUser(response.getUser());
                                SessionManager.createUserLoginSession();
                                customUploadingDialog.setProgress(100);
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
                                customUploadingDialog.setProgress(100);
                            }
                        }));

    }

    public void updatePictureClick() {
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

    @Override
    public void onProgressUpdate(int percentage) {
        customUploadingDialog.setProgress(percentage);
    }

    @Override
    public void onError() {
        customUploadingDialog.setProgress(100);
    }

    @Override
    public void onFinish() {
        customUploadingDialog.setProgress(100);
    }
}
