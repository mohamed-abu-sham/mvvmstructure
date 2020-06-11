package com.selwantech.raheeb.ui.userprofilejourney.user_profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUserProfileBinding;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.userprofilejourney.myoffers.MyOffersFragment;
import com.selwantech.raheeb.ui.userprofilejourney.user_profile_details.UserProfileDetailsFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

public class UserProfileViewModel extends BaseViewModel<UserProfileNavigator, FragmentUserProfileBinding> {


    ProductOwner productOwner;

    public <V extends ViewDataBinding, N extends BaseNavigator> UserProfileViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (UserProfileNavigator) navigation, (FragmentUserProfileBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        if (getNavigator().getUserId() == -1) {
            productOwner = getNavigator().getProductOwner();
            setUpTablayout();
        } else {
            getUser();
        }
    }

    private void getUser() {
        getDataManager().getUserService().getUser(getMyContext(), true, getNavigator().getUserId(), new APICallBack<ProductOwner>() {
            @Override
            public void onSuccess(ProductOwner response) {
                productOwner = response;
                getViewBinding().setData(productOwner);
                setUpTablayout();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error,
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

    public void setUpTablayout() {

        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 0, true);
        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 1, false);
//        getViewBinding().tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#55ACEE"));
        getViewBinding().tabLayout.setSelectedTabIndicatorHeight(0);
        getViewBinding().tabLayout.setTabTextColors(Color.parseColor("#A8A8A8"), Color.parseColor("#55ACEE"));
        getViewBinding().tabLayout.getTabAt(0).setText(R.string.profile);
        getViewBinding().tabLayout.getTabAt(1).setText(R.string.my_offers);

        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager().getFragments().get(0).getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_profile_tab, getItem(0)).commit();
        getViewBinding().tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentManager.beginTransaction().replace(R.id.fragment_profile_tab, getItem(tab.getPosition())).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                UserProfileDetailsFragment userProfileDetailsFragment = new UserProfileDetailsFragment();
                bundle.putSerializable(AppConstants.BundleData.PRODUCT_OWNER, productOwner);
                userProfileDetailsFragment.setArguments(bundle);
                return userProfileDetailsFragment;
            default:
                MyOffersFragment myOffersFragment = new MyOffersFragment();
                bundle.putSerializable(AppConstants.BundleData.PRODUCT_OWNER, productOwner);
                myOffersFragment.setArguments(bundle);
                return myOffersFragment;
        }
    }

    public void onBackClicked() {
        popUp();
    }

    public void onShareClicked() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getMyContext().getResources().getString(R.string.app_name));
            String shareMessage = "\n" + getMyContext().getResources().getString(R.string.share_this_product_with) + "\n\n";
            shareMessage = shareMessage + "http://raheeb.selwantech.tech/user?id=" + productOwner.getId() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            getBaseActivity().startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void onReportClicked() {

    }

}
