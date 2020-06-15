package com.selwantech.raheeb.repository.network.ApiCallHandler;

import android.content.Context;

import com.google.gson.Gson;
import com.selwantech.raheeb.App;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.ui.dialog.CustomDialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import retrofit2.Response;

public class CustomObserverResponse<T> extends CustomDialogUtils implements Observer<Response<GeneralResponse<T>>> {

    APICallBack apiCallBack;
    Context context;

    boolean withProgress;
    public CustomObserverResponse(Context mContext, boolean withProgress, APICallBack apiCallBack) {
        super(mContext, withProgress, false);
        context = mContext;
        this.apiCallBack = apiCallBack;
        this.withProgress = withProgress;
    }
//    public CustomObserverResponse(APICallBack apiCallBack) {
//        this.apiCallBack = apiCallBack;
//    }

    @Override
    public void onSubscribe(Disposable d) {
        if (withProgress) {
            showProgress();
        }
    }

    @Override
    public void onNext(Response<GeneralResponse<T>> generalResponseResponse) {

        if (generalResponseResponse.code() == 200 && !generalResponseResponse.body().getError()) {
            if (generalResponseResponse.body().getData() != null &&
                    !generalResponseResponse.body().getData().toString().equals("[]")){
                this.apiCallBack.onSuccess(generalResponseResponse.body().getData());
            }else if(generalResponseResponse.body().getMessage() != null &&
                    !generalResponseResponse.body().getMessage().equals("")) {
                this.apiCallBack.onSuccess(generalResponseResponse.body().getMessage());
            } else {
                this.apiCallBack.onError(App.getInstance().getApplicationContext()
                        .getResources().getString(R.string.no_data_available), 0);
            }
        } else {
            int code = generalResponseResponse.code();
            GeneralResponse errorBody = null;
            if (generalResponseResponse.errorBody() != null) {
                try {
                    JSONObject jsonObject = new JSONObject(generalResponseResponse.errorBody().string());
                    errorBody = new Gson().fromJson(jsonObject.toString(), GeneralResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            errorHandler(code, errorBody);
        }
    }


    @Override
    public void onError(Throwable e) {
        if (withProgress) {
            hideProgress();
        }
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int statusCode = httpException.code();
            // handle different HTTP error codes here (4xx)
            errorHandler(statusCode, null);
        } else if (e instanceof SocketTimeoutException) {
            // handle timeout from Retrofit
            this.apiCallBack.onError(App.getInstance().getApplicationContext()
                    .getResources().getString(R.string.no_internet_connection), 0);
        } else if (e instanceof IOException) {
            // file was not found, do something
            this.apiCallBack.onError(App.getInstance().getApplicationContext()
                    .getResources().getString(R.string.no_internet_connection), 0);
        }
    }

    @Override
    public void onComplete() {
        if (withProgress) {
            hideProgress();
        }
    }

    private void errorHandler(int code, GeneralResponse errorBody) {
        switch (code) {
            case 401:
            case 403:
                if (errorBody != null &&
                        errorBody.getMessage() != null &&
                        !errorBody.getMessage().isEmpty()) {
                    this.apiCallBack.onError(errorBody.getMessage(), code);
                } else
                    this.apiCallBack.onError(App.getInstance().getApplicationContext()
                            .getResources().getString(R.string.unauthorized), code);
                break;
            case 404:
                if (errorBody != null &&
                        errorBody.getMessage() != null &&
                        !errorBody.getMessage().isEmpty()) {
                    this.apiCallBack.onError(errorBody.getMessage(), code);
                } else
                    this.apiCallBack.onError(App.getInstance().getApplicationContext()
                            .getResources().getString(R.string.not_found), code);
                break;
            case 405:
                this.apiCallBack.onError(App.getInstance().getApplicationContext()
                        .getResources().getString(R.string.error_request_type), code);
                break;
            case 413:
                this.apiCallBack.onError(App.getInstance().getApplicationContext()
                        .getResources().getString(R.string.file_too_large), code);
                break;
            case 500:
                this.apiCallBack.onError(App.getInstance().getApplicationContext()
                        .getResources().getString(R.string.server_error), code);
                break;
            case 422:
                if (errorBody != null &&
                        errorBody.getMessage() != null &&
                        !errorBody.getMessage().isEmpty()) {
                    this.apiCallBack.onError(errorBody.getMessage(), code);
                } else
                    this.apiCallBack.onError(App.getInstance().getApplicationContext()
                            .getResources().getString(R.string.error_parsing_entity), code);
                break;
            default:
                if (errorBody != null &&
                        errorBody.getMessage() != null &&
                        !errorBody.getMessage().isEmpty()) {
                    this.apiCallBack.onError(errorBody.getMessage(), code);
                } else {
                    this.apiCallBack.onError(App.getInstance().getApplicationContext()
                            .getResources().getString(R.string.server_error), code);
                }
        }
    }


}
