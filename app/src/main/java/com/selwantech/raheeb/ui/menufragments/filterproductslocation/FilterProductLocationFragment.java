package com.selwantech.raheeb.ui.menufragments.filterproductslocation;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.ViewModelProviderFactory;
import com.selwantech.raheeb.databinding.FragmentEmptyBinding;
import com.selwantech.raheeb.databinding.FragmentFilterProductsLocationBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.DataExample;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;


public class FilterProductLocationFragment extends BaseFragment<FragmentFilterProductsLocationBinding, FilterProductLocationViewModel> implements FilterProductLocationNavigator {

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
        return false;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return null;
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


}
