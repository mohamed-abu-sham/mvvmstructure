package com.selwantech.raheeb.ui.offersjourney.favorite;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentFavoriteBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.HomeAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

public class FavoriteViewModel extends BaseViewModel<FavoriteNavigator, FragmentFavoriteBinding> implements RecyclerClick<Product> {

    HomeAdapter homeAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;
    boolean enableLoading = false;
    boolean isLoadMore = false;

    public <V extends ViewDataBinding, N extends BaseNavigator> FavoriteViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (FavoriteNavigator) navigation, (FragmentFavoriteBinding) viewDataBinding);
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

        getViewBinding().recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        getViewBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        homeAdapter = new HomeAdapter(getMyContext(), this, getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                homeAdapter.addItem(null);
                homeAdapter.notifyItemInserted(homeAdapter.getItemCount() - 1);
                getViewBinding().recyclerView.scrollToPosition(homeAdapter.getItemCount() - 1);
                setLoadMore(true);
                getData();
            }
        });

    }

    public void getData() {
        if (!isLoadMore() && !isRefreshing() && !isRetry()) {
            enableLoading = true;
        }
        getDataManager().getProductService().getFavorite(getMyContext(), enableLoading,
                isRefreshing ? 0 : homeAdapter.getItemCount(), new APICallBack<ArrayList<Product>>() {
                    @Override
                    public void onSuccess(ArrayList<Product> response) {
                        checkIsLoadMoreAndRefreshing(true);
                        homeAdapter.addItems(response);
                        notifyAdapter();
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        if (homeAdapter.getItemCount() == 0) {
                            showNoDataFound();
                        }
                        if (!isLoadMore) {
                            showSnackBar(getMyContext().getString(R.string.error),
                                    error, getMyContext().getResources().getString(R.string.ok),
                                    new SnackViewBulider.SnackbarCallback() {
                                        @Override
                                        public void onActionClick(Snackbar snackbar) {
                                            snackbar.dismiss();
                                        }
                                    });
                        }
                        checkIsLoadMoreAndRefreshing(false);
                    }
                });
    }

    private void showNoDataFound() {
        getViewBinding().swipeRefreshLayout.setEnabled(false);
        getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.VISIBLE);
    }


    private void notifyAdapter() {
        getViewBinding().recyclerView.post(new Runnable() {
            @Override
            public void run() {
                homeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(Product product, int position) {
        Bundle data = new Bundle();
        data.putSerializable(AppConstants.BundleData.PRODUCT, product);
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.productDetailsFragment, data);
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
        } else if (isLoadMore()) {
            finishLoadMore();
        } else {
            enableLoading = false;
        }
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void finishLoadMore() {
        homeAdapter.remove(homeAdapter.getItemCount() - 1);
        notifyAdapter();
        homeAdapter.setLoaded();
        setLoadMore(false);
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
            homeAdapter.clearItems();
            getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.GONE);
            getViewBinding().swipeRefreshLayout.setEnabled(true);
        }
    }

    protected void finishRefreshing(boolean isSuccess) {
        if (isSuccess) {
            homeAdapter.clearItems();
        }
        getViewBinding().swipeRefreshLayout.setRefreshing(false);
        setIsRefreshing(false);
    }

    public void reloadData() {
        getViewBinding().swipeRefreshLayout.setRefreshing(true);
        setIsRefreshing(true);
        getData();
    }
}
