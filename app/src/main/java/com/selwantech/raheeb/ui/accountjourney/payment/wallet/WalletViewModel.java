package com.selwantech.raheeb.ui.accountjourney.payment.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentWalletBinding;
import com.selwantech.raheeb.enums.PaymentActions;
import com.selwantech.raheeb.interfaces.OnLoadMoreListener;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Transaction;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.Wallet;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.TransactionsAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class WalletViewModel extends BaseViewModel<WalletNavigator, FragmentWalletBinding>
        implements RecyclerClick<Transaction> {

    TransactionsAdapter transactionsAdapter;
    boolean isRefreshing = false;
    boolean isRetry = false;
    boolean enableLoading = false;
    boolean isLoadMore = false;
    boolean canLoadMore = false;
    boolean isFirstIn = true;

    public <V extends ViewDataBinding, N extends BaseNavigator> WalletViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (WalletNavigator) navigation, (FragmentWalletBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
//        setUpRecycler();
//        getData();
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
        transactionsAdapter = new TransactionsAdapter(getMyContext(), this, getViewBinding().recyclerView);
        getViewBinding().recyclerView.setAdapter(transactionsAdapter);
        transactionsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    transactionsAdapter.addItem(null);
                    notifyAdapter();
                    getViewBinding().recyclerView.scrollToPosition(transactionsAdapter.getItemCount() - 1);
                    setLoadMore(true);
                    getData();
                }
            }
        });
    }

    public void onCashOutClicked() {
        if (Double.parseDouble(User.getInstance().getBalance().getAmount()) > 0) {
            Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_walletFragment_to_cashOutFragment);
        }
    }

    public void onCashInClicked() {
        Bundle data = new Bundle();
        data.putInt(AppConstants.BundleData.PAYMENT_ACTION, PaymentActions.CASH_IN.getAction());
        if (Double.parseDouble(User.getInstance().getBalance().getAmount()) > 0) {
            Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_walletFragment_to_payFragment, data);
        }
    }

    public void getData() {
        if (!isLoadMore() && !isRefreshing() && !isRetry()) {
            enableLoading = true;
        }
        getDataManager().getAccountService().getWallet(getMyContext(), true, new APICallBack<Wallet>() {
            @Override
            public void onSuccess(Wallet response) {
                checkIsLoadMoreAndRefreshing(true);
                transactionsAdapter.addItems(response.getTransactionArrayList());
                notifyAdapter();
                canLoadMore = true;
            }

            @Override
            public void onError(String error, int errorCode) {
                if (isLoadMore) {
                    canLoadMore = false;
                }
                if (transactionsAdapter.getItemCount() == 0) {
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
                transactionsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(Transaction transaction, int position) {
//        Bundle data = new Bundle();
//        data.putSerializable(AppConstants.BundleData.CHAT, transaction);
//        data.putInt(AppConstants.BundleData.CHAT_POSITION, position);
//        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
//                .navigate(R.id.chatFragment, data);
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
            isFirstIn = false;
        }
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void finishLoadMore() {
        transactionsAdapter.remove(transactionsAdapter.getItemCount() - 1);
//        notifyAdapter();
        transactionsAdapter.notifyItemRemoved(transactionsAdapter.getItemCount());
        transactionsAdapter.setLoaded();
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
            transactionsAdapter.clearItems();
            getViewBinding().layoutNoDataFound.relativeNoData.setVisibility(View.GONE);
            getViewBinding().swipeRefreshLayout.setEnabled(true);
        }
    }

    protected void finishRefreshing(boolean isSuccess) {
        if (isSuccess) {
            transactionsAdapter.clearItems();
        }
        getViewBinding().swipeRefreshLayout.setRefreshing(false);
        setIsRefreshing(false);
    }


    public String balance() {
        return User.getInstance().getBalance().getFormatted();
    }
}
