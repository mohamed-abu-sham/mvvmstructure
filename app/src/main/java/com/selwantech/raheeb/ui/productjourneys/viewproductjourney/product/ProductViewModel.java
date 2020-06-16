package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.product;

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
import com.selwantech.raheeb.databinding.FragmentProductBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.FilterPrice;
import com.selwantech.raheeb.model.FilterProduct;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.HomeAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.FilterPriceFragmentDialog;
import com.selwantech.raheeb.ui.dialog.OrderByFragmentDialog;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

public class ProductViewModel extends BaseViewModel<ProductNavigator, FragmentProductBinding> implements RecyclerClick<Product> {

    HomeAdapter homeAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;
    boolean enableLoading = false;
    boolean isLoadMore = false;
    public <V extends ViewDataBinding, N extends BaseNavigator> ProductViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ProductNavigator) navigation, (FragmentProductBinding) viewDataBinding);
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
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        getViewBinding().recyclerView.setLayoutManager(staggeredGridLayoutManager);
        homeAdapter = new HomeAdapter(getMyContext(), this,getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                homeAdapter.addItem(null);
//                homeAdapter.notifyItemInserted(homeAdapter.getItemCount() - 1);
//                getViewBinding().recyclerView.scrollToPosition(homeAdapter.getItemCount() - 1);
                getViewBinding().swipeRefreshLayout.setRefreshing(true);
                setLoadMore(true);
                getData();
            }
        });
//        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
//        getViewBinding().recyclerView.addItemDecoration(decoration);
//        ItemTouchHelper ith = new ItemTouchHelper(new ItemTouchCallBack(homeAdapter,homeAdapter.getArrayList()));
//        ith.attachToRecyclerView(getViewBinding().recyclerView);
    }

    public void getData() {
        if (!isLoadMore() && !isRefreshing() && !isRetry()) {
            enableLoading = true;
        }
        getDataManager().getProductService().getProducts(getMyContext(), enableLoading,
                isLoadMore ? homeAdapter.getItemCount() : 0, new APICallBack<ArrayList<Product>>() {
            @Override
            public void onSuccess(ArrayList<Product> response) {
                checkIsLoadMoreAndRefreshing(true);
                homeAdapter.addItems(response);
                notifyAdapter();
                showNoDataFound(false);
            }

            @Override
            public void onError(String error, int errorCode) {
                if (!isLoadMore) {
                    showNoDataFound(true);
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

    private void showNoDataFound(boolean show) {
        getViewBinding().swipeRefreshLayout.setEnabled(!show);
        getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(show ? View.VISIBLE : View.GONE);

    }

    public void onFilterPriceClicked(){
        FilterPriceFragmentDialog dialog = new FilterPriceFragmentDialog.Builder().build();
        dialog.setMethodCallBack(new FilterPriceFragmentDialog.FilterPriceCallBack() {
            @Override
            public void callBack(FilterPrice filterPrice) {
                FilterProduct.getInstance().setPrice_min(filterPrice.getMin());
                FilterProduct.getInstance().setPrice_max(filterPrice.getMax());
                applyFilter();
            }
        });
        dialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }

    public void onFilterDateClicked(){
        OrderByFragmentDialog dialog = new OrderByFragmentDialog.Builder().build();
        dialog.setMethodCallBack(new OrderByFragmentDialog.OrderByCallBack() {
            @Override
            public void callBack(int filterId) {

                applyFilter();
            }
        });
        dialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
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

    public void applyFilter() {
        getViewBinding().swipeRefreshLayout.setRefreshing(true);
        setIsRefreshing(true);
        getData();
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
//        homeAdapter.remove(homeAdapter.getItemCount() - 1);
//        homeAdapter.notifyItemRemoved(homeAdapter.getItemCount() - 1);
        getViewBinding().swipeRefreshLayout.setRefreshing(false);
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

}
