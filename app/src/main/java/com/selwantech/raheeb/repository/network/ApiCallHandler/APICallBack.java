package com.selwantech.raheeb.repository.network.ApiCallHandler;

public interface APICallBack<T> {

    void onSuccess(T response);

    void onError(String error, int errorCode);
}
