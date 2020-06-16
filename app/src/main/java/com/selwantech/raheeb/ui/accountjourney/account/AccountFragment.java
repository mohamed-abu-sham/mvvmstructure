package com.selwantech.raheeb.ui.accountjourney.account;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAccountBinding;
import com.selwantech.raheeb.databinding.FragmentUserProfileBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;
import com.theartofdev.edmodo.cropper.CropImage;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends BaseFragment<FragmentAccountBinding, AccountViewModel>
        implements AccountNavigator , ActivityResultCallBack{

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
        return true;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return this::callBack;
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

    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PickImageUtility.PICK_IMAGE_GALLERY:
                if (resultCode == RESULT_OK) {
                    PickImageUtility.Crop(data.getData(), getBaseActivity(), true);
                }
                break;
            case PickImageUtility.PICK_IMAGE_CAMERA:
                if (resultCode == RESULT_OK) {
                    PickImageUtility.Crop(PickImageUtility.getCameraImage(), getBaseActivity(), true);
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    mViewModel.setImage(result.getUri().getPath());
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }

}
