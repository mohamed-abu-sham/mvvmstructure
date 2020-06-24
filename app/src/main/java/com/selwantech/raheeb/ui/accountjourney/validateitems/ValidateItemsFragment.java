package com.selwantech.raheeb.ui.accountjourney.validateitems;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentValidateListBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class ValidateItemsFragment extends BaseFragment<FragmentValidateListBinding, ValidateItemsViewModel>
        implements ValidateItemsNavigator, ActivityResultCallBack {

    private static final String TAG = ValidateItemsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ValidateItemsViewModel mOffersViewModel;
    private FragmentValidateListBinding mViewBinding;

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
        return R.layout.fragment_validate_list;
    }

    @Override
    public ValidateItemsViewModel getViewModel() {
        mOffersViewModel = (ValidateItemsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ValidateItemsViewModel.class, getViewDataBinding(), this);
        return mOffersViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.verify_account);
        mOffersViewModel.setUp();
    }

    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        mOffersViewModel.getTwitterAuthClient().onActivityResult(requestCode, resultCode, data);
    }

}
