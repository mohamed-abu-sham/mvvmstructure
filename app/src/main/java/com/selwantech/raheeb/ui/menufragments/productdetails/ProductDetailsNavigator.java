package com.selwantech.raheeb.ui.menufragments.productdetails;


import com.google.android.gms.maps.SupportMapFragment;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.ui.base.BaseNavigator;

public interface ProductDetailsNavigator extends BaseNavigator {

    Product getProduct();

    SupportMapFragment getChildManager();

    int getProductId();
}
