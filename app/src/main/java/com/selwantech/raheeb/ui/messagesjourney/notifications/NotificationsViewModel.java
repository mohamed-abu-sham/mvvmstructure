package com.selwantech.raheeb.ui.messagesjourney.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentNotificationsBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.notificationsdata.Notification;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.NotificationsAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

public class NotificationsViewModel extends BaseViewModel<NotificationsNavigator, FragmentNotificationsBinding> implements RecyclerClick<Notification> {

    NotificationsAdapter notificationsAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;
    boolean enableLoading = false;
    boolean isLoadMore = false;

    public <V extends ViewDataBinding, N extends BaseNavigator> NotificationsViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (NotificationsNavigator) navigation, (FragmentNotificationsBinding) viewDataBinding);
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
        notificationsAdapter = new NotificationsAdapter(getMyContext(), this, getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(notificationsAdapter);
        notificationsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                notificationsAdapter.addItem(null);
                notificationsAdapter.notifyItemInserted(notificationsAdapter.getItemCount() - 1);
                getViewBinding().recyclerView.scrollToPosition(notificationsAdapter.getItemCount() - 1);
                setLoadMore(true);
                getData();
            }
        });
    }

    public void getData() {
        if (!isLoadMore() && !isRefreshing() && !isRetry()) {
            enableLoading = true;
        }
        getDataManager().getMessagesService().getNotifications(getMyContext(), enableLoading, isRefreshing ? 0 :
                notificationsAdapter.getItemCount(), new APICallBack<ArrayList<Notification>>() {
            @Override
            public void onSuccess(ArrayList<Notification> response) {
                checkIsLoadMoreAndRefreshing(true);
                notificationsAdapter.addItems(response);
                notifyAdapter();
            }

            @Override
            public void onError(String error, int errorCode) {
                if (notificationsAdapter.getItemCount() == 0) {
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
                notificationsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(Notification notification, int position) {
        Bundle data = new Bundle();
        data.putSerializable(AppConstants.BundleData.MY_OFFER, notification);
//        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
//                .navigate(R.id.productDetailsFragment, data);
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
        notificationsAdapter.remove(notificationsAdapter.getItemCount() - 1);
        notificationsAdapter.notifyItemRemoved(notificationsAdapter.getItemCount());
        notificationsAdapter.setLoaded();
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
            notificationsAdapter.clearItems();
            getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.GONE);
            getViewBinding().swipeRefreshLayout.setEnabled(true);
        }
    }

    protected void finishRefreshing(boolean isSuccess) {
        if (isSuccess) {
            notificationsAdapter.clearItems();
        }
        getViewBinding().swipeRefreshLayout.setRefreshing(false);
        setIsRefreshing(false);
    }

}
