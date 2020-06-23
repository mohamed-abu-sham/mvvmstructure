package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.productstabsholder;

import android.content.Context;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductsTabsHolderBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentManager;


public class ProductsHolderFragment extends
        BaseFragment<FragmentProductsTabsHolderBinding, ProductsHolderViewModel>
        implements ProductsHolderNavigator {

    private static final String TAG = ProductsHolderFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ProductsHolderViewModel mViewModel;
    private FragmentProductsTabsHolderBinding mViewBinding;


    public static ProductsHolderFragment newInstance() {
        Bundle args = new Bundle();
        ProductsHolderFragment fragment = new ProductsHolderFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public boolean hideBottomSheet() {
        return false;
    }

    @Override
    public boolean isNeedActivityResult() {
        return false;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_products_tabs_holder;
    }

    @Override
    public ProductsHolderViewModel getViewModel() {
        mViewModel = (ProductsHolderViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ProductsHolderViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mViewModel.setUp();
        setupOnBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupOnBackPressed();
        mViewModel.onResume();
    }

    private void setupOnBackPressed() {
        getBaseActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (!mViewBinding.edSearch.getText().toString().isEmpty() || mViewBinding.edSearch.isFocused()) {
                    mViewModel.onCancelSearchClicked();
                } else {
                    getBaseActivity().finishAffinity();
                    this.setEnabled(false);
                }

            }
        });
    }
    @Override
    public FragmentManager getChildFragment() {
        return getChildFragmentManager();
    }

}
