package com.selwantech.raheeb.ui.accountjourney.account;

import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAccountBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.auth.chooseusertype.ChooseUserTypeActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

public class AccountViewModel extends BaseViewModel<AccountNavigator, FragmentAccountBinding> {


    MutableLiveData<User> user = new MutableLiveData<>();

    public <V extends ViewDataBinding, N extends BaseNavigator> AccountViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (AccountNavigator) navigation, (FragmentAccountBinding) viewDataBinding);

    }


    @Override
    protected void setUp() {
        user.postValue(User.getInstance());
        setTexts();
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
                getBaseActivity().startActivity(ChooseUserTypeActivity.newIntent(getMyContext(), ""));
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

    public void onValidateItemsClicked() {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_nav_account_to_validateItemsFragment);
    }

    public void onInviteClicked() {
        getDataManager().getAccountService().getInviteToken(getMyContext(), true, new APICallBack<VerifyPhoneResponse>() {
            @Override
            public void onSuccess(VerifyPhoneResponse response) {
                sendInvite(response.getToken());
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error), error,
                        getMyContext().getResources().getString(R.string.ok),
                        new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }

    public void sendInvite(String inviteToken) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getMyContext().getResources().getString(R.string.app_name));
            String shareMessage = "\n" + getMyContext().getResources().getString(R.string.share_this_product_with) + "\n\n";
            shareMessage = shareMessage + "http://raheeb.selwantech.tech/invite?token=" + inviteToken + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            getBaseActivity().startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void onHelpClicked(){
        Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
        .navigate(R.id.action_nav_account_to_helpFragment);
    }

    public void onTwitterFriendsClicked() {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_nav_account_to_twitterFriendsFragment);
    }

    public void onFollowingClicked(){
        Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
                .navigate(R.id.action_nav_account_to_followingFragment);
    }

    public void onFollowersClicked(){
        Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
                .navigate(R.id.action_nav_account_to_followersFragment);
    }

    public void onAccountSettings() {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_nav_account_to_settingsFragment);
    }

    public void onPaymentClicked() {
//        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
//                .navigate(R.id.action_nav_account_to_walletFragment);
    }


    public void getProfile() {
        getDataManager().getAccountService().getProfile(getMyContext(), true, new APICallBack<User>() {
                    @Override
                    public void onSuccess(User response) {
                        response.setToken(User.getInstance().getToken());
                        User.getInstance().setObjUser(response);
                        SessionManager.createUserLoginSession();
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


}
