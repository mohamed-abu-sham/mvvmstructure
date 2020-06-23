package com.selwantech.raheeb.repository.network.services;

import android.content.Context;
import android.net.Uri;

import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.model.MyOffer;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.model.Rate;
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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class UserService {

    private static UserService instance;
    private static int timeOutDB = 10;
    Context mContext;
    private DataApi mDataApi;

    private UserService() {
        mDataApi = ApiClient.getRetrofitClient(ApiConstants.BASE_URL).create(DataApi.class);
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }


    public void getMyOffers(Context mContext, boolean enableLoading, int userId, int skip, APICallBack<ArrayList<MyOffer>> apiCallBack) {

        getDataApi().getOffers(userId, skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<MyOffer>>(mContext, enableLoading, apiCallBack));
    }


    public void getUser(Context mContext, boolean enableLoading, int userId, APICallBack<ProductOwner> apiCallBack) {
        getDataApi().getUser(userId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ProductOwner>(mContext, enableLoading, apiCallBack));
    }

    public void followUser(Context mContext, boolean enableLoading, int userId, APICallBack<String> apiCallBack) {
        getDataApi().followUser(userId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }
    public void unfollowUser(Context mContext, boolean enableLoading, int userId, APICallBack<String> apiCallBack) {
        getDataApi().unfollowUser(userId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void rateUser(Context mContext, boolean enableLoading, int userId, Rate rate, APICallBack<String> apiCallBack) {
        getDataApi().rateUser(userId, rate)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void sendReport(Context mContext, boolean enableLoading, int productId, String message ,APICallBack<String> apiCallBack) {
        getDataApi().sendReport(productId, message)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }
    public void sendReportWithImage(Context mContext, boolean enableLoading, int productId , String message, Uri image, APICallBack<String> apiCallBack) {
        getDataApi().sendReportWithImage(productId,message, GeneralFunction.getImageMultipart(image.getPath(),"image"))
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public DataApi getDataApi() {
        return mDataApi;
    }

    public interface DataApi {

        @GET(ApiConstants.apiUserService.GET_MY_OFFERS)
        Single<Response<GeneralResponse<ArrayList<MyOffer>>>> getOffers(@Path("userId") int userId, @Query("skip") int skip);

        @GET(ApiConstants.apiUserService.GET_USER)
        Single<Response<GeneralResponse<ProductOwner>>> getUser(@Path("userId") int productId);

        @POST(ApiConstants.apiUserService.FOLLOW_USER)
        Single<Response<GeneralResponse<String>>> followUser(@Query("user_id") int productId);

        @POST(ApiConstants.apiUserService.UNFOLLOW_USER)
        Single<Response<GeneralResponse<String>>> unfollowUser(@Query("user_id") int productId);

        @POST(ApiConstants.apiUserService.RATE_USER)
        Single<Response<GeneralResponse<String>>> rateUser(@Path("user_id") int user_id, @Body Rate rate);

        @Multipart
        @POST(ApiConstants.apiUserService.SEND_REPORT)
        Single<Response<GeneralResponse<String>>> sendReportWithImage(@Path("user_id") int product_id,
                                                                      @Query("message ") String message,@Part MultipartBody.Part image);

        @POST(ApiConstants.apiUserService.SEND_REPORT)
        Single<Response<GeneralResponse<String>>> sendReport(@Path("user_id") int product_id,
                                                             @Query("message ") String message);
    }
}


