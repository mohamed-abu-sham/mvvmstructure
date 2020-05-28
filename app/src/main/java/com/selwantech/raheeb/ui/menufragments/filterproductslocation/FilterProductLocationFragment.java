package com.selwantech.raheeb.ui.menufragments.filterproductslocation;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentFilterProductsLocationBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class FilterProductLocationFragment extends BaseFragment<FragmentFilterProductsLocationBinding,
        FilterProductLocationViewModel> implements FilterProductLocationNavigator, ActivityResultCallBack {

    private static final String TAG = FilterProductLocationFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private FilterProductLocationViewModel mViewModel;
    private FragmentFilterProductsLocationBinding mViewBinding;

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
        return true;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return this::callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_filter_products_location;
    }

    @Override
    public FilterProductLocationViewModel getViewModel() {
        mViewModel = (FilterProductLocationViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(FilterProductLocationViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar,TAG,
                R.string.location);
        mViewModel.setUp();
    }


    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                mViewModel.setLocation(data.getParcelableExtra(AppConstants.BundleData.ADDRESS));
            }
        }
    }
}
