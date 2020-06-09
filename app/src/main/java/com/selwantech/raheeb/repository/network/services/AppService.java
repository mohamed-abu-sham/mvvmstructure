package com.selwantech.raheeb.repository.network.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selwantech.raheeb.helper.FilterTypeAdapter;
import com.selwantech.raheeb.model.Condition;
import com.selwantech.raheeb.model.Distance;
import com.selwantech.raheeb.model.FilterProduct;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.ApiClient;
import com.selwantech.raheeb.repository.network.ApiCallHandler.CustomObserverResponse;
import com.selwantech.raheeb.repository.network.ApiCallHandler.GeneralResponse;
import com.selwantech.raheeb.repository.network.ApiConstants;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.http.GET;


public class AppService {

    private static AppService instance;
    private static int timeOutDB = 10;
    Context mContext;
    Gson gson;
    private DataApi mDataApi;

    private AppService() {
        gson = new GsonBuilder().registerTypeAdapter(FilterProduct.class, new FilterTypeAdapter()).create();
        mDataApi = ApiClient.getRetrofitClient(ApiConstants.BASE_URL).create(DataApi.class);
    }

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    public void getDistances(Context mContext, boolean enableLoading, APICallBack<ArrayList<Distance>> apiCallBack) {
        getDataApi().getDistances()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Distance>>(mContext, enableLoading, apiCallBack));
    }

    public void getConditions(Context mContext, boolean enableLoading, APICallBack<ArrayList<Condition>> apiCallBack) {
        getDataApi().getConditions()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Condition>>(mContext, enableLoading, apiCallBack));
    }

    public void getCurrency(Context mContext, boolean enableLoading, APICallBack<String> apiCallBack) {
        getDataApi().getCurrency()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }


    public DataApi getDataApi() {
        return mDataApi;
    }

    public interface DataApi {

        @GET(ApiConstants.apiAppService.DISTANCES)
        Single<Response<GeneralResponse<ArrayList<Distance>>>> getDistances();

        @GET(ApiConstants.apiAppService.CONDITION)
        Single<Response<GeneralResponse<ArrayList<Condition>>>> getConditions();

        @GET(ApiConstants.apiAppService.CURRENCY)
        Single<Response<GeneralResponse<String>>> getCurrency();




    }
}


