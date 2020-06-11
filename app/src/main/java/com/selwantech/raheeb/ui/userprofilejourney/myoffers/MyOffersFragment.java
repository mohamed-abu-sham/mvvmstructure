package com.selwantech.raheeb.ui.userprofilejourney.myoffers;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentMyOffersBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class MyOffersFragment extends BaseFragment<FragmentMyOffersBinding, MyOffersViewModel>
        implements MyOffersNavigator {

    private static final String TAG = MyOffersFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private MyOffersViewModel mHomeViewModel;
    private FragmentMyOffersBinding mViewBinding;


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
        return R.layout.fragment_my_offers;
    }

    @Override
    public MyOffersViewModel getViewModel() {
        mHomeViewModel = (MyOffersViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(MyOffersViewModel.class, getViewDataBinding(), this);
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
    public ProductOwner getUser() {
        return (ProductOwner) getArguments().getSerializable(AppConstants.BundleData.PRODUCT_OWNER);
    }

}
