package com.selwantech.raheeb.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityMainBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel>
        implements MainActivityNavigator {

    NavController navController;

    @Inject
    ViewModelProviderFactory factory;
    private MainActivityViewModel mMainViewModel;
    private ActivityMainBinding mViewBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
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
                .create(MainActivityViewModel.class, getViewDataBinding(), this);
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
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getData().toString();
        if (sharedText != null &&
                sharedText.contains("product") &&
                sharedText.contains("id")) {
            Bundle data = new Bundle();
            int productId = Integer.valueOf(sharedText.substring(sharedText.indexOf("=") + 1));
            data.putInt(AppConstants.BundleData.PRODUCT_ID, productId);
            Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.productDetailsFragment, data);
        }
    }
    public void onActivityResultFromFragment(int requestCode, int resultCode, @Nullable Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getActivityResultCallBack() != null) {
            getActivityResultCallBack().callBack(requestCode, resultCode, data);
        }
    }
}