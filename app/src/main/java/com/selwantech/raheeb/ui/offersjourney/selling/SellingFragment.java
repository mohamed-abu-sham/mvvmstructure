package com.selwantech.raheeb.ui.offersjourney.selling;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSellingBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class SellingFragment extends BaseFragment<FragmentSellingBinding, SellingViewModel>
        implements SellingNavigator {

    private static final String TAG = SellingFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private SellingViewModel mHomeViewModel;
    private FragmentSellingBinding mViewBinding;


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
        return R.layout.fragment_selling;
    }

    @Override
    public SellingViewModel getViewModel() {
        mHomeViewModel = (SellingViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(SellingViewModel.class, getViewDataBinding(), this);
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
