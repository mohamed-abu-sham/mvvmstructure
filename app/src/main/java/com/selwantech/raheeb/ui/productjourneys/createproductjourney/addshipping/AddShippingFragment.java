package com.selwantech.raheeb.ui.productjourneys.createproductjourney.addshipping;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductShippingBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.interfaces.BackPressed;
import com.selwantech.raheeb.interfaces.BackPressedHandler;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class AddShippingFragment extends BaseFragment<FragmentAddProductShippingBinding, AddShippingViewModel>
        implements AddShippingNavigator, ActivityResultCallBack {

    private static final String TAG = AddShippingFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    BackPressed backPressed;
    private AddShippingViewModel mViewModel;
    private FragmentAddProductShippingBinding mViewBinding;
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
        return R.layout.fragment_add_product_shipping;
    }

    @Override
    public AddShippingViewModel getViewModel() {
        mViewModel = (AddShippingViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(AddShippingViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mViewBinding.setCategory(getPost().getCategory());
        setUpLocalToolbar();
        mViewModel.setUp();
        setupOnBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getBaseActivity() != null)
            setupOnBackPressed();
    }

    public void setupOnBackPressed() {
        if (backPressedHandler != null) {
            getBaseActivity().getOnBackPressedDispatcher().addCallback(this, backPressedHandler);
        }
    }

    private void setUpLocalToolbar() {
        mViewBinding.toolbar.toolbar.setTitle(R.string.set_your_price);
        mViewBinding.toolbar.toolbar.setNavigationIcon(getMyContext().getResources().getDrawable(R.drawable.ic_arrow_back));
        mViewBinding.toolbar.toolbar.setNavigationOnClickListener(v -> {
            backPressed.onBackPressed(3);
        });
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
        backPressedHandler = new BackPressedHandler(true, 3, this.backPressed);
    }

    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                mViewModel.setLocation(data.getParcelableExtra(AppConstants.BundleData.ADDRESS));
            }
        }
    }

    public void disableBackPress() {
        backPressedHandler.setEnabled(false);
    }
}
