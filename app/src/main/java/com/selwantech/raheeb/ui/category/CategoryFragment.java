package com.selwantech.raheeb.ui.category;

import android.content.Context;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.ViewModelProviderFactory;
import com.selwantech.raheeb.databinding.FragmentCategoryBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;

import javax.inject.Inject;


public class CategoryFragment extends BaseFragment<FragmentCategoryBinding, CategoryViewModel> implements CategoryNavigator {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private CategoryViewModel mHomeViewModel;
    private FragmentCategoryBinding mViewBinding;


    public static CategoryFragment newInstance() {
        Bundle args = new Bundle();
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public boolean hideBottomSheet() {
        return true;
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
        return R.layout.fragment_category;
    }

    @Override
    public CategoryViewModel getViewModel() {
        mHomeViewModel = (CategoryViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(CategoryViewModel.class, getViewDataBinding(), this);
        return mHomeViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }


    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar,TAG,R.string.categories);
        mHomeViewModel.setUp();
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void showToast(String message) {

    }

}
