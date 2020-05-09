package com.selwantech.raheeb.ui.auth.chooseusertype;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;

import com.selwantech.raheeb.databinding.ActivityChooseUserTypeBinding;
import com.selwantech.raheeb.databinding.ActivityEmptyBinding;
import com.selwantech.raheeb.model.DataExample;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.RequestFactory;
import com.selwantech.raheeb.ui.auth.login.LoginActivity;
import com.selwantech.raheeb.ui.auth.register.RegisterActivity;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseUserTypeViewModel extends BaseViewModel<ChooseUserTypeNavigator, ActivityChooseUserTypeBinding> {

    public <V extends ViewDataBinding, N extends BaseNavigator> ChooseUserTypeViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ChooseUserTypeNavigator) navigation, (ActivityChooseUserTypeBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {

    }

    public void loginClick() {
        getMyContext().startActivity(LoginActivity.newIntent(getMyContext()));
    }

    public void loginAsGuestClick() {
        getMyContext().startActivity(MainActivity.newIntent(getMyContext()));
    }

    public void registerClick() {
        getMyContext().startActivity(RegisterActivity.newIntent(getMyContext()));
    }
}
