package com.selwantech.raheeb.ui.auth.editprofile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.ViewModelProviderFactory;
import com.selwantech.raheeb.databinding.FragmentEditProfileBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.theartofdev.edmodo.cropper.CropImage;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>
        implements EditProfileNavigator, ActivityResultCallBack {

    private static final String TAG = EditProfileFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private EditProfileViewModel mEditProfileViewModel;
    private FragmentEditProfileBinding mViewBinding;


    public static EditProfileFragment newInstance() {
        Bundle args = new Bundle();
        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
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
        return true;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return this::callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    public EditProfileViewModel getViewModel() {
        mEditProfileViewModel = (EditProfileViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(EditProfileViewModel.class, getViewDataBinding(), this);
        return mEditProfileViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mEditProfileViewModel.setUp();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.edit_profile);
        mEditProfileViewModel.setUp();

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
                    mEditProfileViewModel.uploadProfilePicture(result.getUri());
//                    mViewBinding.imgPicture.setImageURI(result.getUri());
                    GeneralFunction.loadImage(getMyContext(), result.getUri().toString(), mViewBinding.imgPicture);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }
}
