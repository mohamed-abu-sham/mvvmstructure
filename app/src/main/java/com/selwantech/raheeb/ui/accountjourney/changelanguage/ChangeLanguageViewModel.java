package com.selwantech.raheeb.ui.accountjourney.changelanguage;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.databinding.FragmentChangeLanguageBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.LanguageUtils;

import androidx.databinding.ViewDataBinding;

public class ChangeLanguageViewModel extends BaseViewModel<FragmentChangeLanguageBinding> {


    public <V extends ViewDataBinding, N> ChangeLanguageViewModel(Context mContext, DataManager dataManager, V viewDataBinding, Intent intent) {
        super(mContext, dataManager, intent, (FragmentChangeLanguageBinding) viewDataBinding);

    }


    public void onArClick() {
        getViewBinding().checkAr.setChecked(true);
        getViewBinding().checkEn.setChecked(false);
    }

    public void onEnClick() {
        getViewBinding().checkAr.setChecked(false);
        getViewBinding().checkEn.setChecked(true);
    }

    public void saveClicked() {
        if (getViewBinding().checkAr.isChecked()) {
            LanguageUtils.updateLanguage(getBaseActivity(), "ar");
            return;
        }
        LanguageUtils.updateLanguage(getBaseActivity(), "en");
    }

    @Override
    protected void setUp() {
        String lang = LanguageUtils.getLanguage(getMyContext());
        if (lang.equals("ar")) {
            getViewBinding().checkAr.setChecked(true);
        } else {
            getViewBinding().checkEn.setChecked(true);
        }
    }
}
