package com.selwantech.raheeb.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentCategoryBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Category;
import com.selwantech.raheeb.model.Slider;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.adapter.CategoryAdapter;
import com.selwantech.raheeb.ui.adapter.HomeAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SpacesItemDecoration;

public class CategoryViewModel extends BaseViewModel<CategoryNavigator, FragmentCategoryBinding> implements RecyclerClick<Category> {

    CategoryAdapter categoryAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;

    public <V extends ViewDataBinding, N extends BaseNavigator> CategoryViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (CategoryNavigator) navigation, (FragmentCategoryBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpRecycler();
        getData();
        getViewBinding().layoutNoDataFound.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRetring();
                getData();
            }
        });
    }

    private void setUpRecycler() {
        getViewBinding().swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setIsRefreshing(true);
                getData();
            }
        });

        getViewBinding().recyclerView.setLayoutManager(new GridLayoutManager(getMyContext(), 3));
        getViewBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        getViewBinding().recyclerView.addItemDecoration(decoration);
        categoryAdapter = new CategoryAdapter(getMyContext(), this);
        getViewBinding().recyclerView.setAdapter(categoryAdapter);

    }


    public void getData() {
//        if (!isRefreshing() && !isRetry()) {
//            showLoading();
//        }
//        getDataManager().getHomeService().getDataApi().getHomeCategories()
//                .toObservable()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new CustomObserverResponse<Home>(new APICallBack<Home>() {
//                    @Override
//                    public void onSuccess(Home response) {
//                        checkIsLoadMoreAndRefreshing(true);
//                        categoryAdapter.addItems(response.getCategoryList());
//                        notifiAdapter();
//                        if (response.getSliderList().size() > 0) {
//                            setUpViewPager(response.getSliderList());
//                        }
//                    }
//
//                    @Override
//                    public void onError(String error, int errorCode) {
//                        if (categoryAdapter.getItemCount() == 0) {
//                            showNoDataFound();
//                        }
//                        showSnackBar(getMyContext().getString(R.string.error),
//                                error, getMyContext().getResources().getString(R.string.OK),
//                                new SnackViewBulider.SnackbarCallback() {
//                                    @Override
//                                    public void onActionClick(Snackbar snackbar) {
//                                        snackbar.dismiss();
//                                    }
//                                });
//                        checkIsLoadMoreAndRefreshing(false);
//                    }
//                }));

        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
        categoryAdapter.addItem(new Category());
    }

    private void showNoDataFound() {
        getViewBinding().swipeRefreshLayout.setEnabled(false);
        getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.VISIBLE);

    }

    private void notifiAdapter() {
        getViewBinding().recyclerView.post(new Runnable() {
            @Override
            public void run() {
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(Category category, int position) {
//        Bundle data = new Bundle();
//        data.putInt("categoryId", category.getId());
//        data.putString("categoryName", category.getName());
//        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_servicesFragment,
//                data);

    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setIsRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }

    public boolean isRetry() {
        return isRetry;
    }

    public void setRetry(boolean retry) {
        isRetry = retry;
    }

    private void checkIsLoadMoreAndRefreshing(boolean isSuccess) {
        if (isRefreshing()) {
            finishRefreshing(isSuccess);
        } else if (isRetry()) {
            finishRetry(isSuccess);
        } else {
            hideLoading();
        }
    }

    protected void setRetring() {
        getViewBinding().layoutNoDataFound.btnRetry.setVisibility(View.GONE);
        getViewBinding().layoutNoDataFound.progressBar.setVisibility(View.VISIBLE);
        setRetry(true);
    }

    protected void finishRetry(boolean isSuccess) {

        getViewBinding().layoutNoDataFound.progressBar.setVisibility(View.GONE);
        getViewBinding().layoutNoDataFound.btnRetry.setVisibility(View.VISIBLE);
        setRetry(false);
        if (isSuccess) {
            categoryAdapter.clearItems();
            getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.GONE);
            getViewBinding().swipeRefreshLayout.setEnabled(true);
        }
    }

    protected void finishRefreshing(boolean isSuccess) {
        if (isSuccess) {
            categoryAdapter.clearItems();
        }
        getViewBinding().swipeRefreshLayout.setRefreshing(false);
        setIsRefreshing(false);
    }
}
