package com.selwantech.raheeb.ui.main;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

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

    }
}
