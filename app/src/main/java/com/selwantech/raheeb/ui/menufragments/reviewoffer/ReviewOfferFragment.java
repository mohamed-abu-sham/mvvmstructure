package com.selwantech.raheeb.ui.menufragments.reviewoffer;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentReviewOfferBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.Address;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class ReviewOfferFragment extends BaseFragment<FragmentReviewOfferBinding, ReviewOfferViewModel>
        implements ReviewOfferNavigator, ActivityResultCallBack {

    private static final String TAG = ReviewOfferFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ReviewOfferViewModel mViewModel;
    private FragmentReviewOfferBinding mViewBinding;

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
        return R.layout.fragment_review_offer;
    }

    @Override
    public ReviewOfferViewModel getViewModel() {
        mViewModel = (ReviewOfferViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ReviewOfferViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mViewBinding.setData(getProduct());
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.review_offer);
        mViewModel.setUp();
    }

    @Override
    public Product getProduct() {
        return (Product) getArguments().getSerializable(AppConstants.BundleData.PRODUCT);
    }

    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mViewModel.onAddressChanged((Address) data.getSerializableExtra(AppConstants.BundleData.ADDRESS));
        }
    }
}
