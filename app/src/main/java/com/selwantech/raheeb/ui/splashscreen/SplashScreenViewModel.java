package com.selwantech.raheeb.ui.splashscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;

import com.selwantech.raheeb.databinding.ActivitySplashScreenBinding;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.auth.chooseusertype.ChooseUserTypeActivity;
import com.selwantech.raheeb.ui.auth.register.RegisterActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.main.MainActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreenViewModel extends BaseViewModel<SplashScreenNavigator, ActivitySplashScreenBinding> {


    private static final int PERMISSION_REQUEST_CODE = 1;
    private final double SPLASH_DISPLAY_LENGTH = 3000.00;

    public <V extends ViewDataBinding, N extends BaseNavigator> SplashScreenViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (SplashScreenNavigator) navigation, (ActivitySplashScreenBinding) viewDataBinding);
        checkPermission();
    }


    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getMyContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getMyContext(),
                        WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                && (ContextCompat.checkSelfPermission(getMyContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getMyContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getMyContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            requestPermission();
        } else {
            doSplash();
        }
    }

    public void doSplash() {
        countDownTimer.start();
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions((Activity) getMyContext(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                , PERMISSION_REQUEST_CODE);

    }

    @Override
    protected void setUp() {

    }

    CountDownTimer countDownTimer = new CountDownTimer((long) SPLASH_DISPLAY_LENGTH, 100) {
        @Override
        public void onTick(long millisUntilFinished) {

            double progress = (SPLASH_DISPLAY_LENGTH - millisUntilFinished);
            progress= (progress / SPLASH_DISPLAY_LENGTH) * 100;
            getViewBinding().progress.setProgress((int) Math.round(progress));
        }

        @Override
        public void onFinish() {
            SessionManager.init(getMyContext());
            if (SessionManager.isLoggedIn()) {
                SessionManager.getUserDetails();
                getBaseActivity().finish();
                getMyContext().startActivity(MainActivity.newIntent(getMyContext()));
            } else if (!SessionManager.isLoggedIn() && !getNavigator().getInviteToken().isEmpty()) {
                getBaseActivity().finish();
                getMyContext().startActivity(RegisterActivity.newIntent(getMyContext(), getNavigator().getInviteToken()));
            } else {
                getBaseActivity().finish();
                getMyContext().startActivity(ChooseUserTypeActivity.newIntent(getMyContext(), getNavigator().getInviteToken()));
            }
        }
    };
}
