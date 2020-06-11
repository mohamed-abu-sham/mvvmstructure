package com.selwantech.raheeb.ui.productjourneys.viewproductjourney.productdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductDetailsBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.helper.GeoCoderAddress;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.ImagesItem;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.ProductImagesAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OfferFragmentDialog;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SmoothMoveMarker;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.io.IOException;

public class ProductDetailsViewModel extends
        BaseViewModel<ProductDetailsNavigator, FragmentProductDetailsBinding>
        implements OnMapReadyCallback, RecyclerClick<ImagesItem> {


    Product product;
    SupportMapFragment search_place_map;
    ProductImagesAdapter productImagesAdapter;
    private GoogleMap googleMap;
    private Marker PickUpMarker;

    public <V extends ViewDataBinding, N extends BaseNavigator> ProductDetailsViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ProductDetailsNavigator) navigation, (FragmentProductDetailsBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        search_place_map = getNavigator().getChildManager();
        setUpRecycler();
        if (getNavigator().getProductId() == -1) {
            product = getNavigator().getProduct();
            setTexts();
            initMap();
            product.getImages().get(0).setSelected(true);
            productImagesAdapter.addItems(product.getImages());
            setShippingView();
        } else {
            getProduct();
        }

    }

    private void setUpRecycler() {
        getViewBinding().recyclerViewProductImage.recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.HORIZONTAL, false));
        getViewBinding().recyclerViewProductImage.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
