package com.selwantech.raheeb.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityMainBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.ui.dialog.CustomDialogUtils;
import com.selwantech.raheeb.utils.LanguageUtils;
import com.selwantech.raheeb.utils.NetworkUtils;
import com.selwantech.raheeb.utils.SnackViewBulider;

import dagger.android.AndroidInjection;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    ActivityResultCallBack activityResultCallBack;
    private T mViewDataBinding;
    private V mViewModel;
    private CustomDialogUtils progressDialog;

    public abstract int getBindingVariable();

    public abstract void setUpToolbar();

    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();

    public abstract Context getMyContext();


    public ActivityResultCallBack getActivityResultCallBack() {
        return activityResultCallBack;
    }

    @Override
    public void onFragmentAttachedNeedActivityResult(boolean hideBottom,
                                                     ActivityResultCallBack activityResultCallBack) {
        onFragmentAttachProcess(hideBottom);
        this.activityResultCallBack = activityResultCallBack;
    }

    @Override
    public void onFragmentAttached(boolean hideBottom) {
        onFragmentAttachProcess(hideBottom);
    }

    private void onFragmentAttachProcess( boolean hideBottom) {
        if (mViewDataBinding instanceof ActivityMainBinding) {
            if (hideBottom) {
                ((ActivityMainBinding) mViewDataBinding).bottomSheet.setVisibility(View.GONE);
            } else {
                ((ActivityMainBinding) mViewDataBinding).bottomSheet.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onFragmentDetached(String tag) {
        if (activityResultCallBack != null) {
            activityResultCallBack = null;
        }
    }


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        performDependencyInjection();
        setTheme(LanguageUtils.getStyle(this));

        checkPhoneTheme();
        progressDialog = new CustomDialogUtils(this, true, false);

        super.onCreate(savedInstanceState);
        performDataBinding();
        setUpToolbar();
    }

    public void setUpToolbar(Toolbar toolbar, int title, boolean withHome) {
        setSupportActionBar(toolbar);
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(withHome);
    }
    public void setUpToolbar(Toolbar toolbar, boolean withHome) {
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(withHome);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        checkPhoneTheme();
    }

    private void checkPhoneTheme() {
        Configuration configuration = Resources.getSystem().getConfiguration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                getTheme().applyStyle(R.style.AppThemeLight, true);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                getTheme().applyStyle(R.style.AppThemeLight, true);
                break;
        }
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
//        startActivity(LoginActivity.newIntent(this));
//        finish();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showLoading(boolean isCancelable) {
        progressDialog.showProgress(isCancelable);
    }

    public void showLoading() {
        progressDialog.showProgress();
    }

    public void hideLoading() {
        progressDialog.hideProgress();
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();

    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void showSnackBar(View view, int duration,
                             int icon, String title, String body,
                             String actionText) {
        SnackViewBulider snackViewBulider = new SnackViewBulider(this, view, icon, title,
                body, actionText);
        snackViewBulider.setDuration(duration);
        snackViewBulider.showSnackbar(new SnackViewBulider.SnackbarCallback() {
            @Override
            public void onActionClick(Snackbar snackbar) {
                snackbar.dismiss();
            }
        });
    }

    public void showSnackBar(View view, int icon,
                             String title, String body,
                             String actionText, SnackViewBulider.SnackbarCallback snackbarCallback) {
        SnackViewBulider snackViewBulider = new SnackViewBulider(this, view, icon, title,
                body, actionText);
        snackViewBulider.showSnackbar(snackbarCallback);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

