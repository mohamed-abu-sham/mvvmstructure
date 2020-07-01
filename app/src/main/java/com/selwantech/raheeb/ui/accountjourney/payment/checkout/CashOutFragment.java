package com.selwantech.raheeb.ui.accountjourney.payment.checkout;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentCashOutBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class CashOutFragment extends BaseFragment<FragmentCashOutBinding, CashOutViewModel> implements CashOutNavigator {

    private static final String TAG = CashOutFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private CashOutViewModel mViewModel;
    private FragmentCashOutBinding mViewBinding;


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
        return R.layout.fragment_cash_out;
    }

    @Override
    public CashOutViewModel getViewModel() {
        mViewModel = (CashOutViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(CashOutViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.cash_out);
        mViewModel.setUp();
    }

}
