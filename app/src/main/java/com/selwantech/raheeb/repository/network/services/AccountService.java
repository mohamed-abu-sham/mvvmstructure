package com.selwantech.raheeb.repository.network.services;

import android.content.Context;

import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.VerifyPhoneResponse;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.ApiClient;
import com.selwantech.raheeb.repository.network.ApiCallHandler.CustomObserverResponse;
import com.selwantech.raheeb.repository.network.ApiCallHandler.GeneralResponse;
import com.selwantech.raheeb.repository.network.ApiConstants;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public class AccountService {

    private static AccountService instance;
    private static int timeOutDB = 10;
    private DataApi mDataApi;

    private AccountService() {
        mDataApi = ApiClient.getRetrofitClient(ApiConstants.BASE_URL).create(DataApi.class);
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public void updateAvatar(Context mContext, boolean enableLoading , MultipartBody.Part image, APICallBack<User> apiCallBack) {
        getDataApi().updateAvatar(image)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<User>(mContext, enableLoading, apiCallBack));
    }

    public void getFollowing(Context mContext, boolean enableLoading , int skip, APICallBack<ArrayList<ProductOwner>> apiCallBack) {
        getDataApi().getFollowing(skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<ProductOwner>>(mContext, enableLoading, apiCallBack));
    }

    public void getFollowers(Context mContext, boolean enableLoading , int skip, APICallBack<ArrayList<ProductOwner>> apiCallBack) {
        getDataApi().getFollowers(skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<ProductOwner>>(mContext, enableLoading, apiCallBack));
    }

    public void getTwitterFriends(Context mContext, boolean enableLoading, int skip, APICallBack<ArrayList<ProductOwner>> apiCallBack) {
        getDataApi().getTwitterFriends(skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<ProductOwner>>(mContext, enableLoading, apiCallBack));
    }

    public void getProfile(Context mContext, boolean enableLoading , APICallBack<User> apiCallBack) {
        getDataApi().getProfile()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<User>(mContext, enableLoading, apiCallBack));
    }

    public void updatePushNotifications(Context mContext, boolean enableLoading, APICallBack<User> apiCallBack) {
        getDataApi().updatePushNotifications()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<User>(mContext, enableLoading, apiCallBack));
    }

    public void updateEmailNotifications(Context mContext, boolean enableLoading, APICallBack<User> apiCallBack) {
        getDataApi().updateEmailNotifications()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<User>(mContext, enableLoading, apiCallBack));
    }

    public void updateID(Context mContext, boolean enableLoading, MultipartBody.Part image, APICallBack<User> apiCallBack) {
        getDataApi().updateID(image)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<User>(mContext, enableLoading, apiCallBack));
    }

    public void updateLocation(Context mContext, boolean enableLoading, double lat, double lon, APICallBack<Object> apiCallBack) {
        getDataApi().updateLocation(lat, lon)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<Object>(mContext, enableLoading, apiCallBack));
    }

    public void getInviteToken(Context mContext, boolean enableLoading, APICallBack<VerifyPhoneResponse> apiCallBack) {
        getDataApi().getInviteToken()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<VerifyPhoneResponse>(mContext, enableLoading, apiCallBack));
    }

    public DataApi getDataApi() {
        return mDataApi;
    }

    public interface DataApi {

        @Multipart
        @POST(ApiConstants.apiAccountService.UPDATE_AVATAR)
        Single<Response<GeneralResponse<User>>> updateAvatar(@Part MultipartBody.Part avatar);

        @GET(ApiConstants.apiAccountService.FOLLOWING)
        Single<Response<GeneralResponse<ArrayList<ProductOwner>>>> getFollowing(@Query("skip") int skip);

        @GET(ApiConstants.apiAccountService.FOLLOWERS)
        Single<Response<GeneralResponse<ArrayList<ProductOwner>>>> getFollowers(@Query("skip") int skip);

        @GET(ApiConstants.apiAccountService.TWITTER_FRIENDS)
        Single<Response<GeneralResponse<ArrayList<ProductOwner>>>> getTwitterFriends(@Query("skip") int skip);

        @GET(ApiConstants.apiAppService.CURRENCY)
        Single<Response<GeneralResponse<String>>> getCurrency();

        @GET(ApiConstants.apiAccountService.GET_PROFILE)
        Single<Response<GeneralResponse<User>>> getProfile();

        @POST(ApiConstants.apiAccountService.PUSH_NOTIFICATIONS)
        Single<Response<GeneralResponse<User>>> updatePushNotifications();

        @POST(ApiConstants.apiAccountService.EMAIL_NOTIFICATIONS)
        Single<Response<GeneralResponse<User>>> updateEmailNotifications();

        @Multipart
        @POST(ApiConstants.apiAccountService.UPDATE_ID)
        Single<Response<GeneralResponse<User>>> updateID(@Part MultipartBody.Part avatar);

        @POST(ApiConstants.apiAccountService.UPDATE_LOCATION)
        Single<Response<GeneralResponse<Object>>> updateLocation(@Query("lat") double lat, @Query("lon") double lon);

        @POST(ApiConstants.apiAccountService.INVITE_TOKEN)
        Single<Response<GeneralResponse<VerifyPhoneResponse>>> getInviteToken();
    }
}


