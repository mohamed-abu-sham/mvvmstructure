package com.selwantech.raheeb.ui.accountjourney.updateprofilepicture;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentUpdateProfilePictureBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;
import com.theartofdev.edmodo.cropper.CropImage;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class UpdateProfilePictureFragment extends BaseFragment<FragmentUpdateProfilePictureBinding, UpdateProfilePictureViewModel>
        implements ActivityResultCallBack {

    private static final String TAG = UpdateProfilePictureFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private UpdateProfilePictureViewModel mViewModel;
    private FragmentUpdateProfilePictureBinding mViewBinding;

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
        return R.layout.fragment_update_profile_picture;
    }

    @Override
    public UpdateProfilePictureViewModel getViewModel() {
        mViewModel = (UpdateProfilePictureViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(UpdateProfilePictureViewModel.class, getViewDataBinding(), new Intent().putExtras(getArguments()));
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, getString(R.string.update_profile_picture));
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
                    mViewModel.setReportImageUri(result.getUri());
//                    mViewBinding.imgPicture.setImageURI(result.getUri());
                    GeneralFunction.loadImage(getMyContext(), result.getUri().toString(), mViewBinding.imgPicture);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }

}
