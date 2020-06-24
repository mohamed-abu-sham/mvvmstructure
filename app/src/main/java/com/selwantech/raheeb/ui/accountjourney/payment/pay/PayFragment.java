package com.selwantech.raheeb.ui.accountjourney.payment.pay;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentPaymentWebviewBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import androidx.activity.OnBackPressedCallback;

public class PayFragment extends BaseFragment<FragmentPaymentWebviewBinding, PayViewModel>
        implements PayNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private PayViewModel mViewModel;
    private FragmentPaymentWebviewBinding mViewBinding;

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
        return R.layout.fragment_payment_webview;
    }

    @Override
    public PayViewModel getViewModel() {
        mViewModel = (PayViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(PayViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, "", R.string.payment_and_sales);
        mViewModel.setUp();
        setupOnBackPressed();
    }

    private void setupOnBackPressed() {
        getBaseActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mViewBinding.webview.canGoBack()) {
                    mViewBinding.webview.goBack();
                } else {
                    mViewModel.popUp();
                    this.setEnabled(false);
                }

            }
        });
    }
}