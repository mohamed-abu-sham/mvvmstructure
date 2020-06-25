package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.reviewoffer;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentReviewOfferBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.helper.GeoCoderAddress;
import com.selwantech.raheeb.model.Address;
import com.selwantech.raheeb.model.BuyNow;
import com.selwantech.raheeb.model.PriceDetails;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.io.IOException;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;

public class ReviewOfferViewModel extends BaseViewModel<ReviewOfferNavigator, FragmentReviewOfferBinding> {


    BuyNow buyNow;

    public <V extends ViewDataBinding, N extends BaseNavigator> ReviewOfferViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ReviewOfferNavigator) navigation, (FragmentReviewOfferBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setData();
//        getViewBinding().linearGooglePay.setAlpha((float) 0.5);
//        GeneralFunction.enableDisableViewHolder(getViewBinding().linearGooglePay, false);
        getViewBinding().radCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buyNow.setPayment_method("cash");
                    getViewBinding().radOnline.setChecked(false);
                    getViewBinding().radWallet.setChecked(false);
                }

            }
        });
        getViewBinding().radOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buyNow.setPayment_method("online");
                    getViewBinding().radCash.setChecked(false);
                    getViewBinding().radWallet.setChecked(false);
                }
            }
        });
        getViewBinding().radWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buyNow.setPayment_method("wallet");
                    getViewBinding().radOnline.setChecked(false);
                    getViewBinding().radCash.setChecked(false);
                }
            }
        });
    }

    public int isShipping() {
        return getNavigator().getProduct().isIsShipNationwide() ?
                View.VISIBLE : View.GONE;
    }

    public void onConfirmClicked() {
        if (isValid()) {
            getDataManager().getProductService().buyNow(getMyContext(), true,
                    getNavigator().getProduct().getId(), buyNow, new APICallBack<String>() {
                        @Override
                        public void onSuccess(String response) {
                            Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                                    .navigate(R.id.action_reviewOfferFragment_to_productsHolderFragment);
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
    }

    private boolean isValid() {
        int error = 0;
        if (getNavigator().getProduct().isIsShipNationwide()) {
            if (buyNow.getAddress() == null) {
                showSnackBar(getMyContext().getResources().getString(R.string.warning),
                        getMyContext().getResources().getString(R.string.please_choose_shipping_address),
                        getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
                error = +1;
            }
        }

        if (buyNow.getPayment_method().isEmpty()) {
            showToast(getMyContext().getResources().getString(R.string.please_choose_payment_method));
            error = +1;
        }
        return error == 0;
    }

    public void onGooglePayClicked() {

    }

    private void setData() {
        buyNow = new BuyNow();
        if (getNavigator().getProduct().isIsShipNationwide()) {
            getViewBinding().tvLocation.setVisibility(View.VISIBLE);
            getViewBinding().viewLocation.setVisibility(View.VISIBLE);
            getViewBinding().tvLocation.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_shipping_blue, 0, R.drawable.ic_question_blue, 0);
            getViewBinding().tvLocation.setText(Html.fromHtml(getLocationText()));
            getViewBinding().tvShippingAddress.setVisibility(View.VISIBLE);
            getViewBinding().viewShippingAddress.setVisibility(View.VISIBLE);
            getViewBinding().itemView.setVisibility(View.VISIBLE);
            getViewBinding().tvLocationTitle.setVisibility(View.VISIBLE);
        }
        getData();
        setGooglePayDis();


    }

    private void setGooglePayDis() {
        StringBuilder description = new StringBuilder();
        description.append(getMyContext().getResources().getString(R.string.by_continuing_you_accept_raheeb));
        description.append(" ");
        description.append(GeneralFunction.textMultiColor(getMyContext().getResources().getString(R.string.terms_of_service), "#55ACEE"));
        getViewBinding().tvGooglePayDis.setText(Html.fromHtml(description.toString()));
    }

    private void getData() {
        getDataManager().getProductService().getPriceDetails(getMyContext(), true,
                getNavigator().getProduct().getId(), new APICallBack<PriceDetails>() {
                    @Override
                    public void onSuccess(PriceDetails response) {
                        getViewBinding().setPriceDetails(response);
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

    private String getLocationText() {
        StringBuilder location = new StringBuilder();
        location.append(getMyContext().getResources().getString(R.string.ships_from));
        location.append(getAddress(new LatLng(getNavigator().getProduct().getLat()
                , getNavigator().getProduct().getLon())));
        location.append(" ");
        location.append(getMyContext().getResources().getString(R.string.for_price));
        location.append(" ");
        location.append(GeneralFunction.textMultiColor(getNavigator().getProduct().getShipping_price().getFormatted(), "#55ACEE"));
        return location.toString();
    }

    private String getAddress(LatLng latLng) {
        try {
            return GeoCoderAddress.getInstance()
                    .getAddress(latLng).getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void onAddressChanged(Address address) {
        buyNow.setAddress(address);
        getViewBinding().tvShippingAddress.setText(getAddress(new LatLng(Double.valueOf(address.getLatitude())
                , Double.valueOf(address.getLongitude()))));
    }

    public void onAddAddressClicked() {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_reviewOfferFragment_to_shippingInfoFragment);
    }
}
