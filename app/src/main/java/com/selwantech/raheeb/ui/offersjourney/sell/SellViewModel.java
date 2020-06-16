package com.selwantech.raheeb.ui.offersjourney.sell;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSellBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.SetSold;
import com.selwantech.raheeb.model.SoldTo;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.SoldToAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

public class SellViewModel extends BaseViewModel<SellNavigator, FragmentSellBinding>
        implements RecyclerClick<SoldTo> {

    SoldToAdapter soldToAdapter;

    public <V extends ViewDataBinding, N extends BaseNavigator> SellViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (SellNavigator) navigation, (FragmentSellBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpRecycler();
        getData();
    }

    private void setUpRecycler() {
        getViewBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.VERTICAL, false));
        getViewBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        soldToAdapter = new SoldToAdapter(getMyContext(), this);
        getViewBinding().recyclerView.setAdapter(soldToAdapter);

    }

    @Override
    public void onClick(SoldTo soldTo, int position) {
        getViewBinding().radSoldElse.setChecked(false);
        getViewBinding().rating.setVisibility(View.VISIBLE);
    }

    public void onSoldElseClicked() {
        getViewBinding().radSoldElse.setChecked(true);
        soldToAdapter.setSoldElse();
        getViewBinding().rating.setVisibility(View.GONE);
    }

    private void getData() {
        getDataManager().getProductService().getInteractedPeople(getMyContext(), true, getNavigator().getSellingItem().getId(), new APICallBack<ArrayList<SoldTo>>() {
            @Override
            public void onSuccess(ArrayList<SoldTo> response) {
                response.get(0).setSelected(true);
                soldToAdapter.addItems(response);
                soldToAdapter.setSelectedItemPosition(0);
                notifyAdapter();
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error,
                        getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }

    public void onSubmitClick() {
        if (getViewBinding().radSoldElse.isChecked()) {
            setSold(new SetSold());
        } else {
            setSold(new SetSold(soldToAdapter.getSelectedItem().getId(), (int) getViewBinding().rating.getRating()));
        }

    }

    public void setSold(SetSold sold) {
        getDataManager().getProductService().setSold(getMyContext(), true,
                getNavigator().getSellingItem().getId(), sold
                , new APICallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                                .navigate(R.id.action_sellFragment_to_nav_home);
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

    private void notifyAdapter() {
        getViewBinding().recyclerView.post(new Runnable() {
            @Override
            public void run() {
                soldToAdapter.notifyDataSetChanged();
            }
        });
    }

}
