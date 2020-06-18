package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.shippinginfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentShippingInfoBinding;
import com.selwantech.raheeb.helper.GeoCoderAddress;
import com.selwantech.raheeb.model.Address;
import com.selwantech.raheeb.model.GeoAddress;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.io.IOException;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;

public class ShippingInfoViewModel extends BaseViewModel<ShippingInfoNavigator, FragmentShippingInfoBinding> {

    boolean canceled = true;
    private LatLng currentLatLan;

    GeoAddress geoAddress;
    public <V extends ViewDataBinding, N extends BaseNavigator> ShippingInfoViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ShippingInfoNavigator) navigation, (FragmentShippingInfoBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {


    }

    public void onEditLocationClick() {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_shippingInfoFragment_to_mapPickerFragment);
    }

    public void returnData() {
        if (!canceled) {
            Intent data = new Intent();
            data.putExtra(AppConstants.BundleData.ADDRESS, getAddressObj());
            ((MainActivity) getBaseActivity()).onActivityResultFromFragment(
                    100, Activity.RESULT_OK, data);
        }
    }

    public void onSubmitClicked() {
        if (isValid()) {
            canceled = false;
            popUp();
        }
    }

    private Address getAddressObj() {
        return new Address(getViewBinding().edName.getText().toString(),
                String.valueOf(currentLatLan.latitude),
                String.valueOf(currentLatLan.longitude),
                getViewBinding().edFloor.getText().toString(),
                getViewBinding().edBuildingNumber.getText().toString());
    }

    private boolean isValid() {
        int error = 0;
        if (currentLatLan == null) {
            showSnackBar(getMyContext().getResources().getString(R.string.wrong_location),
                    getMyContext().getResources().getString(R.string.please_select_location),
                    getMyContext().getResources().getString(R.string.OK), new SnackViewBulider.SnackbarCallback() {
                        @Override
                        public void onActionClick(Snackbar snackbar) {
                            snackbar.dismiss();
                        }
                    });
            error = +1;
        }
        if (getViewBinding().edName.getText().toString().trim().isEmpty()) {
            getViewBinding().edName.setError(getMyContext().getResources().getString(R.string.this_fieled_is_required));
            error = +1;
        }
        if (getViewBinding().edFloor.getText().toString().trim().isEmpty()) {
            getViewBinding().edFloor.setError(getMyContext().getResources().getString(R.string.this_fieled_is_required));
            error = +1;
        }
        if (getViewBinding().edBuildingNumber.getText().toString().trim().isEmpty()) {
            getViewBinding().edBuildingNumber.setError(getMyContext().getResources().getString(R.string.this_fieled_is_required));
            error = +1;
        }
        return error == 0;
    }

    protected void setLocation(LatLng latLng) {
        try {
            if (latLng != null) {
                geoAddress = GeoCoderAddress.getInstance().getAddress(latLng);
                currentLatLan = latLng;
                getViewBinding().tvLocation.setText(geoAddress.getAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
