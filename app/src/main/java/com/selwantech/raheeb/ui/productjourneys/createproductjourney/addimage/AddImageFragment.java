package com.selwantech.raheeb.ui.productjourneys.createproductjourney.addimage;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductImagesBinding;
import com.selwantech.raheeb.helper.BackPressedHandler;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.interfaces.BackPressed;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;
import com.theartofdev.edmodo.cropper.CropImage;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class AddImageFragment extends BaseFragment<FragmentAddProductImagesBinding, AddImageViewModel>
        implements AddImageNavigator, ActivityResultCallBack {

    private static final String TAG = AddImageFragment.class.getSimpleName();

    BackPressed backPressed;
    @Inject
    ViewModelProviderFactory factory;
    private AddImageViewModel mViewModel;
    private FragmentAddProductImagesBinding mViewBinding;

    boolean enableCallback = true;
    BackPressedHandler backPressedHandler;
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
        return R.layout.fragment_add_product_images;
    }

    @Override
    public AddImageViewModel getViewModel() {
        mViewModel = (AddImageViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(AddImageViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpLocalToolbar();
        mViewModel.setUp();
        setupOnBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupOnBackPressed();
    }

    public void setupOnBackPressed() {
        if (backPressedHandler != null) {
            getBaseActivity().getOnBackPressedDispatcher().addCallback(this, backPressedHandler);
        }
    }

    private void setUpLocalToolbar() {
        mViewBinding.toolbar.toolbar.setTitle(R.string.post_an_item);
        mViewBinding.toolbar.toolbar.setNavigationIcon(getMyContext().getResources().getDrawable(R.drawable.ic_arrow_back));
        mViewBinding.toolbar.toolbar.setNavigationOnClickListener(v -> {
            backPressed.onBackPressed(0);
        });
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

    @Override
    public Post getPost() {
        return (Post) getArguments().getSerializable(AppConstants.BundleData.POST);
    }

    public Post onNextClicked() {
        return mViewModel.returnData();

    }

    public void setBackPressed(BackPressed backPressed) {
        this.backPressed = backPressed;
        backPressedHandler = new BackPressedHandler(true, 0, this.backPressed);
    }

    public void disableBackPress() {
        backPressedHandler.setEnabled(false);
    }

}
