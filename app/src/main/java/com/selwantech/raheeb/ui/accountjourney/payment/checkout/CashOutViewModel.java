package com.selwantech.raheeb.ui.accountjourney.payment.checkout;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentCashOutBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;

public class CashOutViewModel extends BaseViewModel<CashOutNavigator, FragmentCashOutBinding> {

    ArrayAdapter<String> banks;

    public <V extends ViewDataBinding, N extends BaseNavigator> CashOutViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (CashOutNavigator) navigation, (FragmentCashOutBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        getCurrency();
        setUpSpinner();
    }

    private void setUpSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(getMyContext().getResources().getString(R.string.success));
        arrayList.add(getMyContext().getResources().getString(R.string.success));
        banks = new ArrayAdapter<String>(getMyContext(), android.R.layout.simple_spinner_item, arrayList);
        banks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getViewBinding().spinnerBanks.setAdapter(banks);
    }

    private void getCurrency() {
        getDataManager().getAppService().getCurrency(getMyContext(), true, new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                getViewBinding().tvCurrency.setText(response);
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getString(R.string.error),
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

    public void onConfirmClicked() {
//        if (isValid()) {
//            getDataManager().getProductService().buyNow(getMyContext(), true,
//                    getNavigator().getProduct().getId(), buyNow, new APICallBack<String>() {
//                        @Override
//                        public void onSuccess(String response) {
//                            Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
//                                    .navigate(R.id.action_reviewOfferFragment_to_productsHolderFragment);
//                        }
//
//                        @Override
//                        public void onError(String error, int errorCode) {
//                            showSnackBar(getMyContext().getResources().getString(R.string.error),
//                                    error,
//                                    getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
//                                        @Override
//                                        public void onActionClick(Snackbar snackbar) {
//                                            snackbar.dismiss();
//                                        }
//                                    });
//                        }
//                    });
//        }
    }
}
