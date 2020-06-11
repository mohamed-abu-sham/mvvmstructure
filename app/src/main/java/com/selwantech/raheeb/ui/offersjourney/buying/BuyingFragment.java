package com.selwantech.raheeb.ui.offersjourney.buying;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentBuyingBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class BuyingFragment extends BaseFragment<FragmentBuyingBinding, BuyingViewModel>
        implements BuyingNavigator {

    private static final String TAG = BuyingFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private BuyingViewModel mHomeViewModel;
    private FragmentBuyingBinding mViewBinding;

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
        return R.layout.fragment_buying;
    }

    @Override
    public BuyingViewModel getViewModel() {
        mHomeViewModel = (BuyingViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(BuyingViewModel.class, getViewDataBinding(), this);
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

}
