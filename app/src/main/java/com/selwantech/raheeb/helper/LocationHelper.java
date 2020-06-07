package com.selwantech.raheeb.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LocationHelper implements LocationListener,
        OnConnectionFailedListener, ConnectionCallbacks {
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private final int INTERVAL = 5000;
    private final int FAST_INTERVAL = 5000;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingError = false;
    private OnLocationReceived mLocationReceived;
    private Context context;
    private LatLng latLong;
    private boolean isLocationReceived = false;

    public LocationHelper(Context context) {
        this.context = context;
        createLocationRequest();
        buildGoogleApiClient();
        if (!mResolvingError) {
            mGoogleApiClient.connect();
        }

    }

    public void setLocationReceivedLister(OnLocationReceived mLocationReceived) {
        this.mLocationReceived = mLocationReceived;
    }

    public LatLng getCurrentLocation() {
        return latLong;
    }

    @SuppressLint("MissingPermission")
    public void returnLastLatLng() {
        if (servicesConnected()) {
            Location location = null;
            if (mGoogleApiClient.isConnected()) {


                location = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);

            }
            latLong = getLatLng(location);
            mLocationReceived.onConntected(location);
        }
    }


    public void onStart() {
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        } else {
            startPeriodicUpdates();
        }
    }

    public void onResume() {
        if (mGoogleApiClient.isConnected()) {
            startPeriodicUpdates();
        }
    }

    public void onPause() {
        if (mGoogleApiClient.isConnected()) {
            stopPeriodicUpdates();
        }
    }

    public void onStop() {
        // If the client is connected
        if (mGoogleApiClient.isConnected()) {
            stopPeriodicUpdates();
        }

        // After disconnect() is called, the client is considered "dead".
        mGoogleApiClient.disconnect();
    }

    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);

        // If Google Play services is available
        // Continue
        // Google Play services was not available for some reason
        // Display an error dialog
        // Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
        // context, 0);
        // if (dialog != null) {
        // ErrorDialogFragment errorFragment = new ErrorDialogFragment();
        // errorFragment.setDialog(dialog);
        // errorFragment.show(context.getSupportFragmentManager(), APPTAG);
        //
        // }
        return ConnectionResult.SUCCESS == resultCode;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (!isLocationReceived) {
            mLocationReceived.onConntected(location);
            isLocationReceived = true;
        }

        if (mLocationReceived != null) {
            mLocationReceived.onLocationReceived(location);
        }
        latLong = getLatLng(location);
        if (mLocationReceived != null && latLong != null) {
            mLocationReceived.onLocationReceived(latLong);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        }
        // else if (connectionResult.hasResolution()) {
        // try {
        // mResolvingError = true;
        // connectionResult.startResolutionForResult(context,
        // CONNECTION_FAILURE_RESOLUTION_REQUEST);
        //
        // } catch (SendIntentException e) {
        // // There was an error with the resolution intent. Try again.
        // mGoogleApiClient.connect();
        // }
        // } else {
        // // Show dialog using GooglePlayServicesUtil.getErrorDialog()
        // // showErrorDialog(arg0.getErrorCode());
        //
        // showErrorDialog(connectionResult.getErrorCode());
        // mResolvingError = true;
        // }

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        startPeriodicUpdates();
        if (mLocationReceived != null)
            mLocationReceived.onConntected(connectionHint);
    }

    private void startPeriodicUpdates() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return;
        }
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                });
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    private void stopPeriodicUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);

    }

    public void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                , PERMISSION_REQUEST_CODE);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FAST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        checkLocationEnabled();
    }

    private void checkLocationEnabled() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10);
        mLocationRequest.setSmallestDisplacement(10);
        mLocationRequest.setFastestInterval(10);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());
        task.addOnCompleteListener(task1 -> {
            try {
                LocationSettingsResponse response = task1.getResult(ApiException.class);
            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            resolvable.startResolutionForResult(
                                    (Activity) context,
                                    101);
//                            returnLastLatLng();
                        } catch (IntentSender.SendIntentException e) {
                        } catch (ClassCastException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        returnLastLatLng();
                        break;
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int arg0) {

    }

    public LatLng getLatLng(Location currentLocation) {
        // If the location is valid
        if (currentLocation != null) {
            // Return the latitude and longitude as strings
            LatLng latLong = new LatLng(currentLocation.getLatitude(),
                    currentLocation.getLongitude());

            return latLong;
        } else {
            // Otherwise, return the empty string
            return null;
        }
    }

    public interface OnLocationReceived {
        void onLocationReceived(LatLng latlong);

        void onLocationReceived(Location location);

        void onConntected(Bundle bundle);

        void onConntected(Location location);

    }

    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}
