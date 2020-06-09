package com.selwantech.raheeb.ui.productjourneys.createproductjourney.addprice;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductPriceBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class AddPriceFragment extends BaseFragment<FragmentAddProductPriceBinding, AddPriceViewModel> implements AddPriceNavigator {

    private static final String TAG = AddPriceFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private AddPriceViewModel mViewModel;
    private FragmentAddProductPriceBinding mViewBinding;


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
        return R.layout.fragment_add_product_price;
    }

    @Override
    public AddPriceViewModel getViewModel() {
        mViewModel = (AddPriceViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(AddPriceViewModel.class, getViewDataBinding(), this);
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
    }

    @Override
    public Post getPost() {
        return (Post) getArguments().getSerializable(AppConstants.BundleData.POST);
    }

    public Post onNextClicked() {
        return mViewModel.returnData();
    }

}
