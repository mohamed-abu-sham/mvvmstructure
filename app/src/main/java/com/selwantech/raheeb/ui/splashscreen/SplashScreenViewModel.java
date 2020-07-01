package com.selwantech.raheeb.ui.splashscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;

import com.selwantech.raheeb.databinding.ActivitySplashScreenBinding;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.notificationsdata.NotifyData;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.auth.register.RegisterActivity;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.main.MainActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreenViewModel extends BaseViewModel<ActivitySplashScreenBinding> {


    private static final int PERMISSION_REQUEST_CODE = 1;
    private final double SPLASH_DISPLAY_LENGTH = 3000.00;
    String inviteToken = "";
    public <V extends ViewDataBinding, N> SplashScreenViewModel(Context mContext, DataManager dataManager, V viewDataBinding, Intent intent) {
        super(mContext, dataManager, intent, (ActivitySplashScreenBinding) viewDataBinding);
        checkPermission();
    }

    @Override
    protected void setUp() {
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
                sharedText.contains("invite") &&
                sharedText.contains("token")) {

            inviteToken = sharedText.substring(sharedText.indexOf("=") + 1);
        }
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
                getMyContext().startActivity(MainActivity.newIntent(getMyContext(),getNotification()));
            } else if (!SessionManager.isLoggedIn() && !inviteToken.isEmpty()) {
                getBaseActivity().finish();
                getMyContext().startActivity(RegisterActivity.newIntent(getMyContext(), inviteToken));
            } else {
                getBaseActivity().finish();
//                getMyContext().startActivity(ChooseUserTypeActivity.newIntent(getMyContext(), getNavigator().getInviteToken()));
            }
        }
    };

    public NotifyData getNotification() {
        if (getIntent().getExtras() != null)
            return new NotifyData(Integer.valueOf(getIntent().getExtras().getString(("acction_id"))),
                    getIntent().getExtras().getString("type"));
        else return null;
    }
}
