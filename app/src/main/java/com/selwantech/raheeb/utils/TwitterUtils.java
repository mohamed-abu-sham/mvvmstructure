package com.selwantech.raheeb.utils;

import android.app.Activity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.model.User;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import retrofit2.Call;

public class TwitterUtils {

    Activity activity;
    TwitterCallback twitterCallback;
    private TwitterAuthClient twitterAuthClient;

    public TwitterUtils(Activity activity, TwitterCallback twitterCallback) {
        this.activity = activity;
        this.twitterCallback = twitterCallback;
        initTwitter();
    }

    public void initTwitter() {
        TwitterConfig config = new TwitterConfig.Builder(activity)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(activity.getResources().getString(R.string.twitter_api_key)
                        , activity.getResources().getString(R.string.twitter_api_secret)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        this.twitterAuthClient = new TwitterAuthClient();

    }

    public void twitterClick() {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if (twitterSession == null) {
            twitterAuthClient.authorize(activity, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    TwitterAuthToken twitterAuthToken = session.getAuthToken();
//                    twitterAuthToken;
                    TwitterSession twitterSession = result.data;
                    getTwitterData(twitterSession);

                }

                @Override
                public void failure(TwitterException e) {
                    Log.e("Twitter", "Failed to authenticate user " + e.getMessage());
                }
            });
        } else {
            getTwitterData(twitterSession);
        }
    }

    private void getTwitterData(final TwitterSession twitterSession) {
        TwitterApiClient twitterApiClient = new TwitterApiClient(twitterSession);
        final Call<com.twitter.sdk.android.core.models.User> getUserCall = twitterApiClient.getAccountService().verifyCredentials(true, false, true);
        getUserCall.enqueue(new Callback<com.twitter.sdk.android.core.models.User>() {
            @Override
            public void success(Result<com.twitter.sdk.android.core.models.User> result) {

                com.twitter.sdk.android.core.models.User twitterUser = result.data;
                User userObj = User.getInstance();
                userObj.setSocial_id(twitterUser.idStr);
                userObj.setEmail(twitterUser.email);
                userObj.setAvatar(twitterUser.profileImageUrl);
                userObj.setLogin_with("twitter");
                userObj.setName(twitterUser.name);
                twitterCallback.twitterUser(userObj);
                CookieSyncManager.createInstance(activity);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeSessionCookie();
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Twitter", "Failed to get user data " + exception.getMessage());
            }
        });
    }

    public TwitterAuthClient getTwitterAuthClient() {
        return twitterAuthClient;
    }

    public interface TwitterCallback {
        void twitterUser(User user);
    }

}
