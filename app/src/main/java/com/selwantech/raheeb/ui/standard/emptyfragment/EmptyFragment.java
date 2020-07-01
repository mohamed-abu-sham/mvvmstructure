package com.selwantech.raheeb.ui.standard.emptyfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentEmptyBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.model.DataExample;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;


public class EmptyFragment extends BaseFragment<FragmentEmptyBinding, EmptyFragmentViewModel> {

    private static final String TAG = EmptyFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private EmptyFragmentViewModel mViewModel;
    private FragmentEmptyBinding mViewBinding;


    public static EmptyFragment newInstance() {
        Bundle args = new Bundle();
        EmptyFragment fragment = new EmptyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public boolean hideBottomSheet() {
        return false;
    }

    @Override
    public boolean isNeedActivityResult() {
        return false;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_empty;
    }

    @Override
    public EmptyFragmentViewModel getViewModel() {
        mViewModel = (EmptyFragmentViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(EmptyFragmentViewModel.class, getViewDataBinding(), new Intent().putExtras(getArguments()));
        return mViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
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
