package com.selwantech.raheeb.ui.productjourneys.createproductjourney.addprice;

import android.content.Context;

import androidx.activity.OnBackPressedCallback;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductPriceBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.interfaces.BackPressed;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class AddPriceFragment extends BaseFragment<FragmentAddProductPriceBinding, AddPriceViewModel> implements AddPriceNavigator {

    private static final String TAG = AddPriceFragment.class.getSimpleName();

    BackPressed backPressed;

    @Inject
    ViewModelProviderFactory factory;
    private AddPriceViewModel mViewModel;
    private FragmentAddProductPriceBinding mViewBinding;


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
        return R.layout.fragment_add_product_price;
    }

    @Override
    public AddPriceViewModel getViewModel() {
        mViewModel = (AddPriceViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(AddPriceViewModel.class, getViewDataBinding(), this);
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

    private void setupOnBackPressed() {
        getBaseActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backPressed.onBackPressed(2);
                this.setEnabled(false);
            }
        });
    }

    private void setUpLocalToolbar() {
        mViewBinding.toolbar.toolbar.setTitle(R.string.set_your_price);
        mViewBinding.toolbar.toolbar.setNavigationIcon(getMyContext().getResources().getDrawable(R.drawable.ic_arrow_back));
        mViewBinding.toolbar.toolbar.setNavigationOnClickListener(v -> {
            backPressed.onBackPressed(2);
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
