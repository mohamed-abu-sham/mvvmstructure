package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.reportproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentReportProductBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;
import com.theartofdev.edmodo.cropper.CropImage;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class ReportProductFragment extends BaseFragment<FragmentReportProductBinding, ReportProductViewModel>
        implements ReportProductNavigator, ActivityResultCallBack {

    private static final String TAG = ReportProductFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ReportProductViewModel mViewModel;
    private FragmentReportProductBinding mViewBinding;


    public static ReportProductFragment newInstance() {
        Bundle args = new Bundle();
        ReportProductFragment fragment = new ReportProductFragment();
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
        return R.layout.fragment_report_product;
    }

    @Override
    public ReportProductViewModel getViewModel() {
        mViewModel = (ReportProductViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ReportProductViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mViewBinding.setProduct(getProduct());
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
    public Product getProduct() {
        return (Product) getArguments().getSerializable(AppConstants.BundleData.PRODUCT);
    }
}
