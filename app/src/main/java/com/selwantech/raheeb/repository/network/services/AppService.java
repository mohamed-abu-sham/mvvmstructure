package com.selwantech.raheeb.repository.network.services;

import android.content.Context;
import android.net.Uri;

import com.selwantech.raheeb.helper.GeneralFunction;
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


public class AppService {

    private static AppService instance;
    private static int timeOutDB = 10;
    Context mContext;
    private DataApi mDataApi;

    private AppService() {
        mDataApi = ApiClient.getRetrofitClient(ApiConstants.BASE_URL).create(DataApi.class);
    }

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    public void sendHelp(Context mContext, boolean enableLoading, String message ,APICallBack<String> apiCallBack) {
        getDataApi().sendHelp(message)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }
    public void sendHelpWithImage(Context mContext, boolean enableLoading, String message, Uri image, APICallBack<String> apiCallBack) {
        getDataApi().sendHelpWithImage(message, GeneralFunction.getImageMultipart(image.getPath(),"image"))
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void getAbout(Context mContext, boolean enableLoading, APICallBack<String> apiCallBack) {
        getDataApi().about()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }
    public DataApi getDataApi() {
        return mDataApi;
    }

    public interface DataApi {

        @GET(ApiConstants.apiAppService.CURRENCY)
        Single<Response<GeneralResponse<String>>> getCurrency();

        @Multipart
        @POST(ApiConstants.apiAppService.TICKET)
        Single<Response<GeneralResponse<String>>> sendHelpWithImage(@Query("message") String message, @Part MultipartBody.Part image);

        @POST(ApiConstants.apiAppService.TICKET)
        Single<Response<GeneralResponse<String>>> sendHelp(@Query("message") String message);

        @GET(ApiConstants.apiAppService.ABOUT)
        Single<Response<GeneralResponse<String>>> about();

    }
}


