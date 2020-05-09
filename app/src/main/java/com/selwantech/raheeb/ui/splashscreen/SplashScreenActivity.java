package com.selwantech.raheeb.ui.splashscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.selwantech.raheeb.BR;
import com.selwantech.raheeb.BuildConfig;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.ViewModelProviderFactory;
import com.selwantech.raheeb.databinding.ActivitySplashScreenBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreenActivity extends BaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>
        implements SplashScreenNavigator {

    private SplashScreenViewModel mSplashViewModel;
    private ActivitySplashScreenBinding mViewBinding;

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
        printHash();
    }

    private void printHash() {
        try {
            PackageInfo p = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            for (Signature s : p.signatures) {

                MessageDigest m = MessageDigest.getInstance("SHA");
                m.update(s.toByteArray());
                Log.d("Hashkey", Base64.encodeToString(m.digest(), Base64.DEFAULT));
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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

}