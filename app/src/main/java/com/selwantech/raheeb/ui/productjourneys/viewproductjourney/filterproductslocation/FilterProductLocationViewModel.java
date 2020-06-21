package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.filterproductslocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentFilterProductsLocationBinding;
import com.selwantech.raheeb.enums.FilterProductResultsTypes;
import com.selwantech.raheeb.helper.GeoCoderAddress;
import com.selwantech.raheeb.helper.LocationHelper;
import com.selwantech.raheeb.model.Distance;
import com.selwantech.raheeb.model.FilterLocation;
import com.selwantech.raheeb.model.FilterProduct;
import com.selwantech.raheeb.model.GeoAddress;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.utils.SnackViewBulider;
import com.selwantech.raheeb.utils.seekbar.OnRangeChangedListener;
import com.selwantech.raheeb.utils.seekbar.RangeSeekBar;

import java.io.IOException;
import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;

public class FilterProductLocationViewModel extends
        BaseViewModel<FilterProductLocationNavigator, FragmentFilterProductsLocationBinding>
        implements LocationHelper.OnLocationReceived {

    boolean isCanceled = true ;
    GeoAddress geoAddress;
    FilterLocation filterLocation;
    int distance = 0;
    LocationHelper locationHelper;
    public <V extends ViewDataBinding, N extends BaseNavigator> FilterProductLocationViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (FilterProductLocationNavigator) navigation, (FragmentFilterProductsLocationBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        locationHelper = new LocationHelper(getMyContext());
        locationHelper.setLocationReceivedLister(this);
        getViewBinding().toolbar.tvToolbarAction.setText(R.string.reset);
        getViewBinding().toolbar.tvToolbarAction.setVisibility(View.VISIBLE);
        getViewBinding().toolbar.tvToolbarAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getViewBinding().seekbarKm.getTickMarkTextArray() != null) {
                    getViewBinding().seekbarKm.setProgress(0);
                    onSeekChanged(getViewBinding().seekbarKm.getTickMarkTextArray().get(0));
                }
                setLocation(locationHelper.getCurrentLocation());
            }
        });
        getViewBinding().seekbarKm.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft, Distance distance) {
                onSeekChanged(distance);
//                onSeekChanged(view.getLeftSeekBar().getProgress());
            }
        });

        getData();
    }

    private void getData() {
        getDataManager().getAppService().getDistances(getMyContext(), true, new APICallBack<ArrayList<Distance>>() {
            @Override
            public void onSuccess(ArrayList<Distance> response) {
                getViewBinding().seekbarKm.setSteps(response.size() - 1);
                getViewBinding().seekbarKm.setTickMarkTextArray(response);
                getViewBinding().seekbarKm.setProgress(0);
                onSeekChanged(getViewBinding().seekbarKm.getTickMarkTextArray().get(0));
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

//    private String[] convertDistanceToStrings(ArrayList<Distance> distanceArrayList) {
//        List<String> strings = new ArrayList<>();
//        strings.toArray();
//    }

    public void onEditLocationClick() {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_filterProductLocationFragment_to_mapPickerFragment);
    }

    private void onSeekChanged(Distance distance) {
        changeSelectedDisText(distance.toString());
        setDistance(distance.getDistance());
    }

//    private void onSeekChanged(float progress) {
//        SeekKMTypes whichView = SeekKMTypes.fromInt((int) progress);
//        setDistance(whichView.getMode());
//        switch (whichView){
//            case KM5:
//                changeSelectedDisText(R.string.km5);
//                break;
//            case KM10:
//                changeSelectedDisText(R.string.km10);
//                break;
//            case KM20:
//                changeSelectedDisText(R.string.km20);
//                break;
//            case KM30:
//                changeSelectedDisText(R.string.km30);
//                break;
//            case MAX:
//                changeSelectedDisText(R.string.max);
//                break;
//        }
//    }

    private void changeSelectedDisText(String text) {
        getViewBinding().tvSelectedDis.setText(text);
    }

    private void setDistance(int distance) {
        this.distance = distance;
    }
    public void onApplyClicked() {
        if (isValid()) {
            isCanceled = false;
            popUp();
        }
    }

    private boolean isValid() {
        int error = 0;
        if (filterLocation == null) {
            if (locationHelper.hasPermission()) {
                locationHelper.checkLocationEnabled();
            }
            showSnackBar(getMyContext().getResources().getString(R.string.error),
                    getMyContext().getResources().getString(R.string.please_select_location),
                    getMyContext().getResources().getString(R.string.ok),
                    new SnackViewBulider.SnackbarCallback() {
                        @Override
                        public void onActionClick(Snackbar snackbar) {
                            snackbar.dismiss();
                        }
                    });
            error++;
        }
        return error == 0;
    }

    public void returnData() {
        if(!isCanceled) {
            Intent intent = new Intent();

            FilterProduct.getInstance().setLat(filterLocation.getLat());
            FilterProduct.getInstance().setLon(filterLocation.getLon());
            FilterProduct.getInstance().setDistance(distance);
            ((MainActivity) getBaseActivity()).onActivityResultFromFragment(
                    FilterProductResultsTypes.LOCATION.getValue(), Activity.RESULT_OK, intent);
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
        setLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    protected void setLocation(LatLng latLng) {
        try {
            if (latLng != null) {
                geoAddress = GeoCoderAddress.getInstance().getAddress(latLng);
                filterLocation = new FilterLocation(geoAddress.getAddress(), latLng.latitude, latLng.longitude, distance);
                getViewBinding().tvLocation.setText(geoAddress.getAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
