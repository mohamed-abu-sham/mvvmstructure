package com.selwantech.raheeb.ui.auth.phonenumber;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUpdatePhoneBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class PhoneNumberFragment extends BaseFragment<FragmentUpdatePhoneBinding, PhoneNumberViewModel> {

    int type;
    @Inject
    ViewModelProviderFactory factory;
    private PhoneNumberViewModel mPhoneNumberViewModel;
    private FragmentUpdatePhoneBinding mViewBinding;

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, PhoneNumberFragment.class).putExtra("type", type);
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
        return R.layout.fragment_update_phone;
    }

    @Override
    public PhoneNumberViewModel getViewModel() {
        mPhoneNumberViewModel = (PhoneNumberViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(PhoneNumberViewModel.class, getViewDataBinding(), new Intent().putExtras(getArguments()));
        return mPhoneNumberViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, "", R.string.change_phone_number);
        type = getArguments().getInt("type", 0);
        mPhoneNumberViewModel.setType(type);
    }

}