package com.selwantech.raheeb.ui.offersjourney.sell;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSellBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.Selling;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class SellFragment extends BaseFragment<FragmentSellBinding, SellViewModel> implements SellNavigator {

    private static final String TAG = SellFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private SellViewModel mViewModel;
    private FragmentSellBinding mViewBinding;


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
        return R.layout.fragment_sell;
    }

    @Override
    public SellViewModel getViewModel() {
        mViewModel = (SellViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(SellViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.rate_the_buyer);
        mViewBinding.setData(getSellingItem());
        mViewModel.setUp();
    }

    @Override
    public Selling getSellingItem() {
        return (Selling) getArguments().getSerializable(AppConstants.BundleData.SELLING_ITEM);
    }
}
