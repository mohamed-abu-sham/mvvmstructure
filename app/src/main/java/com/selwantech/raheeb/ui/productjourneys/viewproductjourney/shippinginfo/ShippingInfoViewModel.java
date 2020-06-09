package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.shippinginfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentShippingInfoBinding;
import com.selwantech.raheeb.helper.LocationHelper;
import com.selwantech.raheeb.model.Address;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SmoothMoveMarker;
import com.selwantech.raheeb.utils.SnackViewBulider;

public class ShippingInfoViewModel extends BaseViewModel<ShippingInfoNavigator, FragmentShippingInfoBinding>
        implements LocationHelper.OnLocationReceived, OnMapReadyCallback {

    SupportMapFragment search_place_map;
    boolean canceled = true;
    private GoogleMap googleMap;
    private LatLng currentLatLan;
    private Marker PickUpMarker;
    private LocationHelper locHelper;

    public <V extends ViewDataBinding, N extends BaseNavigator> ShippingInfoViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ShippingInfoNavigator) navigation, (FragmentShippingInfoBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        search_place_map = getNavigator().getChildManager();
        if (null != search_place_map) {
            search_place_map.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        this.googleMap = gMap;
        if (googleMap != null) {
            locHelper = new LocationHelper(getMyContext());
            locHelper.setLocationReceivedLister(this);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(true);
            googleMap.setOnCameraIdleListener(() -> {
                if (null != PickUpMarker) {
                    currentLatLan = googleMap.getCameraPosition().target;
                    SmoothMoveMarker.animateMarker(PickUpMarker, googleMap.getCameraPosition().target, false, googleMap);
                }
            });
        }
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


    @Override
    public void onLocationReceived(LatLng latlong) {

    }

    @Override
    public void onLocationReceived(Location location) {

    }

    @Override
    public void onConntected(Bundle bundle) {

    }

    @Override
    public void onConntected(Location location) {
        if (null != location) {
            LatLng latlong = new LatLng(location.getLatitude(), location.getLongitude());
            currentLatLan = latlong;
            if (null != googleMap) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLan,
                        15));
            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentLatLan)
                    .zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            if (null != googleMap) {
                MarkerOptions markerOpt = new MarkerOptions();
                markerOpt.position(currentLatLan);
                markerOpt.icon(SmoothMoveMarker.bitmapDescriptorFromVector(getMyContext(), R.drawable.ic_pin));
                PickUpMarker = googleMap.addMarker(markerOpt);
            }
        }
    }
}
