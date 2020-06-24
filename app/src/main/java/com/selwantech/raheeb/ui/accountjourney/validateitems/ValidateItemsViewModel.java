package com.selwantech.raheeb.ui.accountjourney.validateitems;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentValidateListBinding;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.interfaces.RecyclerClickNoData;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.ValidateItem;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.ValidateItemsAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;
import com.selwantech.raheeb.utils.TwitterUtils;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

public class ValidateItemsViewModel extends BaseViewModel<ValidateItemsNavigator, FragmentValidateListBinding>
        implements RecyclerClickNoData, TwitterUtils.TwitterCallbackToConnect {

    ValidateItemsAdapter validateItemsAdapter;
    TwitterUtils twitterUtils;
    public <V extends ViewDataBinding, N extends BaseNavigator> ValidateItemsViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ValidateItemsNavigator) navigation, (FragmentValidateListBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        twitterUtils = new TwitterUtils(getBaseActivity(), this::twitterUser, false);
        setUpRecycler();
        getData();
    }

    private void setUpRecycler() {
        getViewBinding().recyclerView.setLayoutManager(new GridLayoutManager(getMyContext(), 3));
        validateItemsAdapter = new ValidateItemsAdapter(getMyContext(), this::onClick);
        getViewBinding().recyclerView.setAdapter(validateItemsAdapter);

    }

    private void getData() {
        ArrayList<ValidateItem> validateItems = new ArrayList<>();
        validateItems.add(new ValidateItem(R.drawable.ic_verified, R.color.color_dark_blue, R.string.trusted, User.getInstance().isIs_valid()));
        validateItems.add(new ValidateItem(R.drawable.ic_phone, R.color.color_green, R.string.phone,
                User.getInstance().getPhone() != null && !User.getInstance().getPhone().isEmpty()));
        validateItems.add(new ValidateItem(R.drawable.ic_camera_white, R.color.color_image_validate, R.string.image,
                User.getInstance().getAvatar() != null && !User.getInstance().getAvatar().isEmpty()));
        validateItems.add(new ValidateItem(R.drawable.ic_email, R.color.color_gray, R.string.email,
                User.getInstance().getEmail() != null && !User.getInstance().getEmail().isEmpty()));
        validateItems.add(new ValidateItem(R.drawable.ic_twitter, R.color.colorPrimaryDark, R.string.twitter,
                User.getInstance().getLogin_with().equals("twitter")));

        validateItemsAdapter.addItems(validateItems);
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_validateItemsFragment_to_updateIDFragment);
                break;
            case 1:
                Bundle data = new Bundle();
                data.putInt(AppConstants.BundleData.TYPE, PhoneNumberTypes.CHANGE_PHONE_NUMBER.getValue());
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.phoneNumberFragment, data);
                break;
            case 2:
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.updateProfilePictureFragment);
                break;
            case 3:
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.updateEmailFragment);
                break;
            case 4:
                if (!User.getInstance().getLogin_with().equals(AppConstants.LOGGED_IN_TYPE.TWITTER)) {
                    connectWithTwitter();
                }
                break;

        }
    }

    private void connectWithTwitter() {
        twitterUtils.twitterClick();
    }

    @Override
    public void twitterUser(String userID) {
        getDataManager().getAuthService().connectTwitterUser(getMyContext(),
                true, userID, new APICallBack<User>() {
                    @Override
                    public void onSuccess(User response) {
                        response.setToken(User.getInstance().getToken());
                        User.getInstance().setObjUser(response);
                        SessionManager.createUserLoginSession();
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
}
