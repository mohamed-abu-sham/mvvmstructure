package com.selwantech.raheeb.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.ui.splashscreen.SplashScreenActivity;
import com.yariksoffice.lingver.Lingver;

import java.util.Locale;

public class LanguageUtils {

    public static void setAppLanguage(Application application) {
        String lang = getLanguage(application);
        Lingver.init(application, lang);

    }

    public static void updateLanguage(Activity activity, String lang) {
        Lingver.getInstance().setLocale(activity, lang);
        SessionManager.setLanguage(lang);
        activity.finishAffinity();
        activity.startActivity(SplashScreenActivity.newIntent(activity));
    }

    public static void setStyle(Context context, String lang) {
        if (lang.equals("ar")) {
            context.getTheme().applyStyle(R.style.AppThemeLight, true);
        } else {
            context.getTheme().applyStyle(R.style.AppThemeLight, true);
        }
    }

    public static String getLanguage(Context context) {
        SessionManager.init(context);
        if (SessionManager.getisSetLanguage().isEmpty()) {
            return Locale.getDefault().getDisplayLanguage().equals("العربية") ? "ar" : "en";
        } else {
            return SessionManager.getLanguage();
        }
    }

    public static void setTextViewStyle(TextView textView, Context context) {
        if (getLanguage(context).equals("ar")) {
            textView.setTextAppearance(context, R.style.textview_arabic_style);
        } else {
            textView.setTextAppearance(context, R.style.bitter_regular);
        }
    }

    public static int getStyle(Context context) {
        String lang = getLanguage(context);
        if (lang.equals("ar")) {
            return R.style.AppThemeArabic;
        } else {
            return R.style.AppThemeEnglish;
        }
    }

    public static int setBlackStyle(Context context) {
        String lang = getLanguage(context);
        if (lang.equals("ar")) {
            return R.style.AppThemeBlackText;
        } else {
            return R.style.AppThemeBlackTextArabic;
        }
    }
}
