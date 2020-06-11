package com.selwantech.raheeb.ui.main;

import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityMainBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;


public class MainActivityViewModel extends BaseViewModel<MainActivityNavigator, ActivityMainBinding> {

    public <V extends ViewDataBinding, N extends BaseNavigator> MainActivityViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (MainActivityNavigator) navigation, (ActivityMainBinding) viewDataBinding);

    }


    @Override
    protected void setUp() {

        getViewBinding().bottomSheet.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .getCurrentDestination().getId() != menuItem.getItemId()) {
                    Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                            .navigate(menuItem.getItemId());
                }
                return true;
            }
        });

    }
}
