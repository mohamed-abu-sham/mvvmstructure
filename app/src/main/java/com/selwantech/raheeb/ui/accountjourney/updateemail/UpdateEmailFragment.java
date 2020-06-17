package com.selwantech.raheeb.ui.accountjourney.updateemail;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUpdateEmailBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class UpdateEmailFragment extends BaseFragment<FragmentUpdateEmailBinding, UpdateEmailViewModel>
        implements UpdateEmailNavigator {

    int type;
    @Inject
    ViewModelProviderFactory factory;
    private UpdateEmailViewModel mPhoneNumberViewModel;
    private FragmentUpdateEmailBinding mViewBinding;

    public static Intent newIntent(Context context, int type) {
        return new Intent(context, UpdateEmailFragment.class).putExtra("type", type);
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
        return R.layout.fragment_update_email;
    }

    @Override
    public UpdateEmailViewModel getViewModel() {
        mPhoneNumberViewModel = (UpdateEmailViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(UpdateEmailViewModel.class, getViewDataBinding(), this);
        return mPhoneNumberViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, "", R.string.change_email);
        mPhoneNumberViewModel.setType(type);
    }

}