//        getViewBinding().recyclerViewProductImage.recyclerView.addItemDecoration(decoration);
        productImagesAdapter = new ProductImagesAdapter(getMyContext(), this);
        getViewBinding().recyclerViewProductImage.recyclerView.setAdapter(productImagesAdapter);

    }

    private void getProduct() {
        getDataManager().getProductService().getProduct(getMyContext(), true, getNavigator().getProductId(), new APICallBack<Product>() {
            @Override
            public void onSuccess(Product response) {
                product = response;
                getViewBinding().setData(product);
                setTexts();
                initMap();
                setShippingView();
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

    private void initMap() {
        if (null != search_place_map) {
            search_place_map.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        this.googleMap = gMap;
        if (googleMap != null) {
            LatLng currentLatLan = new LatLng(product.getLat(), product.getLon());
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentLatLan)
                    .zoom(21).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            MarkerOptions markerOpt = new MarkerOptions();
            markerOpt.position(currentLatLan);
            markerOpt.icon(SmoothMoveMarker.bitmapDescriptorFromVector(getMyContext(), R.drawable.ic_pin));
            PickUpMarker = googleMap.addMarker(markerOpt);
        }
    }

    public void setTexts() {

        getViewBinding().tvLocation.setText(Html.fromHtml(getLocationText()));
        getViewBinding().tvLocation2.setText(getAddress());
        if (product.isIsShipNationwide()) {
            getViewBinding().tvGroundShipping.setText(getMyContext().getResources().getString(R.string.ground_shipping) + " " + product.getShipping_price().getFormatted());
            getViewBinding().tvShippingProtectionDis.setText(Html.fromHtml(getShippingProtectionDescription()));
            getViewBinding().tvPriceDetails.setText(getPriceDetails());
            getViewBinding().tvShippedToYou.setText(getShippedToYouText());
        }
        if (product.getProductOwner().isIsValid()) {
            getViewBinding().linearOwnerInfo.setVisibility(View.VISIBLE);
            getViewBinding().tvOwnerRate.setText(product.getProductOwner().getRateString());
        }
    }

    public void setShippingView() {
        if (product.isIsShipNationwide()) {
            getViewBinding().tvLocation.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_shipping_blue, 0, R.drawable.ic_question_blue, 0);
            getViewBinding().linearShip.setVisibility(View.VISIBLE);
            getViewBinding().viewShip.setVisibility(View.VISIBLE);
            getViewBinding().linearShipProtection.setVisibility(View.VISIBLE);
            getViewBinding().viewShipProtection.setVisibility(View.VISIBLE);
            getViewBinding().linearPriceDetails.setVisibility(View.VISIBLE);
            getViewBinding().linearShippedToYou.setVisibility(View.VISIBLE);
        }
    }

    private String getShippedToYouText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getMyContext().getResources().getString(R.string.have_it_shipped_to_you_for));
        stringBuilder.append(" ");
        stringBuilder.append(product.getShipping_price().getFormatted());
        return stringBuilder.toString();
    }

    private String getLocationText() {
        StringBuilder location = new StringBuilder();
        if (product.isIsShipNationwide()) {
            location.append(getMyContext().getResources().getString(R.string.ships_from));
        }
        location.append(getAddress());
        if (product.isIsShipNationwide()) {
            location.append(" ");
            location.append(getMyContext().getResources().getString(R.string.for_price));
            location.append(" ");
            location.append(GeneralFunction.textMultiColor(product.getShipping_price().getFormatted(), "#55ACEE"));
        }
        return location.toString();
    }

    private String getAddress() {
        try {
            return GeoCoderAddress.getInstance()
                    .getAddress(new LatLng(product.getLat()
                            , product.getLon())).getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getPriceDetails() {
        StringBuilder priceDetails = new StringBuilder();
        priceDetails.append(product.getPrice().getFormatted());
        priceDetails.append(" + ");
        priceDetails.append(product.getShipping_price().getFormatted());
        priceDetails.append(" ");
        priceDetails.append(getMyContext().getResources().getString(R.string.shipping));
        return priceDetails.toString();
    }

    private String getShippingProtectionDescription() {
        StringBuilder description = new StringBuilder();
        description.append(getMyContext().getResources().getString(R.string.shipping_protection_text));
        description.append(" ");
        description.append(GeneralFunction.textMultiColor(getMyContext().getResources().getString(R.string.learn_more), "#55ACEE"));
        return description.toString();
    }

    public void onFavoriteClicked() {
        if (product.isIsFaverate()) {
            removeFavorite();
        } else {
            addFavorite();
        }
    }

    private void addFavorite() {
        getDataManager().getProductService().addFavorite(getMyContext(), true, product.getId(), new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                product.setFaverate(true);
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

    private void removeFavorite() {
        getDataManager().getProductService().removeFavorite(getMyContext(), true, product.getId(), new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                product.setFaverate(false);
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

    public void onShareClicked() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getMyContext().getResources().getString(R.string.app_name));
            String shareMessage = "\n" + getMyContext().getResources().getString(R.string.share_this_product_with) + "\n\n";
            shareMessage = shareMessage + "http://raheeb.selwantech.tech/product?id=" + product.getId() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            getBaseActivity().startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void onReportClicked() {

    }

    public void onAskClicked() {

    }

    public void onViewProductOwnerClicked() {
        Bundle data = new Bundle();
        data.putSerializable(AppConstants.BundleData.PRODUCT_OWNER, product.getProductOwner());
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_productDetailsFragment_to_userProfileFragment, data);
    }

    public void onMakeOfferClicked() {
        OfferFragmentDialog offerFragmentDialog = new OfferFragmentDialog.Builder().build();
        offerFragmentDialog.setMethodCallBack(new OfferFragmentDialog.OfferCallBack() {
            @Override
            public void callBack(double amount) {
                sendOffer(amount);
            }
        });
        offerFragmentDialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }

    private void sendOffer(double amount) {
        getDataManager().getProductService().makeOffer(getMyContext(), true, product.getId(), amount, new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                showToast(response);
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

    public void onBuyNowClicked() {
        if (product != null) {
            Bundle data = new Bundle();
            data.putSerializable(AppConstants.BundleData.PRODUCT, product);
            Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_productDetailsFragment_to_reviewOfferFragment, data);
        }
    }

    public void onBackClicked() {
        popUp();
    }

    @Override
    public void onClick(ImagesItem imagesItem, int position) {
        GeneralFunction.loadImage(getMyContext(), imagesItem.getImage(), getViewBinding().imgProduct);
    }


}
