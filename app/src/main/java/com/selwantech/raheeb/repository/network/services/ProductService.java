package com.selwantech.raheeb.repository.network.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selwantech.raheeb.helper.FilterTypeAdapter;
import com.selwantech.raheeb.model.FilterProduct;
import com.selwantech.raheeb.model.Product;
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
import retrofit2.http.Query;


public class ProductService {

    private static ProductService instance;
    private static int timeOutDB = 10;
    Context mContext;
    private DataApi mDataApi;
    Gson gson;
    private ProductService() {
        gson = new GsonBuilder().registerTypeAdapter(FilterProduct.class, new FilterTypeAdapter()).create();
        mDataApi = ApiClient.getRetrofitClient(ApiConstants.BASE_URL).create(DataApi.class);
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    public void getProducts(Context mContext, boolean enableLoading, APICallBack<ArrayList<Product>> apiCallBack) {

        getDataApi().getProducts(gson.toJson(FilterProduct.getInstance()))
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Product>>(mContext, enableLoading, apiCallBack));
    }


    public DataApi getDataApi() {
        return mDataApi;
    }

    public interface DataApi {

        //        @Body FilterProduct filterProduct
        @GET(ApiConstants.apiProductService.PRODUCTS)
        Single<Response<GeneralResponse<ArrayList<Product>>>> getProducts(@Query("data") String filterProduct);


    }
}


