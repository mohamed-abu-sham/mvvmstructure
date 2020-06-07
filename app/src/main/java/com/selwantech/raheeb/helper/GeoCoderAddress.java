package com.selwantech.raheeb.helper;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.selwantech.raheeb.App;
import com.selwantech.raheeb.model.GeoAddress;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class GeoCoderAddress implements Serializable {

    private static GeoCoderAddress ourInstance = new GeoCoderAddress();
    List<Address> addresses;
    Context context;
    private Geocoder geoCoder;

    private GeoCoderAddress() {
        this.geoCoder = new Geocoder(App.getInstance(), Locale.getDefault());
    }

    public static GeoCoderAddress getInstance() {
        if (ourInstance == null)
            ourInstance = new GeoCoderAddress();
        return ourInstance;
    }

    public GeoAddress getAddress(LatLng latLng) throws IOException {
        addresses = this.geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        GeoAddress geoAddress = new GeoAddress(addresses.get(0).getLocality(),
                addresses.get(0).getSubLocality(),
                addresses.get(0).getCountryName(),
                addresses.get(0).getPostalCode(),
                addresses.get(0).getFeatureName(),
                latLng.latitude, latLng.longitude);
        return geoAddress;
    }

}
