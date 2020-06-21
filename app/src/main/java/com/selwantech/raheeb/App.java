package com.selwantech.raheeb;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.selwantech.raheeb.utils.LanguageUtils;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class App extends Application {

    private static App sInstance;

    public static App getInstance() {
        return sInstance;
    }

    /**
     * Part of @Dagger Framework injection to use activity
     */
    //    @Inject
//    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
//        ParseTwitterUtils.initialize(getResources().getString(R.string.twitter_api_key), getResources().getString(R.string.twitter_api_secret));
//        DaggerAppComponent.builder()
//                .application(this)
//                .build()
//                .inject(this);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.twitter_api_key)
                        , getResources().getString(R.string.twitter_api_secret)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        LanguageUtils.setAppLanguage(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        FirebaseApp.initializeApp(base);
        super.attachBaseContext(base);
    }
}
