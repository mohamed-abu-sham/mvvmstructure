package com.selwantech.raheeb.utils;

import android.app.Activity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.selwantech.raheeb.model.User;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import retrofit2.Call;

public class TwitterUtils {

    Activity activity;
    TwitterCallback twitterCallback;
    TwitterCallbackToConnect twitterCallbackToConnect;
    private TwitterAuthClient twitterAuthClient;
    boolean isRegister;

    public TwitterUtils(Activity activity, TwitterCallback twitterCallback, boolean isRegister) {
        this.activity = activity;
        this.twitterCallback = twitterCallback;
        this.isRegister = isRegister;
        initTwitter();
    }

    public TwitterUtils(Activity activity, TwitterCallbackToConnect twitterCallback, boolean isRegister) {
        this.activity = activity;
        this.twitterCallbackToConnect = twitterCallback;
        this.isRegister = isRegister;
        initTwitter();
    }

    public void initTwitter() {

//        ParseTwitterUtils.initialize(activity.getResources().getString(R.string.twitter_api_key), activity.getResources().getString(R.string.twitter_api_secret));

    }

    public void twitterClick() {

        this.twitterAuthClient = new TwitterAuthClient();


//        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        twitterAuthClient.authorize(activity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken twitterAuthToken = session.getAuthToken();
//                    twitterAuthToken;
                TwitterSession twitterSession = result.data;
                if (isRegister)
                    getTwitterDataToRegister(twitterSession);
                else
                    getTwitterDataToConnect(twitterSession);

            }

            @Override
            public void failure(TwitterException e) {
                Log.e("Twitter", "Failed to authenticate user " + e.getMessage());
                twitterAuthClient.cancelAuthorize();
            }
        });
    }

    private void getTwitterDataToRegister(final TwitterSession twitterSession) {
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
//                logoutTwitter();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Twitter", "Failed to get user data " + exception.getMessage());

            }
        });
    }

    private void getTwitterDataToConnect(final TwitterSession twitterSession) {
        TwitterApiClient twitterApiClient = new TwitterApiClient(twitterSession);
        final Call<com.twitter.sdk.android.core.models.User> getUserCall = twitterApiClient.getAccountService().verifyCredentials(true, false, true);
        getUserCall.enqueue(new Callback<com.twitter.sdk.android.core.models.User>() {
            @Override
            public void success(Result<com.twitter.sdk.android.core.models.User> result) {
                com.twitter.sdk.android.core.models.User twitterUser = result.data;
                twitterCallbackToConnect.twitterUser(twitterUser.idStr);
//                logoutTwitter();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Twitter", "Failed to get user data " + exception.getMessage());
            }
        });
    }

    private void logoutTwitter() {
        CookieSyncManager.createInstance(activity);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
    }

    public TwitterAuthClient getTwitterAuthClient() {
        return twitterAuthClient;
    }

    public interface TwitterCallback {
        void twitterUser(User user);
    }

    public interface TwitterCallbackToConnect {
        void twitterUser(String userID);
    }

}
