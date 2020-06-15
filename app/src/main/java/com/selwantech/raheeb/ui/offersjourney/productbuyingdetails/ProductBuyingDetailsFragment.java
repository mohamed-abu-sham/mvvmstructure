package com.selwantech.raheeb.ui.offersjourney.productbuyingdetails;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductBuyingDetailsBinding;
import com.selwantech.raheeb.databinding.FragmentProductSellingDetailsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.model.Selling;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class ProductBuyingDetailsFragment extends BaseFragment<FragmentProductBuyingDetailsBinding, ProductBuyingDetailsViewModel> implements ProductBuyingDetailsNavigator {

    private static final String TAG = ProductBuyingDetailsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ProductBuyingDetailsViewModel mViewModel;
    private FragmentProductBuyingDetailsBinding mViewBinding;

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
        return R.layout.fragment_product_buying_details;
    }

    @Override
    public ProductBuyingDetailsViewModel getViewModel() {
        mViewModel = (ProductBuyingDetailsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ProductBuyingDetailsViewModel.class, getViewDataBinding(), this);
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
    public Product getProduct() {
        return (Product) getArguments().getSerializable(AppConstants.BundleData.PRODUCT);
    }

}
