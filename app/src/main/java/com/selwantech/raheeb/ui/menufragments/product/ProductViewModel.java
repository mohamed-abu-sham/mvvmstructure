package com.selwantech.raheeb.ui.menufragments.product;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.FilterPrice;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.adapter.HomeAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.FilterDateFragmentDialog;
import com.selwantech.raheeb.ui.dialog.FilterPriceFragmentDialog;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.ItemTouchCallBack;
import com.selwantech.raheeb.utils.SpacesItemDecoration;

public class ProductViewModel extends BaseViewModel<ProductNavigator, FragmentProductBinding> implements RecyclerClick<Product> {

    HomeAdapter homeAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;

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
        getViewBinding().recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        getViewBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        homeAdapter = new HomeAdapter(getMyContext(), this,getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(homeAdapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        getViewBinding().recyclerView.addItemDecoration(decoration);
        ItemTouchHelper ith = new ItemTouchHelper(new ItemTouchCallBack(homeAdapter,homeAdapter.getArrayList()));
//        ith.attachToRecyclerView(getViewBinding().recyclerView);
    }

    public void getData() {

        homeAdapter.addItem(new Product(R.drawable.app_background));
        homeAdapter.addItem(new Product(R.drawable.logo));
        homeAdapter.addItem(new Product(R.drawable.ic_arrow_back));
        homeAdapter.addItem(new Product(R.drawable.head));
        homeAdapter.addItem(new Product(R.drawable.app_background));
        homeAdapter.addItem(new Product(R.drawable.t_shirt));
        homeAdapter.addItem(new Product(R.drawable.shirt));
        homeAdapter.addItem(new Product(R.drawable.t_shirt));
        homeAdapter.addItem(new Product(R.drawable.head));
        homeAdapter.addItem(new Product(R.drawable.shirt));
        homeAdapter.addItem(new Product(R.drawable.app_background));
        notifyAdapter();

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
//                        homeAdapter.addItems(response.getCategoryList());
//                        notifiAdapter();
//                        if (response.getSliderList().size() > 0) {
//                            setUpViewPager(response.getSliderList());
//                        }
//                    }
//
//                    @Override
//                    public void onError(String error, int errorCode) {
//                        if (homeAdapter.getItemCount() == 0) {
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
    }

    private void showNoDataFound() {
        getViewBinding().swipeRefreshLayout.setEnabled(false);
        getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.VISIBLE);

    }

    public void onFilterPriceClicked(){
        FilterPriceFragmentDialog dialog = new FilterPriceFragmentDialog.Builder().build();
        dialog.setMethodCallBack(new FilterPriceFragmentDialog.FilterPriceCallBack() {
            @Override
            public void callBack(FilterPrice dateAndTime) {

            }
        });
        dialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }

    public void onFilterDateClicked(){
        FilterDateFragmentDialog dialog = new FilterDateFragmentDialog.Builder().build();
        dialog.setMethodCallBack(new FilterDateFragmentDialog.FilterDateCallBack() {
            @Override
            public void callBack(int filterId) {

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
