package com.selwantech.raheeb.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.ui.auth.login.LoginActivity;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;

public class SessionManager {
    private static final String PREF_NAME = "com.selwantech.spring_nights";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_OBJECT_USER = "objUser";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_FIREBASE_TOKEN = "deviceToken";
    private static final String KEY_ALLOW_EMAIL_NOTIFICATIONS = "allowEmailNotifications";
    private static final String KEY_ALLOW_PUSH_NOTIFICATIONS = "allowPushNotifications";
    // Editor for Shared preferences
    static SharedPreferences.Editor editor;
    private static SharedPreferences mSharedPref;
    // Shared mSharedPref mode
    int PRIVATE_MODE = 0;


    // Constructor

    private SessionManager() {
    }

    /**
     * Create login session
     */
    public static void createUserLoginSession() {

        editor = mSharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(User.getObjUser());
        editor.putString(KEY_OBJECT_USER, json);
        editor.commit();
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // commit changes
        editor.commit();
    }


    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
    }

    /**
     * Get stored session data
     */
    public static void getUserDetails() {
        Gson gson = new Gson();
        String json = mSharedPref.getString(KEY_OBJECT_USER, "");
        User.getInstance().setObjUser(gson.fromJson(json, User.class));
    }


    /**
     * Clear session details
     */

    public static void logoutUser() {
        editor = mSharedPref.edit();
        User.getInstance().setObjUser(null);
        String oldToken = getKeyFirebaseToken();
        editor.clear();
        editor.commit();
        saveFireBaseToken(oldToken);
    }

    public static boolean ispUSHNotificationAllowed() {
        return mSharedPref.getBoolean(KEY_ALLOW_PUSH_NOTIFICATIONS, true);
    }

    public static int isPushNotificationAllowedInt() {
        return mSharedPref.getBoolean(KEY_ALLOW_PUSH_NOTIFICATIONS, true) ? 1 : 0;
    }

    public static void setPushNotificationAllowed(boolean NotificationAllowed) {
        editor = mSharedPref.edit();
        editor.putBoolean(KEY_ALLOW_PUSH_NOTIFICATIONS, NotificationAllowed);
        editor.commit();
    }

    public static boolean isEmailNotificationAllowed() {
        return mSharedPref.getBoolean(KEY_ALLOW_EMAIL_NOTIFICATIONS, true);
    }

    public static void setEmailNotificationAllowed(boolean NotificationAllowed) {
        editor = mSharedPref.edit();
        editor.putBoolean(KEY_ALLOW_EMAIL_NOTIFICATIONS, NotificationAllowed);
        editor.commit();
    }

    public static int isEmailNotificationAllowedInt() {
        return mSharedPref.getBoolean(KEY_ALLOW_EMAIL_NOTIFICATIONS, true) ? 1 : 0;
    }

    public static boolean isLoggedInAndLogin(Activity activity) {
        if (isLoggedIn()) {
            return true;
        } else {
            new OnLineDialog(activity) {
                @Override
                public void onPositiveButtonClicked() {
                    dismiss();
                    activity.startActivity(LoginActivity.newIntent(activity, ""));
                }

                @Override
                public void onNegativeButtonClicked() {
                    dismiss();
                }
            }.showConfirmationDialog(DialogTypes.OK_CANCEL, activity.getResources().getString(R.string.login_is_required),
                    activity.getResources().getString(R.string.go_to_login));
            return false;
        }
    }
    public static boolean isLoggedIn() {
        return mSharedPref.getBoolean(IS_LOGIN, false);
    }

    public static String getLanguage() {
        return mSharedPref.getString(KEY_LANGUAGE, "en");
    }

    public static void setLanguage(String language) {
        editor = mSharedPref.edit();
        editor.putString(KEY_LANGUAGE, language);
        editor.commit();
    }

    public static String getisSetLanguage() {
        return mSharedPref.getString(KEY_LANGUAGE, "");
    }

    public static void saveFireBaseToken(String token) {
        editor = mSharedPref.edit();
        editor.putString(KEY_FIREBASE_TOKEN, token);
        editor.commit();
    }

    public static String getKeyFirebaseToken() {
        return mSharedPref.getString(KEY_FIREBASE_TOKEN, "");
    }

}