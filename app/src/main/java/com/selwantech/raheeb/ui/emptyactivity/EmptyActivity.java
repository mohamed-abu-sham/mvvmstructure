package com.selwantech.raheeb.ui.emptyactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ActivityEmptyBinding;
import com.selwantech.raheeb.model.DataExample;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

public class EmptyActivity extends BaseActivity<ActivityEmptyBinding, EmptyActivityViewModel>
        implements EmptyActivityNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private EmptyActivityViewModel mViewModel;
    private ActivityEmptyBinding mViewBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, EmptyActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    public EmptyActivityViewModel getViewModel() {
        mViewModel = (EmptyActivityViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(EmptyActivityViewModel.class, getViewDataBinding(), this);
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();

        mViewModel.getLoadingStatus().observe(this, new LoadingObserver());
        mViewModel.getData().observe(this, new DataObserver());
//        mEmptyViewModel.loadData();
    }


    //Observers
    private class LoadingObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if (isLoading == null) return;

            if (isLoading) {
//                progressBar.setVisibility(View.VISIBLE);
            } else {
//                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private class DataObserver implements Observer<List<DataExample>> {
        @Override
        public void onChanged(@Nullable List<DataExample> dataExampleList) {
            if (dataExampleList == null) return;
//            dataAdapter.setItems(movies);
//
//            if (dataExampleList.isEmpty()) {
//                emptyView.setVisibility(View.VISIBLE);
//            } else {
//                emptyView.setVisibility(View.GONE);
//            }
        }
    }
}