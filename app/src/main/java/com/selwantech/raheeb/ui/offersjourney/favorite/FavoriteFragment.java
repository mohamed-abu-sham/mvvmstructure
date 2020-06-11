package com.selwantech.raheeb.ui.offersjourney.favorite;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentFavoriteBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;


public class FavoriteFragment extends BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>
        implements FavoriteNavigator {

    private static final String TAG = FavoriteFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private FavoriteViewModel mHomeViewModel;
    private FragmentFavoriteBinding mViewBinding;

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
        return R.layout.fragment_favorite;
    }

    @Override
    public FavoriteViewModel getViewModel() {
        mHomeViewModel = (FavoriteViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(FavoriteViewModel.class, getViewDataBinding(), this);
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

    public void reloadData() {
        mHomeViewModel.reloadData();
    }
}
