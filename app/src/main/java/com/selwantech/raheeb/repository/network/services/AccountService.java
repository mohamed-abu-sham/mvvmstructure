package com.selwantech.raheeb.repository.network.services;

import android.content.Context;

import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.model.Condition;
import com.selwantech.raheeb.model.Distance;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.model.User;
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

    public void getCurrency(Context mContext, boolean enableLoading, APICallBack<String> apiCallBack) {
        getDataApi().getCurrency()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void getProfile(Context mContext, boolean enableLoading , APICallBack<User> apiCallBack) {
        getDataApi().getProfile()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<User>(mContext, enableLoading, apiCallBack));
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

        @GET(ApiConstants.apiAppService.CURRENCY)
        Single<Response<GeneralResponse<String>>> getCurrency();

        @GET(ApiConstants.apiAccountService.GET_PROFILE)
        Single<Response<GeneralResponse<User>>> getProfile();



    }
}


