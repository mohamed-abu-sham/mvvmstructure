package com.selwantech.raheeb.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.selwantech.raheeb.model.User;

public class SessionManager {
    private static final String PREF_NAME = "com.selwantech.spring_nights";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_OBJECT_USER = "objUser";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_ALLOW_NOTIFICATIONS = "allowNotifications";
    private static final String KEY_FIREBASE_TOKEN = "deviceToken";
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

    public static boolean getIsNotificationAllowed() {
        return mSharedPref.getBoolean(KEY_ALLOW_NOTIFICATIONS, true);
    }

    public static void setIsNotificationAllowed(boolean NotificationAllowed) {
        editor = mSharedPref.edit();
        editor.putBoolean(KEY_ALLOW_NOTIFICATIONS, NotificationAllowed);
        editor.commit();
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