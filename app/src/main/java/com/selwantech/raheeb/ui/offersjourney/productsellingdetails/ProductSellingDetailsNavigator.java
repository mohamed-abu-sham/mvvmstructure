package com.selwantech.raheeb.ui.offersjourney.productsellingdetails;


import com.google.android.gms.maps.SupportMapFragment;
import com.selwantech.raheeb.model.Product;
import com.selwantech.raheeb.model.Selling;
import com.selwantech.raheeb.ui.base.BaseNavigator;

public interface ProductSellingDetailsNavigator extends BaseNavigator {

    Selling getProduct();
}
