package com.selwantech.raheeb.ui.userprofilejourney.user_profile_details;

import android.content.Context;
import android.text.Html;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUserProfileDetailsBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

public class UserProfileDetailsViewModel extends BaseViewModel<UserProfileDetailsNavigator, FragmentUserProfileDetailsBinding> {

    ProductOwner productOwner;

    public <V extends ViewDataBinding, N extends BaseNavigator> UserProfileDetailsViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (UserProfileDetailsNavigator) navigation, (FragmentUserProfileDetailsBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        getUserProfile();
    }

    private void getUserProfile() {
        getDataManager().getUserService().getUser(getMyContext(), true, getNavigator().getUser().getId(), new APICallBack<ProductOwner>() {
            @Override
            public void onSuccess(ProductOwner response) {
                productOwner = response;
                getViewBinding().setData(response);
                getViewBinding().linearMain.setVisibility(View.VISIBLE);
                setTexts();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error,
                        getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }


    public void onFollowClicked() {
        if (!productOwner.isIsFollow()) {
            followUser();
        } else {
            unfollowUser();
        }
    }

    private void followUser() {
        getDataManager().getUserService().followUser(getMyContext(), true, productOwner.getId(), new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                productOwner.setFollow(true);
                productOwner.plusFollower();
                setTexts();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
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

    private void unfollowUser() {
        getDataManager().getUserService().unfollowUser(getMyContext(), true, productOwner.getId(), new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                productOwner.setFollow(false);
                productOwner.minusFollower();
                setTexts();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
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

    private void setTexts() {
        getViewBinding().tvFollowers.setText(Html.fromHtml(getFollowersText()));
        getViewBinding().tvFollowing.setText(Html.fromHtml(getFollowingText()));
    }

    private String getFollowersText() {
        StringBuilder followers = new StringBuilder();
        followers.append(GeneralFunction.textMultiColor(productOwner.getCountFollowers() + "", "#55ACEE"));
        followers.append(" ");
        followers.append(getMyContext().getResources().getString(R.string.followers));
        return followers.toString();
    }

    private String getFollowingText() {
        StringBuilder followers = new StringBuilder();
        followers.append(GeneralFunction.textMultiColor(productOwner.getCountFollowing() + "", "#55ACEE"));
        followers.append(" ");
        followers.append(getMyContext().getResources().getString(R.string.following));
        return followers.toString();
    }
}
