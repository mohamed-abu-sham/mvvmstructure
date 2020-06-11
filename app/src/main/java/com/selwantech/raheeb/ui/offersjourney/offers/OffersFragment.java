package com.selwantech.raheeb.ui.offersjourney.offers;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentOffersBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class OffersFragment extends
        BaseFragment<FragmentOffersBinding, OffersViewModel>
        implements OffersNavigator {

    private static final String TAG = OffersFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private OffersViewModel mViewModel;
    private FragmentOffersBinding mViewBinding;

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
    public void onResume() {
        super.onResume();
        mViewModel.onResume();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_offers;
    }

    @Override
    public OffersViewModel getViewModel() {
        mViewModel = (OffersViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(OffersViewModel.class, getViewDataBinding(), this);
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
    public FragmentManager getChildFragment() {
        return getChildFragmentManager();
    }

}
