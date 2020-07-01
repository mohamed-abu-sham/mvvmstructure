package com.selwantech.raheeb.ui.main;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityMainBinding;
import com.selwantech.raheeb.helper.NotificationHelper;
import com.selwantech.raheeb.model.notificationsdata.NotifyData;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.AppConstants;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;


public class MainActivityViewModel extends BaseViewModel<ActivityMainBinding> {

    NavOptions navOptions;
    NavOptions.Builder navBuilder = new NavOptions.Builder();
    public <V extends ViewDataBinding, N> MainActivityViewModel(Context mContext, DataManager dataManager, V viewDataBinding, Intent intent) {
        super(mContext, dataManager, intent, (ActivityMainBinding) viewDataBinding);

    }


    @Override
    protected void setUp() {
        navOptions = navBuilder.setPopUpTo(R.id.nav_home, false).build();
        checkNotification();
    }

    private void checkNotification() {
        if (getNotification() != null) {
            new NotificationHelper(getBaseActivity(), getNotification()).handleNotificationEvent();
        }
    }
    private void navigate(int id) {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(id, null, navOptions);
    }

    public NotifyData getNotification() {
        if (getIntent().getExtras() != null)
            return (NotifyData) getIntent().getSerializableExtra(AppConstants.BundleData.NOTIFICATION);
        else return null;
    }
}
