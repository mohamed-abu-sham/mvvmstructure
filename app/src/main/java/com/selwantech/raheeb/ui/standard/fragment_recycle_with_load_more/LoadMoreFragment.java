package com.selwantech.raheeb.ui.standard.fragment_recycle_with_load_more;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentLoadMoreBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class LoadMoreFragment extends BaseFragment<FragmentLoadMoreBinding, LoadMoreViewModel> {

    private static final String TAG = LoadMoreFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private LoadMoreViewModel mHomeViewModel;
    private FragmentLoadMoreBinding mViewBinding;


    @Override
    public void onResume() {
        super.onResume();
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
        return R.layout.fragment_load_more;
    }

    @Override
    public LoadMoreViewModel getViewModel() {
        mHomeViewModel = (LoadMoreViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(LoadMoreViewModel.class, getViewDataBinding(), new Intent().putExtras(getArguments()));
        return mHomeViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }


    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mHomeViewModel.setUp();
    }
}
