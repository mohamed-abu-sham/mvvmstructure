package com.selwantech.raheeb.ui.userprofilejourney.myoffers;

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
import com.selwantech.raheeb.databinding.FragmentMyOffersBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.MyOffer;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.MyOffersAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

public class MyOffersViewModel extends BaseViewModel<MyOffersNavigator, FragmentMyOffersBinding> implements RecyclerClick<MyOffer> {

    MyOffersAdapter myOffersAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;
    boolean enableLoading = false;
    boolean isLoadMore = false;

    public <V extends ViewDataBinding, N extends BaseNavigator> MyOffersViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (MyOffersNavigator) navigation, (FragmentMyOffersBinding) viewDataBinding);
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
        myOffersAdapter = new MyOffersAdapter(getMyContext(), this, getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(myOffersAdapter);
        myOffersAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                myOffersAdapter.addItem(null);
                myOffersAdapter.notifyItemInserted(myOffersAdapter.getItemCount() - 1);
                getViewBinding().recyclerView.scrollToPosition(myOffersAdapter.getItemCount() - 1);
                setLoadMore(true);
                getData();
            }
        });
    }

    public void getData() {
        if (!isLoadMore() && !isRefreshing() && !isRetry()) {
            enableLoading = true;
        }
        getDataManager().getUserService().getMyOffers(getMyContext(), enableLoading,
                getNavigator().getUser().getId(), isRefreshing ? 0 :
                        myOffersAdapter.getItemCount(), new APICallBack<ArrayList<MyOffer>>() {
                    @Override
                    public void onSuccess(ArrayList<MyOffer> response) {
                        checkIsLoadMoreAndRefreshing(true);
                        myOffersAdapter.addItems(response);
                        notifyAdapter();
                    }

                    @Override
                    public void onError(String error, int errorCode) {
                        if (myOffersAdapter.getItemCount() == 0) {
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
                myOffersAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(MyOffer myOffer, int position) {
        Bundle data = new Bundle();
        data.putSerializable(AppConstants.BundleData.CHAT_ID, myOffer.getChatId());
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.chatFragment, data);
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
        myOffersAdapter.remove(myOffersAdapter.getItemCount() - 1);
        myOffersAdapter.notifyItemRemoved(myOffersAdapter.getItemCount());
        myOffersAdapter.setLoaded();
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
            myOffersAdapter.clearItems();
            getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.GONE);
            getViewBinding().swipeRefreshLayout.setEnabled(true);
        }
    }

    protected void finishRefreshing(boolean isSuccess) {
        if (isSuccess) {
            myOffersAdapter.clearItems();
        }
        getViewBinding().swipeRefreshLayout.setRefreshing(false);
        setIsRefreshing(false);
    }

}
