package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class ProductFragment extends BaseFragment<FragmentProductBinding, ProductViewModel>
        implements ProductNavigator, ActivityResultCallBack{

    private static final String TAG = ProductFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ProductViewModel mHomeViewModel;
    private FragmentProductBinding mViewBinding;


    public static ProductFragment newInstance() {
        Bundle args = new Bundle();
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        return true;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return this::callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    public ProductViewModel getViewModel() {
        mHomeViewModel = (ProductViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ProductViewModel.class, getViewDataBinding(), this);
        return mHomeViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }


    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mHomeViewModel.setUp();
    }

    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        mHomeViewModel.applyFilter();
    }

    @Override
    public int getRequestType() {
        return getArguments().getInt(AppConstants.BundleData.REQUEST_TYPE, 0);
    }
}