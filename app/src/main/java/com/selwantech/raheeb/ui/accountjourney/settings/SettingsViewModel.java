package com.selwantech.raheeb.ui.accountjourney.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSettingsBinding;
import com.selwantech.raheeb.enums.PhoneNumberTypes;
import com.selwantech.raheeb.enums.SettingsTypes;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.interfaces.RecyclerClickNoData;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.SettingsAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SettingsViewModel extends BaseViewModel<SettingsNavigator, FragmentSettingsBinding> implements RecyclerClickNoData {


    public <V extends ViewDataBinding, N extends BaseNavigator> SettingsViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (SettingsNavigator) navigation, (FragmentSettingsBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpRecycler();
        setNotificationSwitches();
    }

    private void setNotificationSwitches() {
        getViewBinding().switchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatePushNotifications();
            }
        });

        getViewBinding().switchEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateEmailNotifications();
            }
        });
    }

    private void updateEmailNotifications() {
        getDataManager().getAccountService().updateEmailNotifications(getMyContext(), true, new APICallBack<User>() {
            @Override
            public void onSuccess(User response) {
                response.setToken(User.getInstance().getToken());
                User.getInstance().setObjUser(response);
                SessionManager.createUserLoginSession();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
//                getViewBinding().switchEmail.setChecked(!getViewBinding().switchEmail.isChecked());
            }
        });
    }

    private void updatePushNotifications() {
        getDataManager().getAccountService().updatePushNotifications(getMyContext(), true, new APICallBack<User>() {
            @Override
            public void onSuccess(User response) {
                response.setToken(User.getInstance().getToken());
                User.getInstance().setObjUser(response);
                SessionManager.createUserLoginSession();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
//                getViewBinding().switchPush.setChecked(!getViewBinding().switchPush.isChecked());
            }
        });
    }


    private void setUpRecycler() {
        getViewBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.VERTICAL, false));
        getViewBinding().recyclerView.setAdapter(new SettingsAdapter(getMyContext(), this, getArraylistText()));
    }

    public void onAboutClicked(View view) {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_settingsFragment_to_aboutFragment);
    }

    private ArrayList<String> getArraylistText() {
        ArrayList<String> arrayListText = new ArrayList<>();
        arrayListText.add(User.getInstance().getPhone() != null && !User.getInstance().getPhone().isEmpty() ?
                User.getInstance().getPhone() : getMyContext().getResources().getString(R.string.phone_number));
        arrayListText.add(User.getInstance().getEmail() != null && !User.getInstance().getEmail().isEmpty() ?
                User.getInstance().getEmail() : getMyContext().getResources().getString(R.string.email));
        arrayListText.add(User.getInstance().isIs_valid() ? getMyContext().getResources().getString(R.string.trusted_member)
                : getMyContext().getResources().getString(R.string.upload_id_image));
        arrayListText.add(User.getInstance().isLoggedInWithTwitter() ? getMyContext().getResources().getString(R.string.connected_with_twitter)
                : getMyContext().getResources().getString(R.string.connect_with_twitter));
        arrayListText.add(getMyContext().getResources().getString(R.string.password));
        arrayListText.add(getMyContext().getResources().getString(R.string.set_your_location));
        return arrayListText;
    }

    @Override
    public void onClick(int position) {
        Bundle data = new Bundle();
        SettingsTypes settingsTypes = SettingsTypes.fromInt(position);
        switch (settingsTypes) {
            case PHONE_NUMBER:
                data.putInt(AppConstants.BundleData.TYPE, PhoneNumberTypes.CHANGE_PHONE_NUMBER.getValue());
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_settingsFragment_to_phoneNumberFragment, data);
                break;
            case EMAIL:
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_settingsFragment_to_updateEmailFragment);
                break;
            case TRUSTED:
                if (!User.getInstance().isIs_valid()) {
                    Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                            .navigate(R.id.action_settingsFragment_to_updateIDFragment);
                }
                break;
            case TWITTER:

                break;
            case PASSWORD:
                Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_settingsFragment_to_changePasswordFragment);
                break;
            case LOCATION:

                break;
        }
    }

}
