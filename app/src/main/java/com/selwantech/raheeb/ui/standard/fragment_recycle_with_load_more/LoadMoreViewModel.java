package com.selwantech.raheeb.ui.standard.fragment_recycle_with_load_more;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentLoadMoreBinding;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.LoadMoreAdapter;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class LoadMoreViewModel extends BaseViewModel<FragmentLoadMoreBinding> implements RecyclerClick<Object> {

    LoadMoreAdapter adapter;
    boolean isRefreshing = false;
    boolean isRetry = false;
    boolean enableLoading = false;
    boolean isLoadMore = false;
    boolean canLoadMore = false ;

    public <V extends ViewDataBinding> LoadMoreViewModel(Context mContext, DataManager dataManager, V viewDataBinding, Intent intent) {
        super(mContext, dataManager, intent, (FragmentLoadMoreBinding) viewDataBinding);
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
        adapter = new LoadMoreAdapter(getMyContext(), this, getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(canLoadMore) {
                    adapter.addItem(null);
                    notifyAdapter();
                    getViewBinding().recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    setLoadMore(true);
                    getData();
                }
            }
        });
    }

    public void getData() {
//        if (!isLoadMore() && !isRefreshing() && !isRetry()) {
//            enableLoading = true;
//
//        }
//        getDataManager().getMessagesService().getChats(getMyContext(), enableLoading, isRefreshing ? 0 :
//                adapter.getItemCount(), new APICallBack<ArrayList<Object>>() {
//            @Override
//            public void onSuccess(ArrayList<Object> response) {
//                checkIsLoadMoreAndRefreshing(true);
//                adapter.addItems(response);
//                notifyAdapter();
//                canLoadMore = true;
//            }
//
//            @Override
//            public void onError(String error, int errorCode) {
//                if(isLoadMore){
//                    canLoadMore = false;
//                }
//                if (adapter.getItemCount() == 0) {
//                    showNoDataFound();
//                }
//                if (!isLoadMore) {
//                    showSnackBar(getMyContext().getString(R.string.error),
//                            error, getMyContext().getResources().getString(R.string.ok),
//                            new SnackViewBulider.SnackbarCallback() {
//                                @Override
//                                public void onActionClick(Snackbar snackbar) {
//                                    snackbar.dismiss();
//                                }
//                            });
//                }
//                checkIsLoadMoreAndRefreshing(false);
//            }
//        });
    }

    private void showNoDataFound() {
        getViewBinding().swipeRefreshLayout.setEnabled(false);
        getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.VISIBLE);

    }

    private void notifyAdapter() {
        getViewBinding().recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(Object object, int position) {

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
        adapter.remove(adapter.getItemCount() - 1);
//        notifyAdapter();
        adapter.notifyItemRemoved(adapter.getItemCount());
        adapter.setLoaded();
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
            adapter.clearItems();
            getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.GONE);
            getViewBinding().swipeRefreshLayout.setEnabled(true);
        }
    }

    protected void finishRefreshing(boolean isSuccess) {
        if (isSuccess) {
            adapter.clearItems();
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
