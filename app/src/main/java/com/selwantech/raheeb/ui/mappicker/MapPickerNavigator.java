package com.selwantech.raheeb.ui.mappicker;


import com.google.android.gms.maps.SupportMapFragment;
import com.selwantech.raheeb.ui.base.BaseNavigator;

public interface MapPickerNavigator extends BaseNavigator {

    SupportMapFragment getChildManager();

}
