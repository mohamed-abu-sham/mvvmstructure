package com.selwantech.raheeb.ui.productjourneys.createproductjourney.adddetails;

import android.content.Context;

import androidx.activity.OnBackPressedCallback;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductDetailsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.interfaces.BackPressed;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class AddDetailsFragment extends BaseFragment<FragmentAddProductDetailsBinding,
        AddDetailsViewModel> implements AddDetailsNavigator {

    private static final String TAG = AddDetailsFragment.class.getSimpleName();

    BackPressed backPressed;

    @Inject
    ViewModelProviderFactory factory;
    private AddDetailsViewModel mViewModel;
    private FragmentAddProductDetailsBinding mViewBinding;


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
        return R.layout.fragment_add_product_details;
    }

    @Override
    public AddDetailsViewModel getViewModel() {
        mViewModel = (AddDetailsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(AddDetailsViewModel.class, getViewDataBinding(), this);
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
        getBaseActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backPressed.onBackPressed(1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getBaseActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backPressed.onBackPressed(1);
            }
        });
    }

    private void setUpLocalToolbar() {
        mViewBinding.toolbar.toolbar.setTitle(R.string.describe_your_item);
        mViewBinding.toolbar.toolbar.setNavigationIcon(getMyContext().getResources().getDrawable(R.drawable.ic_arrow_back));
        mViewBinding.toolbar.toolbar.setNavigationOnClickListener(v -> {
            backPressed.onBackPressed(1);
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
    }

}
