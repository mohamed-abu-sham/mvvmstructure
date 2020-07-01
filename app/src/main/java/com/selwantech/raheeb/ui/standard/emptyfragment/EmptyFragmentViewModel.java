package com.selwantech.raheeb.ui.standard.emptyfragment;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;

import com.selwantech.raheeb.databinding.FragmentEmptyBinding;
import com.selwantech.raheeb.model.DataExample;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.repository.network.ApiCallHandler.RequestFactory;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmptyFragmentViewModel extends BaseViewModel<FragmentEmptyBinding> {

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<DataExample>> dataExampleList;

    public <V extends ViewDataBinding> EmptyFragmentViewModel(Context mContext, DataManager dataManager, V viewDataBinding, Intent intent) {
        super(mContext, dataManager, intent, (FragmentEmptyBinding) viewDataBinding);
        isLoading = new MutableLiveData<>();
        dataExampleList = new MutableLiveData<>();
    }

    public void onNavBackClick() {

    }

    MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }

    MutableLiveData<List<DataExample>> getData() {
        return dataExampleList;
    }

    private void setData(List<DataExample> data) {
        this.dataExampleList.postValue(data);
    }

    void loadData() {
        getDataFromApi();
    }

    void onEmptyClicked() {
        setData(Collections.emptyList());
    }

    private void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }

    private void getDataFromApi() {
        new RequestFactory(getMyContext(), true, new APICallBack<ArrayList<DataExample>>() {
            @Override
            public void onSuccess(ArrayList<DataExample> response) {
                setData(response);
            }

            @Override
            public void onError(String error, int errorCode) {
                setData(Collections.emptyList());
                CommonUtils.showToast(getMyContext(), error);
            }
        }).getDataFromApi(getDataManager().getDataService().getDataApi().getAllData());
    }

    public void onBtnClick() {
//        setIsLoading(true);
    }

    @Override
    protected void setUp() {

    }
}
