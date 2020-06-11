package com.selwantech.raheeb.repository.network.services;

import android.content.Context;

import com.selwantech.raheeb.model.BoxSize;
import com.selwantech.raheeb.model.Category;
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
import retrofit2.http.Path;


public class CategoryService {

    private static CategoryService instance;
    private static int timeOutDB = 10;
    Context mContext;
    private DataApi mDataApi;

    private CategoryService() {
        mDataApi = ApiClient.getRetrofitClient(ApiConstants.BASE_URL).create(DataApi.class);
    }

    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryService();
        }
        return instance;
    }

    public void getAllCategories(Context mContext, boolean enableLoading, APICallBack<ArrayList<Category>> apiCallBack) {
        getDataApi().getAllCategories()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Category>>(mContext, enableLoading, apiCallBack));
    }


    public void getCategoryBoxSize(Context mContext, boolean enableLoading, int categoryId, APICallBack<ArrayList<BoxSize>> apiCallBack) {
        getDataApi().getCategoryBoxSize(categoryId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<BoxSize>>(mContext, enableLoading, apiCallBack));
    }


    public DataApi getDataApi() {
        return mDataApi;
    }

    public interface DataApi {

        @GET(ApiConstants.apiCategoryService.ALL_CATEGORIES)
        Single<Response<GeneralResponse<ArrayList<Category>>>> getAllCategories();

        @GET(ApiConstants.apiCategoryService.CATEGORY_BOX_SIZE)
        Single<Response<GeneralResponse<ArrayList<BoxSize>>>> getCategoryBoxSize(@Path("categoryId") int categoryId);
    }
}


