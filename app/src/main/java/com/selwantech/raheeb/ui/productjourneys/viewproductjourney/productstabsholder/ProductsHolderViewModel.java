package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.productstabsholder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductsTabsHolderBinding;
import com.selwantech.raheeb.enums.FilterProductResultsTypes;
import com.selwantech.raheeb.enums.ProductTypes;
import com.selwantech.raheeb.model.FilterProduct;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.FilterByFragmentDialog;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.ui.productjourneys.viewproductjourney.product.ProductFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

public class ProductsHolderViewModel extends BaseViewModel<ProductsHolderNavigator, FragmentProductsTabsHolderBinding> {

    ProductFragment fragmentAll = new ProductFragment();
    ProductFragment fragmentPickUP = new ProductFragment();
    ProductFragment fragmentShipping = new ProductFragment();

    public <V extends ViewDataBinding, N extends BaseNavigator> ProductsHolderViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ProductsHolderNavigator) navigation, (FragmentProductsTabsHolderBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpTablayout();
        getViewBinding().edSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    showSearchTools(true);
                }

                return false;
            }
        });
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
                fragmentManager.beginTransaction().replace(R.id.fragment_products_tab, getItem(tab.getPosition())).commit();
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
//        getNavigator().getChildFragment().popBackStack();
        switch (position) {
            case 0:
                FilterProduct.getInstance().setShipping(false);
                FilterProduct.getInstance().setPick_up(false);
                bundle.putInt(AppConstants.BundleData.REQUEST_TYPE, ProductTypes.ALL.getTypeValue());
                fragmentAll.setArguments(bundle);
                return fragmentAll;
            case 1:
                FilterProduct.getInstance().setShipping(false);
                FilterProduct.getInstance().setPick_up(true);
                bundle.putInt(AppConstants.BundleData.REQUEST_TYPE, ProductTypes.PICKUP.getTypeValue());
                fragmentPickUP.setArguments(bundle);
                return fragmentPickUP;
            default:
                FilterProduct.getInstance().setShipping(true);
                FilterProduct.getInstance().setPick_up(false);
                bundle.putInt(AppConstants.BundleData.REQUEST_TYPE, ProductTypes.SHIPPING.getTypeValue());
                fragmentShipping.setArguments(bundle);
                return fragmentShipping;
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

    public void showSearchTools(boolean showSearchTools) {
        getViewBinding().imgSearch.setVisibility(showSearchTools ? View.VISIBLE : View.GONE);
        getViewBinding().imgCancelSearch.setVisibility(showSearchTools ? View.VISIBLE : View.GONE);
        getViewBinding().imgLocation.setVisibility(!showSearchTools ? View.VISIBLE : View.GONE);
        getViewBinding().imgCategory.setVisibility(!showSearchTools ? View.VISIBLE : View.GONE);
    }

    public void onCancelSearchClicked() {
        getBaseActivity().hideKeyboard();
        getViewBinding().edSearch.setText("");
        getViewBinding().edSearch.clearFocus();
        showSearchTools(false);
        if (FilterProduct.getInstance().getTitle() != null &&
                !FilterProduct.getInstance().getTitle().isEmpty()) {
            FilterProduct.getInstance().setTitle("");
            applyFilter();
        }
    }

    public void onSearchClicked() {
        getBaseActivity().hideKeyboard();
        getViewBinding().edSearch.clearFocus();
        if (isSearchValid()) {
            showSearchTools(false);
            FilterProduct.getInstance().setTitle(getViewBinding().edSearch.getText().toString());
            applyFilter();
        }
    }

    public void onFilterByClicked() {
        FilterByFragmentDialog dialog = new FilterByFragmentDialog.Builder().build();
        dialog.setMethodCallBack(new FilterByFragmentDialog.FilterByCallBack() {
            @Override
            public void callBack() {
                applyFilter();
            }
        });
        dialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }

    private void applyFilter() {
        ((MainActivity) getBaseActivity()).onActivityResultFromFragment(
                FilterProductResultsTypes.SEARCH.getValue(), Activity.RESULT_OK, null);
    }

    private boolean isSearchValid() {
        if (getViewBinding().edSearch.getText().toString().trim().isEmpty()) {
            showSnackBar(getMyContext().getResources().getString(R.string.error),
                    getMyContext().getResources().getString(R.string.please_type_your_search_text),
                    getMyContext().getResources().getString(R.string.ok),
                    new SnackViewBulider.SnackbarCallback() {
                        @Override
                        public void onActionClick(Snackbar snackbar) {
                            snackbar.dismiss();
                        }
                    });
            return false;
        }
        return true;
    }

    public void onResume() {
        switch (getViewBinding().tabLayout.getTabAt(getViewBinding().tabLayout.getSelectedTabPosition()).getPosition()) {
            case 0:
                fragmentAll.onResume();
                break;
            case 1:
                fragmentPickUP.onResume();
                break;
            case 2:
                fragmentShipping.onResume();
                break;
        }
    }
}
