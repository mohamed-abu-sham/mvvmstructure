package com.selwantech.raheeb.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityMainBinding;
import com.selwantech.raheeb.helper.LocationHelper;
import com.selwantech.raheeb.model.notificationsdata.NotifyData;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> {

    NavController navController;

    @Inject
    ViewModelProviderFactory factory;
    private MainActivityViewModel mMainViewModel;
    private ActivityMainBinding mViewBinding;

    public static Intent newIntent(Context context, NotifyData notifyData) {
        return new Intent(context, MainActivity.class).putExtra(AppConstants.BundleData.NOTIFICATION, notifyData);
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainActivityViewModel getViewModel() {
        mMainViewModel = (MainActivityViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(MainActivityViewModel.class, getViewDataBinding(), getIntent());
        return mMainViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.setGraph(R.navigation.nav_graph);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .build();
        NavigationUI.setupWithNavController(getViewDataBinding().bottomSheet, navController);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_VIEW.equals(action)) {
            handleSendText(intent);
        }
        mMainViewModel.setUp();

    }

    void handleSendText(Intent intent) {
        Bundle data = new Bundle();
        String sharedText = intent.getData().toString();
        if (sharedText != null &&
                sharedText.contains("product") &&
                sharedText.contains("id")) {

            int productId = Integer.valueOf(sharedText.substring(sharedText.indexOf("=") + 1));
            data.putInt(AppConstants.BundleData.PRODUCT_ID, productId);
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.productDetailsFragment, data);
        } else if (sharedText != null &&
                sharedText.contains("user") &&
                sharedText.contains("id")) {

            int userId = Integer.valueOf(sharedText.substring(sharedText.indexOf("=") + 1));
            data.putInt(AppConstants.BundleData.USER_ID, userId);
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.userProfileFragment, data);
        }
    }
    public void onActivityResultFromFragment(int requestCode, int resultCode, @Nullable Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (getActivityResultCallBack() != null && requestCode != LocationHelper.LOCATION_REQUEST_CODE) {
            getActivityResultCallBack().callBack(requestCode, resultCode, data);
        }
    }
}