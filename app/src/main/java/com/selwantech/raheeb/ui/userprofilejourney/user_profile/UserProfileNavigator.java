package com.selwantech.raheeb.ui.userprofilejourney.user_profile;


import com.selwantech.raheeb.model.ProductOwner;
import com.selwantech.raheeb.ui.base.BaseNavigator;

public interface UserProfileNavigator extends BaseNavigator {


    ProductOwner getProductOwner();

    int getUserId();

    default String getString() {

        return "";

    }
}
