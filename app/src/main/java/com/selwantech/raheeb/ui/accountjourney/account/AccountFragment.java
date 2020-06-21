package com.selwantech.raheeb.ui.accountjourney.account;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAccountBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class AccountFragment extends BaseFragment<FragmentAccountBinding, AccountViewModel>
        implements AccountNavigator {

    private static final String TAG = AccountFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private AccountViewModel mViewModel;
    private FragmentAccountBinding mViewBinding;

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getProfile();
    }

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
        return R.layout.fragment_account;
    }

    @Override
    public AccountViewModel getViewModel() {
        mViewModel = (AccountViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(AccountViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
//        mViewBinding.setData(User.getInstance());
        mViewModel.setUp();
    }


}
