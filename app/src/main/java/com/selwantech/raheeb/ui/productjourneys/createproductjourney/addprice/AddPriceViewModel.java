package com.selwantech.raheeb.ui.productjourneys.createproductjourney.addprice;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductPriceBinding;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

public class AddPriceViewModel extends BaseViewModel<AddPriceNavigator, FragmentAddProductPriceBinding> {

    public <V extends ViewDataBinding, N extends BaseNavigator> AddPriceViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (AddPriceNavigator) navigation, (FragmentAddProductPriceBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        getCurrency();
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

    public Post returnData() {
        if (isValid()) {
            getNavigator().getPost().setPrice(Double.valueOf(getViewBinding().edAmount.getText().toString()));
            return getNavigator().getPost();
        }
        return null;
    }

    private boolean isValid() {
        int error = 0;

        if (getViewBinding().edAmount.getText().toString().trim().isEmpty()) {
            getViewBinding().edAmount.setError(getMyContext().getResources().getString(R.string.this_fieled_is_required));
            error++;
        }
        if (Double.valueOf(getViewBinding().edAmount.getText().toString()) == 0) {
            getViewBinding().edAmount.setError(getMyContext().getResources().getString(R.string.amount_must_be_greater_than_0));
            error++;
        }

        return error == 0;
    }
}
