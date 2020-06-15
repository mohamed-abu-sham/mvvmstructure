package com.selwantech.raheeb.ui.offersjourney.productsellingdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentProductDetailsBinding;
import com.selwantech.raheeb.databinding.FragmentProductSellingDetailsBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.helper.GeoCoderAddress;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.ImagesItem;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.model.Selling;
import com.selwantech.raheeb.model.chatdata.Chat;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.ProductImagesAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OfferFragmentDialog;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.io.IOException;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ProductSellingDetailsViewModel extends
        BaseViewModel<ProductSellingDetailsNavigator, FragmentProductSellingDetailsBinding>
        implements RecyclerClick<ImagesItem> {


    Selling product;
    ProductImagesAdapter productImagesAdapter;

    public <V extends ViewDataBinding, N extends BaseNavigator> ProductSellingDetailsViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ProductSellingDetailsNavigator) navigation, (FragmentProductSellingDetailsBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpRecycler();
        product = getNavigator().getProduct();
        initMap();
        product.getImages().get(0).setSelected(true);
        productImagesAdapter.addItems(product.getImages());
    }

    private void setUpRecycler() {
        getViewBinding().recyclerViewProductImage.recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.HORIZONTAL, false));
        getViewBinding().recyclerViewProductImage.recyclerView.setItemAnimator(new DefaultItemAnimator());
        productImagesAdapter = new ProductImagesAdapter(getMyContext(), this);
        getViewBinding().recyclerViewProductImage.recyclerView.setAdapter(productImagesAdapter);
    }

    private void initMap() {
        GeneralFunction.loadImage(getMyContext(),"https://maps.googleapis.com/maps/api/staticmap?zoom=13&size=600x300&maptype=roadmap\n" +
                "&markers=color:red%7C"+product.getLat()+","+product.getLon()+"\n" +
                "&key="+getMyContext().getResources().getString(R.string.map_key),getViewBinding().imgMap);

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

    public int canShare(){
        return product.getStatus().equals(AppConstants.PRODUCT_STATUS.AVAILABLE) ? View.VISIBLE : View.GONE;
    }

    public void onBackClicked() {
        popUp();
    }

    @Override
    public void onClick(ImagesItem imagesItem, int position) {
        GeneralFunction.loadImage(getMyContext(), imagesItem.getImage(), getViewBinding().imgProduct);
    }


}
