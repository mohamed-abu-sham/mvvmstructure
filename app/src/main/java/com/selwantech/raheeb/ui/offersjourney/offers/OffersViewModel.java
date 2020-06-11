package com.selwantech.raheeb.ui.offersjourney.offers;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentOffersBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.offersjourney.buying.BuyingFragment;
import com.selwantech.raheeb.ui.offersjourney.favorite.FavoriteFragment;
import com.selwantech.raheeb.ui.offersjourney.selling.SellingFragment;

public class OffersViewModel extends BaseViewModel<OffersNavigator, FragmentOffersBinding> {

    SellingFragment sellingFragment = new SellingFragment();
    BuyingFragment buyingFragment = new BuyingFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();

    public <V extends ViewDataBinding, N extends BaseNavigator> OffersViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (OffersNavigator) navigation, (FragmentOffersBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpTablayout();
    }

    public void setUpTablayout() {

        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 0, true);
        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 1, false);
        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 2, false);
        getViewBinding().tabLayout.setSelectedTabIndicatorHeight(0);
        getViewBinding().tabLayout.setTabTextColors(Color.parseColor("#A8A8A8"), Color.parseColor("#55ACEE"));
        getViewBinding().tabLayout.getTabAt(0).setText(R.string.selling);
        getViewBinding().tabLayout.getTabAt(1).setText(R.string.buying);
        getViewBinding().tabLayout.getTabAt(2).setText(R.string.favorite_txt);

        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager().getFragments().get(0).getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_offers_tab, getItem(0)).commit();
        getViewBinding().tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentManager.beginTransaction().replace(R.id.fragment_offers_tab, getItem(tab.getPosition())).commit();
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
                sellingFragment.setArguments(bundle);
                return sellingFragment;
            case 1:
                buyingFragment.setArguments(bundle);
                return buyingFragment;
            default:
                favoriteFragment.setArguments(bundle);
                return favoriteFragment;
        }
    }


    public void onResume() {
        switch (getViewBinding().tabLayout.getTabAt(getViewBinding().tabLayout.getSelectedTabPosition()).getPosition()) {
            case 2:
                favoriteFragment.reloadData();
                break;
        }
    }
}
