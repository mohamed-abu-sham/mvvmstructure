package com.selwantech.raheeb.ui.userprofilejourney.reportuser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentReportUserBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;
import com.theartofdev.edmodo.cropper.CropImage;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class ReportUserFragment extends BaseFragment<FragmentReportUserBinding, ReportUserViewModel>
        implements ReportUserNavigator, ActivityResultCallBack {

    private static final String TAG = ReportUserFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ReportUserViewModel mViewModel;
    private FragmentReportUserBinding mViewBinding;


    public static ReportUserFragment newInstance() {
        Bundle args = new Bundle();
        ReportUserFragment fragment = new ReportUserFragment();
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
        return R.layout.fragment_report_user;
    }

    @Override
    public ReportUserViewModel getViewModel() {
        mViewModel = (ReportUserViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ReportUserViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mViewBinding.setProductOwner(getProductOwner());
        setUpToolbar(mViewBinding.toolbar, TAG, getString(R.string.contact));
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

    @Override
    public ProductOwner getProductOwner() {
        return (ProductOwner) getArguments().getSerializable(AppConstants.BundleData.PRODUCT_OWNER);
    }
}
