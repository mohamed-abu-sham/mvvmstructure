package com.selwantech.raheeb.ui.userprofilejourney.user_profile_details;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUserProfileDetailsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class UserProfileDetailsFragment extends BaseFragment<FragmentUserProfileDetailsBinding, UserProfileDetailsViewModel> implements UserProfileDetailsNavigator {

    private static final String TAG = UserProfileDetailsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private UserProfileDetailsViewModel mViewModel;
    private FragmentUserProfileDetailsBinding mViewBinding;

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
        return R.layout.fragment_user_profile_details;
    }

    @Override
    public UserProfileDetailsViewModel getViewModel() {
        mViewModel = (UserProfileDetailsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(UserProfileDetailsViewModel.class, getViewDataBinding(), this);
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
    public ProductOwner getUser() {
        return (ProductOwner) getArguments().getSerializable(AppConstants.BundleData.PRODUCT_OWNER);
    }
}
