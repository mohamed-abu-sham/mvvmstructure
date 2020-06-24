package com.selwantech.raheeb.ui.main;

import android.content.Context;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityMainBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.helper.NotificationHelper;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.auth.chooseusertype.ChooseUserTypeActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;


public class MainActivityViewModel extends BaseViewModel<MainActivityNavigator, ActivityMainBinding> {

    NavOptions navOptions;
    NavOptions.Builder navBuilder = new NavOptions.Builder();
    public <V extends ViewDataBinding, N extends BaseNavigator> MainActivityViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (MainActivityNavigator) navigation, (ActivityMainBinding) viewDataBinding);

    }


    @Override
    protected void setUp() {
        navOptions = navBuilder.setPopUpTo(R.id.nav_home, false).build();
        getViewBinding().bottomSheet.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .getCurrentDestination().getId() != menuItem.getItemId()) {
                    if (menuItem.getItemId() == R.id.nav_home) {
                        navigate(menuItem.getItemId());
                    } else {
                        if (SessionManager.isLoggedIn()) {
                            navigate(menuItem.getItemId());
                        } else {
                            new OnLineDialog(getBaseActivity()) {
                                @Override
                                public void onPositiveButtonClicked() {
                                    getViewBinding().bottomSheet.setSelectedItemId(R.id.nav_home);
                                    dismiss();
                                    getBaseActivity().startActivity(ChooseUserTypeActivity.newIntent(getBaseActivity(), ""));
                                }

                                @Override
                                public void onNegativeButtonClicked() {
                                    getViewBinding().bottomSheet.setSelectedItemId(R.id.nav_home);
                                    dismiss();
                                }
                            }.showConfirmationDialog(DialogTypes.OK_CANCEL, getMyContext().getResources().getString(R.string.login_is_required),
                                    getMyContext().getResources().getString(R.string.go_to_login));
                        }
                    }
                }
                return true;
            }
        });

        checkNotification();
    }

    private void checkNotification() {
        if (getNavigator().getNotification() != null) {
            new NotificationHelper(getBaseActivity(), getNavigator().getNotification());
        }
    }
    private void navigate(int id) {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(id, null, navOptions);
    }
}
