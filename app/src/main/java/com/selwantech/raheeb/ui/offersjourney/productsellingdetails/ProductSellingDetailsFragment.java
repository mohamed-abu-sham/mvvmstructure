package com.selwantech.raheeb.ui.offersjourney.productsellingdetails;

import android.content.Context;

import com.google.android.gms.maps.SupportMapFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductDetailsBinding;
import com.selwantech.raheeb.databinding.FragmentProductSellingDetailsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.model.Selling;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class ProductSellingDetailsFragment extends BaseFragment<FragmentProductSellingDetailsBinding, ProductSellingDetailsViewModel> implements ProductSellingDetailsNavigator {

    private static final String TAG = ProductSellingDetailsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ProductSellingDetailsViewModel mViewModel;
    private FragmentProductSellingDetailsBinding mViewBinding;

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
        return R.layout.fragment_product_selling_details;
    }

    @Override
    public ProductSellingDetailsViewModel getViewModel() {
        mViewModel = (ProductSellingDetailsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ProductSellingDetailsViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mViewBinding.setData(getProduct());
        mViewModel.setUp();
    }

    @Override
    public Selling getProduct() {
        return (Selling) getArguments().getSerializable(AppConstants.BundleData.SELLING_ITEM);
    }

}
