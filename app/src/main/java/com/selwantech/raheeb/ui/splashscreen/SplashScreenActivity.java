package com.selwantech.raheeb.ui.splashscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.selwantech.raheeb.BR;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivitySplashScreenBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreenActivity extends BaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>
        implements SplashScreenNavigator {

    private SplashScreenViewModel mSplashViewModel;
    private ActivitySplashScreenBinding mViewBinding;

    String inviteToken = "";
    public static Intent newIntent(Context context) {
        return new Intent(context, SplashScreenActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public SplashScreenViewModel getViewModel() {
        mSplashViewModel = (SplashScreenViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(SplashScreenViewModel.class, getViewDataBinding(), this);
        return mSplashViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_VIEW.equals(action)) {
            handleSendText(intent);
        }
    }

    void handleSendText(Intent intent) {
        Bundle data = new Bundle();
        String sharedText = intent.getData().toString();
        if (sharedText != null &&
                sharedText.contains("invite") &&
                sharedText.contains("token")) {

            inviteToken = sharedText.substring(sharedText.indexOf("=") + 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    if (ContextCompat.checkSelfPermission(getMyContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            && (ContextCompat.checkSelfPermission(getMyContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(getMyContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(getMyContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(getMyContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                        showToast(getString(R.string.permission_denied_you_cannot_access_location_data_and_camera));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                                ActivityCompat.requestPermissions((Activity) getMyContext(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA,
                                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                                        , 1);
                                return;
                            }
                        }
                    } else {
                        mSplashViewModel.doSplash();
                    }
                }
                break;
        }
    }

    @Override
    public String getInviteToken() {
        return inviteToken;
    }
}