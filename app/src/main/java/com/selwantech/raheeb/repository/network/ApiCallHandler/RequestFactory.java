package com.selwantech.raheeb.repository.network.ApiCallHandler;

import android.content.Context;

import com.selwantech.raheeb.model.DataExample;
import com.selwantech.raheeb.ui.dialog.CustomDialogUtils;

import java.util.ArrayList;

import retrofit2.Call;


public class RequestFactory extends CustomDialogUtils {
    Context mContext;
    APICallBack apiResult;


    public RequestFactory(Context mContext, boolean withProgress, APICallBack apiResult) {
        super(mContext, withProgress, true);
        this.mContext = mContext;
        this.apiResult = apiResult;
    }

    public void getDataFromApi(ErrorHandlingCallAdapter.MyCall<GeneralResponse<ArrayList<DataExample>>> allData) {
        allData.enqueue(new ErrorHandlingCallAdapter.MyCallback<GeneralResponse<ArrayList<DataExample>>>() {
            @Override
            public void onResult(GeneralResponse<ArrayList<DataExample>> response) {
                apiResult.onSuccess(response.getData());
            }

            @Override
            public void onError(String spineError) {
                apiResult.onError(spineError, 0);
            }

            @Override
            public void unexpectedError(Call<GeneralResponse<ArrayList<DataExample>>> call, Throwable t) {
                apiResult.onError(t.getLocalizedMessage(), 0);
            }
        });
    }

}