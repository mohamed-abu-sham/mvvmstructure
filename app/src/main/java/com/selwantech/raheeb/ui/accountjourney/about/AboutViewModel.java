package com.selwantech.raheeb.ui.accountjourney.about;

import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAboutBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;

public class AboutViewModel extends BaseViewModel<FragmentAboutBinding> {

            public <V extends ViewDataBinding, N> AboutViewModel(Context mContext, DataManager dataManager, V viewDataBinding, Intent intent) {
        super(mContext, dataManager, intent, (FragmentAboutBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        getData();
    }

    public void getData() {
        getDataManager().getAppService().getAbout(getMyContext(), true, new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                getViewBinding().tvBody.setText(Html.fromHtml(response));
            }

            @Override
            public void onError(String error, int errorCode) {

                showSnackBar(getMyContext().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok),
                        new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }
}
