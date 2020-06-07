package com.selwantech.raheeb.ui.menufragments.productdetails;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ViewDataBinding;
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
import com.selwantech.raheeb.helper.GeoCoderAddress;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.ImagesItem;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.ProductImagesAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SmoothMoveMarker;
import com.selwantech.raheeb.utils.SnackViewBulider;
import com.selwantech.raheeb.utils.SpacesItemDecoration;

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
            setProductAddress();
            initMap();
            product.getImages().get(0).setSelected(true);
            productImagesAdapter.addItems(product.getImages());
        } else {
            getProduct();
        }

    }

    private void setUpRecycler() {
        getViewBinding().recyclerViewProductImage.recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.HORIZONTAL, false));
        getViewBinding().recyclerViewProductImage.recyclerView.setItemAnimator(new DefaultItemAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        getViewBinding().recyclerViewProductImage.recyclerView.addItemDecoration(decoration);
        productImagesAdapter = new ProductImagesAdapter(getMyContext(), this);
        getViewBinding().recyclerViewProductImage.recyclerView.setAdapter(productImagesAdapter);

    }

    private void getProduct() {
        getDataManager().getProductService().getProduct(getMyContext(), true, getNavigator().getProductId(), new APICallBack<Product>() {
            @Override
            public void onSuccess(Product response) {
                product = response;
                getViewBinding().setData(product);
                setProductAddress();
                initMap();
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

    public void setProductAddress() {
        try {
            String location = GeoCoderAddress.getInstance()
                    .getAddress(new LatLng(product.getLat()
                            , product.getLon())).getAddress();
            getViewBinding().tvLocation.setText(location);
            getViewBinding().tvLocation2.setText(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void onMakeOfferClicked() {

    }

    public void onBuyNowClicked() {

    }

    public void onBackClicked() {
        popUp();
    }

    @Override
    public void onClick(ImagesItem imagesItem, int position) {

    }
}
