package com.selwantech.raheeb.ui.offersjourney.selling;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSellingBinding;
import com.selwantech.raheeb.enums.SellingItemClickTypes;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.SellingItemClick;
import com.selwantech.raheeb.model.Selling;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.SellingAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.ConfirmSoldFragmentDialog;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

public class SellingViewModel extends BaseViewModel<SellingNavigator, FragmentSellingBinding> implements SellingItemClick<Selling> {

    SellingAdapter sellingAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;
    boolean enableLoading = false;
    boolean isLoadMore = false;

    public <V extends ViewDataBinding, N extends BaseNavigator> SellingViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (SellingNavigator) navigation, (FragmentSellingBinding) viewDataBinding);
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

        getViewBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.VERTICAL, false));
        getViewBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        sellingAdapter = new SellingAdapter(getMyContext(), this, getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(sellingAdapter);
        sellingAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                sellingAdapter.addItem(null);
                sellingAdapter.notifyItemInserted(sellingAdapter.getItemCount() - 1);
                getViewBinding().recyclerView.scrollToPosition(sellingAdapter.getItemCount() - 1);
                setLoadMore(true);
                getData();
            }
        });
    }

    @Override
    public void onClick(Selling selling, int clickType, int position) {
        Bundle data = new Bundle();
        data.putSerializable(AppConstants.BundleData.SELLING_ITEM, selling);
        SellingItemClickTypes sellingItemClickTypes = SellingItemClickTypes.fromInt(clickType);
        switch (sellingItemClickTypes) {

            case ITEM_CLICK:
                Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
                        .navigate(R.id.productSellingDetailsFragment,data);
                break;
            case MARK_SOLD_CLICK:
                ConfirmSoldFragmentDialog dialog = new ConfirmSoldFragmentDialog.Builder().build();
                dialog.setMethodCallBack(new ConfirmSoldFragmentDialog.ConfirmSoldCallBack() {
                    @Override
                    public void confirmed() {
                        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                                .navigate(R.id.sellFragment, data);
                    }
                });
                dialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
                break;
            case SELL_FASTER_CLICK:

                break;
        }

    }

    public void getData() {
        if (!isLoadMore() && !isRefreshing() && !isRetry()) {
            enableLoading = true;
        }
        getDataManager().getProductService().getSelling(getMyContext(), enableLoading, isRefreshing ? 0 :
                sellingAdapter.getItemCount(), new APICallBack<ArrayList<Selling>>() {
            @Override
            public void onSuccess(ArrayList<Selling> response) {
                checkIsLoadMoreAndRefreshing(true);
                sellingAdapter.addItems(response);
                notifyAdapter();
            }

            @Override
            public void onError(String error, int errorCode) {
                if (sellingAdapter.getItemCount() == 0) {
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
                sellingAdapter.notifyDataSetChanged();
            }
        });
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
        sellingAdapter.remove(sellingAdapter.getItemCount() - 1);
        notifyAdapter();
        sellingAdapter.setLoaded();
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
            sellingAdapter.clearItems();
            getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.GONE);
            getViewBinding().swipeRefreshLayout.setEnabled(true);
        }
    }

    protected void finishRefreshing(boolean isSuccess) {
        if (isSuccess) {
            sellingAdapter.clearItems();
        }
        getViewBinding().swipeRefreshLayout.setRefreshing(false);
        setIsRefreshing(false);
    }
}
