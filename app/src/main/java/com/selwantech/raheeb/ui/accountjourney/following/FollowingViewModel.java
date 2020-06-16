package com.selwantech.raheeb.ui.accountjourney.following;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentFollowingBinding;
import com.selwantech.raheeb.enums.FollowStatus;
import com.selwantech.raheeb.enums.SellingItemClickTypes;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.ItemClickWithType;
import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.model.Selling;
import com.selwantech.raheeb.model.SetSold;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.FollowAdapter;
import com.selwantech.raheeb.ui.adapter.SellingAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.ConfirmSoldFragmentDialog;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.selwantech.raheeb.utils.AppConstants.PRODUCT_STATUS.SOLD_OTHER_APP;

public class FollowingViewModel extends BaseViewModel<FollowingNavigator, FragmentFollowingBinding> implements ItemClickWithType<ProductOwner> {

    FollowAdapter followAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;
    boolean enableLoading = false;
    boolean isLoadMore = false;
    boolean canLoadMore = false ;
    public <V extends ViewDataBinding, N extends BaseNavigator> FollowingViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (FollowingNavigator) navigation, (FragmentFollowingBinding) viewDataBinding);
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
        followAdapter = new FollowAdapter(getMyContext(), this, getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(followAdapter);
        followAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(canLoadMore) {
//                    followAdapter.addItem(null);
//                    followAdapter.notifyItemInserted(followAdapter.getItemCount() - 1);
//                    getViewBinding().recyclerView.scrollToPosition(followAdapter.getItemCount() - 1);
//                    setLoadMore(true);
//                    getData();
                }
            }
        });
    }

    @Override
    public void onClick(ProductOwner user, int clickType, int position) {
        FollowStatus followStatus = FollowStatus.fromInt(clickType);
        switch (followStatus) {
            case FOLLOW:
                followUser(user.getId(),position);
                break;
            case UNFOLLOW:
                unfollowUser(user.getId(),position);
                break;
            case NON:
                Bundle data = new Bundle();
                data.putSerializable(AppConstants.BundleData.USER_ID,user.getId());
                Navigation.findNavController(getBaseActivity(),R.id.nav_host_fragment)
                        .navigate(R.id.userProfileFragment,data);
                break;
        }

    }


    private void followUser(int userId , int position) {
        getDataManager().getUserService().followUser(getMyContext(), true, userId, new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                followAdapter.getItem(position).setFollow(true);
                followAdapter.notifyItemChanged(position);
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok),
                        new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }

    private void unfollowUser(int userId , int position) {
        getDataManager().getUserService().unfollowUser(getMyContext(), true, userId, new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                followAdapter.getItem(position).setFollow(false);
                followAdapter.notifyItemChanged(position);
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok),
                        new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }

    public void getData() {
        if (!isLoadMore() && !isRefreshing() && !isRetry()) {
            enableLoading = true;
        }
        getDataManager().getAccountService().getFollowing(getMyContext(), enableLoading, isRefreshing ? 0 :
                followAdapter.getItemCount(), new APICallBack<ArrayList<ProductOwner>>() {
            @Override
            public void onSuccess(ArrayList<ProductOwner> response) {
                checkIsLoadMoreAndRefreshing(true);
                followAdapter.addItems(response);
                notifyAdapter();
                canLoadMore = true;
            }

            @Override
            public void onError(String error, int errorCode) {
                if (followAdapter.getItemCount() == 0) {
                    showNoDataFound();
                }
                if(isLoadMore){
                    canLoadMore = false;
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
                followAdapter.notifyDataSetChanged();
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
        followAdapter.remove(followAdapter.getItemCount() - 1);
        followAdapter.notifyItemRemoved(followAdapter.getItemCount());
        followAdapter.setLoaded();
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
            followAdapter.clearItems();
            getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.GONE);
            getViewBinding().swipeRefreshLayout.setEnabled(true);
        }
    }

    protected void finishRefreshing(boolean isSuccess) {
        if (isSuccess) {
            followAdapter.clearItems();
        }
        getViewBinding().swipeRefreshLayout.setRefreshing(false);
        setIsRefreshing(false);
    }
}
