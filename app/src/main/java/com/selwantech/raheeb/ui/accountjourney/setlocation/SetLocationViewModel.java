package com.selwantech.raheeb.ui.accountjourney.setlocation;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSetLocationBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.helper.LocationHelper;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.utils.SmoothMoveMarker;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.Arrays;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class SetLocationViewModel extends BaseViewModel<SetLocationNavigator, FragmentSetLocationBinding>
        implements LocationHelper.OnLocationReceived, OnMapReadyCallback {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    SupportMapFragment search_place_map;
    private GoogleMap googleMap;
    private LatLng currentLatLan;
    private Marker PickUpMarker;
    private LocationHelper locHelper;

    public <V extends ViewDataBinding, N extends BaseNavigator> SetLocationViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (SetLocationNavigator) navigation, (FragmentSetLocationBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        search_place_map = getNavigator().getChildManager();
        if (null != search_place_map) {
            search_place_map.getMapAsync(this);
        }

    }


    public void onSearchClicked() {
        if (!Places.isInitialized()) {
            Places.initialize(getBaseActivity().getApplicationContext(), getMyContext().getResources().getString(R.string.map_key));
        }
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(getBaseActivity());
        getBaseActivity().startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    }

    public void onSaveClicked() {
        if (isValid()) {
            updateLocation();
        }
    }

    private void updateLocation() {

        getDataManager().getAccountService().updateLocation(getMyContext(), true, currentLatLan.latitude, currentLatLan.longitude, new APICallBack<Object>() {
            @Override
            public void onSuccess(Object response) {
                showSuccessDialog();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error), error,
                        getMyContext().getResources().getString(R.string.ok),
                        new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }

    private void showSuccessDialog() {
        new OnLineDialog(getMyContext()) {
            @Override
            public void onPositiveButtonClicked() {
                dismiss();
                popUp();
            }

            @Override
            public void onNegativeButtonClicked() {
                dismiss();
            }
        }.showConfirmationDialog(DialogTypes.OK, getMyContext().getResources().getString(R.string.success),
                getMyContext().getResources().getString(R.string.update_successfully));
    }

    private LatLng getAddressObj() {
        return new LatLng(currentLatLan.latitude, currentLatLan.longitude);
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
        }
        return error == 0;
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

    public void onActivityResult(Intent data) {
        final Place place = Autocomplete.getPlaceFromIntent(data);
        currentLatLan = place.getLatLng();
        getViewBinding().tvSearch.setText(place.getName());
        animateMarker();
    }

    private void animateMarker() {
        SmoothMoveMarker.animateMarker(PickUpMarker, currentLatLan, false, googleMap);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLatLan)
                .zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
