package com.selwantech.raheeb.ui.accountjourney.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAccountBinding;
import com.selwantech.raheeb.databinding.FragmentUserProfileBinding;
import com.selwantech.raheeb.enums.PickImageTypes;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.auth.login.LoginActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.CustomUploadingDialog;
import com.selwantech.raheeb.ui.dialog.PickImageFragmentDialog;
import com.selwantech.raheeb.ui.userprofilejourney.myoffers.MyOffersFragment;
import com.selwantech.raheeb.ui.userprofilejourney.user_profile_details.UserProfileDetailsFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.utils.ProgressRequestBody;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

public class AccountViewModel extends BaseViewModel<AccountNavigator, FragmentAccountBinding>
        implements ProgressRequestBody.UploadCallbacks {


    CustomUploadingDialog customUploadingDialog;
    MutableLiveData<User> user = new MutableLiveData<>();

    public <V extends ViewDataBinding, N extends BaseNavigator> AccountViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (AccountNavigator) navigation, (FragmentAccountBinding) viewDataBinding);

    }


    @Override
    protected void setUp() {
        user.postValue(User.getInstance());
        setTexts();
        customUploadingDialog = new CustomUploadingDialog(getMyContext());
      //  getProfile();
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void onLogoutClicked(){
        getDataManager().getAuthService().logout(getMyContext(), new APICallBack() {
            @Override
            public void onSuccess(Object response) {
                SessionManager.logoutUser();
                getBaseActivity().finishAffinity();
                getBaseActivity().startActivity(LoginActivity.newIntent(getMyContext()));
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        getMyContext().getResources().getString(R.string.please_try_again),
                        getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });

    }

    public void onShareClicked() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getMyContext().getResources().getString(R.string.app_name));
            String shareMessage = "\n" + getMyContext().getResources().getString(R.string.share_this_product_with) + "\n\n";
            shareMessage = shareMessage + "http://raheeb.selwantech.tech/user?id=" + User.getInstance().getUserID() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            getBaseActivity().startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void onReportClicked() {

    }

    public void onHelpClicked(){
        Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
        .navigate(R.id.action_nav_account_to_helpFragment);
    }

    public void onFollowingClicked(){
        Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
                .navigate(R.id.action_nav_account_to_followingFragment);
    }

    public void onFollowersClicked(){
        Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
                .navigate(R.id.action_nav_account_to_followersFragment);
    }

    public void onAddImageClicked() {
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

    public void setImage(String image) {
        updateImage(image);
    }

    public void getProfile() {
        getDataManager().getAccountService().getProfile(getMyContext(), true, new APICallBack<User>() {
                    @Override
                    public void onSuccess(User response) {
                        response.setToken(User.getInstance().getToken());
                        User.getInstance().setObjUser(response);
                        SessionManager.createUserLoginSession();

//                        User user1 =new User();
//                        user1.setName("llll");
//                        user1.setCount_followers(10);

                        user.postValue(User.getInstance());
                        setTexts();
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
    private void updateImage(String image) {
        customUploadingDialog.showProgress();
        getDataManager().getAccountService().updateAvatar(getMyContext(), false,
                GeneralFunction.getImageMultiPartWithProgress(image, "avatar", this), new APICallBack<User>() {
            @Override
            public void onSuccess(User response) {
                response.setToken(User.getInstance().getToken());
                User.getInstance().setObjUser(response);
                SessionManager.createUserLoginSession();
                GeneralFunction.loadImage(getMyContext(),User.getInstance().getAvatar(),getViewBinding().imgProfile);
                customUploadingDialog.setProgress(100);
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
                customUploadingDialog.setProgress(100);
            }
        });
    }

    private void setTexts() {
        getViewBinding().tvFollowers.setText(Html.fromHtml(getFollowersText()));
        getViewBinding().tvFollowing.setText(Html.fromHtml(getFollowingText()));
    }

    private String getFollowersText() {
        StringBuilder followers = new StringBuilder();
        followers.append(GeneralFunction.textMultiColor(User.getInstance().getCount_followers() + "", "#55ACEE"));
        followers.append(" ");
        followers.append(getMyContext().getResources().getString(R.string.followers));
        return followers.toString();
    }

    private String getFollowingText() {
        StringBuilder followers = new StringBuilder();
        followers.append(GeneralFunction.textMultiColor(User.getInstance().getCount_following() + "", "#55ACEE"));
        followers.append(" ");
        followers.append(getMyContext().getResources().getString(R.string.following));
        return followers.toString();
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
