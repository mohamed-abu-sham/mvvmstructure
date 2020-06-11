package com.selwantech.raheeb.repository.network.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.selwantech.raheeb.helper.FilterTypeAdapter;
import com.selwantech.raheeb.model.BuyNow;
import com.selwantech.raheeb.model.FilterProduct;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.model.PriceDetails;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.model.Selling;
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

    public void getProducts(Context mContext, boolean enableLoading, int skip, APICallBack<ArrayList<Product>> apiCallBack) {

        getDataApi().getProducts(gson.toJson(FilterProduct.getInstance()), skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Product>>(mContext, enableLoading, apiCallBack));
    }

    public void getProduct(Context mContext, boolean enableLoading, int productId, APICallBack<Product> apiCallBack) {

        getDataApi().getProduct(productId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<Product>(mContext, enableLoading, apiCallBack));
    }

    public void addFavorite(Context mContext, boolean enableLoading, int productId, APICallBack<String> apiCallBack) {

        getDataApi().addFavorite(productId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void removeFavorite(Context mContext, boolean enableLoading, int productId, APICallBack<String> apiCallBack) {
        getDataApi().removeFavorite(productId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void makeOffer(Context mContext, boolean enableLoading, int productId, double amount, APICallBack<String> apiCallBack) {
        getDataApi().makeOffer(productId, amount)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void getPriceDetails(Context mContext, boolean enableLoading, int productId, APICallBack<PriceDetails> apiCallBack) {
        getDataApi().getPriceDetails(productId)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<PriceDetails>(mContext, enableLoading, apiCallBack));
    }

    public void buyNow(Context mContext, boolean enableLoading, int productId, BuyNow buyNow, APICallBack<String> apiCallBack) {
        getDataApi().buyNow(productId, buyNow)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void createProduct(Context mContext, boolean enableLoading, Post post, APICallBack<String> apiCallBack) {
        getDataApi().createProduct(post.getImagesMultypart(), new Gson().toJson(post))
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<String>(mContext, enableLoading, apiCallBack));
    }

    public void getSelling(Context mContext, boolean enableLoading, int skip, APICallBack<ArrayList<Selling>> apiCallBack) {

        getDataApi().getSelling(skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Selling>>(mContext, enableLoading, apiCallBack));
    }

    public void getBuying(Context mContext, boolean enableLoading, int skip, APICallBack<ArrayList<Product>> apiCallBack) {
        getDataApi().getBuying(skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Product>>(mContext, enableLoading, apiCallBack));
    }

    public void getFavorite(Context mContext, boolean enableLoading, int skip, APICallBack<ArrayList<Product>> apiCallBack) {
        getDataApi().getFavorite(skip)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CustomObserverResponse<ArrayList<Product>>(mContext, enableLoading, apiCallBack));
    }

    public DataApi getDataApi() {
        return mDataApi;
    }

    public interface DataApi {

        @GET(ApiConstants.apiProductService.PRODUCTS)
        Single<Response<GeneralResponse<ArrayList<Product>>>> getProducts(@Query("data") String filterProduct, @Query("skip") int skip);

        @GET(ApiConstants.apiProductService.PRODUCT)
        Single<Response<GeneralResponse<Product>>> getProduct(@Path("productId") int productId);

        @GET(ApiConstants.apiProductService.PRICE_DETAILS)
        Single<Response<GeneralResponse<PriceDetails>>> getPriceDetails(@Path("productId") int productId);

        @POST(ApiConstants.apiProductService.ADD_FAVORITE)
        Single<Response<GeneralResponse<String>>> addFavorite(@Path("productId") int productId);

        @POST(ApiConstants.apiProductService.REMOVE_FAVORITE)
        Single<Response<GeneralResponse<String>>> removeFavorite(@Path("productId") int productId);

        @POST(ApiConstants.apiProductService.MAKE_OFFER)
        Single<Response<GeneralResponse<String>>> makeOffer(@Path("productId") int productId,
                                                            @Query("price") double amount);

        @POST(ApiConstants.apiProductService.BUY_NOW)
        Single<Response<GeneralResponse<String>>> buyNow(@Path("productId") int productId, @Body BuyNow buyNow);

        @Multipart
        @POST(ApiConstants.apiProductService.CREATE_PRODUCT)
        Single<Response<GeneralResponse<String>>> createProduct(@Part ArrayList<MultipartBody.Part> images
                , @Query("data") String data);

        @GET(ApiConstants.apiProductService.SELLING)
        Single<Response<GeneralResponse<ArrayList<Selling>>>> getSelling(@Query("skip") int skip);

        @GET(ApiConstants.apiProductService.BUYING)
        Single<Response<GeneralResponse<ArrayList<Product>>>> getBuying(@Query("skip") int skip);

        @GET(ApiConstants.apiProductService.FAVORITE)
        Single<Response<GeneralResponse<ArrayList<Product>>>> getFavorite(@Query("skip") int skip);

    }
}


