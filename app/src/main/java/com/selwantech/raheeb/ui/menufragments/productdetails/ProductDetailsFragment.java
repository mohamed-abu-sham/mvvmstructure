package com.selwantech.raheeb.ui.menufragments.productdetails;

import android.content.Context;

import com.google.android.gms.maps.SupportMapFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductDetailsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class ProductDetailsFragment extends BaseFragment<FragmentProductDetailsBinding, ProductDetailsViewModel> implements ProductDetailsNavigator {

    private static final String TAG = ProductDetailsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ProductDetailsViewModel mViewModel;
    private FragmentProductDetailsBinding mViewBinding;

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public boolean hideBottomSheet() {
        return true;
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
        return R.layout.fragment_product_details;
    }

    @Override
    public ProductDetailsViewModel getViewModel() {
        mViewModel = (ProductDetailsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ProductDetailsViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        if (getProductId() == -1) {
            mViewBinding.setData(getProduct());
        }
        mViewModel.setUp();
    }

    @Override
    public Product getProduct() {
        return (Product) getArguments().getSerializable(AppConstants.BundleData.PRODUCT);
    }

    @Override
    public SupportMapFragment getChildManager() {
        return (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.search_place_map);
    }

    @Override
    public int getProductId() {
        return getArguments().getInt(AppConstants.BundleData.PRODUCT_ID, -1);
    }

}
