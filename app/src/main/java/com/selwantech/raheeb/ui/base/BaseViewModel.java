
package com.selwantech.raheeb.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;


public abstract class BaseViewModel<T extends ViewDataBinding> extends ViewModel {


    private T ViewBinding;
    private Context mContext;
    private DataManager dataManager;

    Intent intent;

    public BaseViewModel(Context mContext, DataManager dataManager, Intent intent, T viewBinding) {
        this.mContext = mContext;
        this.dataManager = dataManager;
        this.ViewBinding = viewBinding;
        this.intent = intent;
    }


    public BaseViewModel() {
    }

    protected abstract void setUp();

    protected T getViewBinding() {
        return ViewBinding;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    protected Intent getIntent() {
        return intent;
    }

    protected Bundle getBundle() {
        if (getIntent().getExtras() != null) {
            return getIntent().getExtras();
        } else
            return new Bundle();
    }

    protected Context getMyContext() {
        return mContext;
    }

    protected DataManager getDataManager() {
        return dataManager;
    }

    protected void showLoading() {
        ((BaseActivity<ViewDataBinding, BaseViewModel>) getMyContext()).showLoading();
    }

    protected void hideLoading() {
        ((BaseActivity<ViewDataBinding, BaseViewModel>) getMyContext()).hideLoading();
    }

    protected BaseActivity getBaseActivity() {
        return ((BaseActivity) getMyContext());
    }

    public void showSnackBar(String title, String message, String actionText, SnackViewBulider.SnackbarCallback snackbarCallback) {
        getBaseActivity().showSnackBar(getViewBinding().getRoot(), R.drawable.ic_warning,
                title, message, actionText, snackbarCallback);
    }

    public void showToast(String message) {
        Toast.makeText(getMyContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void popUp() {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment).popBackStack(Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment).
                getCurrentDestination().getId(), true);
    }

}
