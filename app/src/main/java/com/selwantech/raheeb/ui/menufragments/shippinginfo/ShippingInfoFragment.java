package com.selwantech.raheeb.ui.menufragments.shippinginfo;

import android.content.Context;

import com.google.android.gms.maps.SupportMapFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentShippingInfoBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class ShippingInfoFragment extends BaseFragment<FragmentShippingInfoBinding, ShippingInfoViewModel> implements ShippingInfoNavigator {

    private static final String TAG = ShippingInfoFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ShippingInfoViewModel mViewModel;
    private FragmentShippingInfoBinding mViewBinding;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.returnData();
    }

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
        return R.layout.fragment_shipping_info;
    }

    @Override
    public ShippingInfoViewModel getViewModel() {
        mViewModel = (ShippingInfoViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ShippingInfoViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.enter_shipping_info);
        mViewModel.setUp();
    }

    @Override
    public SupportMapFragment getChildManager() {
        return (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.search_place_map);
    }

}
