package com.selwantech.raheeb.ui.menufragments.productstabsholder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductsTabsHolderBinding;
import com.selwantech.raheeb.enums.FilterProductResultsTypes;
import com.selwantech.raheeb.enums.ProductTypes;
import com.selwantech.raheeb.model.FilterProduct;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.menufragments.product.ProductFragment;
import com.selwantech.raheeb.utils.AppConstants;

public class ProductsHolderViewModel extends BaseViewModel<ProductsHolderNavigator, FragmentProductsTabsHolderBinding> {


    public <V extends ViewDataBinding, N extends BaseNavigator> ProductsHolderViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ProductsHolderNavigator) navigation, (FragmentProductsTabsHolderBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpTablayout();
    }

    public void setUpTablayout() {

        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 0, true);
        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 1, false);
        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 2, false);
        getViewBinding().tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#55ACEE"));
        getViewBinding().tabLayout.setTabTextColors(Color.parseColor("#7F8089"), Color.parseColor("#55ACEE"));
        getViewBinding().tabLayout.getTabAt(0).setText(R.string.all);
        getViewBinding().tabLayout.getTabAt(1).setText(R.string.pick_up);
        getViewBinding().tabLayout.getTabAt(2).setText(R.string.shipping);

        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager().getFragments().get(0).getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_products_tab, getItem(0)).commit();
        getViewBinding().tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentManager.beginTransaction().replace(R.id.fragment_products_tab, getItem(0)).commit();
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
        getNavigator().getChildFragment().popBackStack();
        ProductFragment fragment = new ProductFragment();
        switch (position) {
            case 0:
                FilterProduct.getInstance().setShipping(false);
                FilterProduct.getInstance().setPick_up(false);
                bundle.putInt(AppConstants.BundleData.REQUEST_TYPE, ProductTypes.ALL.getTypeValue());
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                FilterProduct.getInstance().setShipping(false);
                FilterProduct.getInstance().setPick_up(true);
                bundle.putInt(AppConstants.BundleData.REQUEST_TYPE, ProductTypes.PICKUP.getTypeValue());
                fragment.setArguments(bundle);
                return fragment;
            default:
                FilterProduct.getInstance().setShipping(true);
                FilterProduct.getInstance().setPick_up(false);
                bundle.putInt(AppConstants.BundleData.REQUEST_TYPE, ProductTypes.SHIPPING.getTypeValue());
                fragment.setArguments(bundle);
                return fragment;
        }
    }
    public void onFilterLocationClicked(){
        Bundle data = new Bundle();
        data.putInt("requestCode", FilterProductResultsTypes.LOCATION.getValue());
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_productsHolderFragment_to_filterProductLocationFragment);
    }

    public void onCategoryClicked(){
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_productsHolderFragment_to_categoryFragment);
    }
}
