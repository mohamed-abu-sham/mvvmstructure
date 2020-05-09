package com.selwantech.raheeb.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.selwantech.raheeb.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class DeviceUtils {

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static String getDeviceVersion(Activity activity) {
        String v;
        try {
            v = activity.getApplication().getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
            return v.replace("-staging", "");
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @SuppressLint("HardwareIds")
    public static String getUDID(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String tmDevice = "" + (tm != null ? tm.getDeviceId() : null);
            String tmSerial = "" + (tm != null ? tm.getSimSerialNumber() : null);
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

            return deviceUuid.toString();
        } else {
            return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }


    public static void closeSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void callPhone(Context context, String phoneText) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneText));
        context.startActivity(intent);
    }

    public static void intentEmail(Context context, String email, String title, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static String getDeviceSerial(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void startActivity(Intent intent, Activity context) {
        context.startActivity(intent);
        context.finish();
    }

    public static String getDeviceCountryCode(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkCountryIso();
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public static void rateApp(Context mContext) {
        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            mContext.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
        }
    }

    public static void openDialer(Context mContext, String phone_contact) {
        if (!phone_contact.equalsIgnoreCase("null") && !phone_contact.equalsIgnoreCase("")) {
            Intent intentCall = new Intent(Intent.ACTION_DIAL);
            intentCall.setData(Uri.parse("tel:" + phone_contact));
            mContext.startActivity(intentCall);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(mContext.getString(R.string.app_name))
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage(mContext.getString(R.string.welcome))
                    .setCancelable(true)
                    .setPositiveButton("ok",
                            (dialog, id) -> dialog.dismiss());
            final AlertDialog alert1 = builder.create();
            //alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            alert1.setOnShowListener(arg -> {
                alert1.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                alert1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            });
            alert1.show();
        }
    }

    public static void messageNumber(Context myContext, String phoneNumber) {

        Intent n = new Intent(Intent.ACTION_VIEW);
        n.setType("vnd.android-dir/mms-sms");
        n.putExtra("address", phoneNumber);
        myContext.startActivity(n);
    }
}
