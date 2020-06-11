package com.selwantech.raheeb.ui.userprofilejourney.user_profile;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUserProfileBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class UserProfileFragment extends BaseFragment<FragmentUserProfileBinding, UserProfileViewModel> implements UserProfileNavigator {

    private static final String TAG = UserProfileFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private UserProfileViewModel mViewModel;
    private FragmentUserProfileBinding mViewBinding;


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
        return R.layout.fragment_user_profile;
    }

    @Override
    public UserProfileViewModel getViewModel() {
        mViewModel = (UserProfileViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(UserProfileViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        if (getUserId() == -1)
            mViewBinding.setData(getProductOwner());
        mViewModel.setUp();
    }

    @Override
    public ProductOwner getProductOwner() {
        return (ProductOwner) getArguments().getSerializable(AppConstants.BundleData.PRODUCT_OWNER);
    }

    @Override
    public int getUserId() {
        return getArguments().getInt(AppConstants.BundleData.USER_ID, -1);
    }
}
