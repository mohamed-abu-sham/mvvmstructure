package com.selwantech.raheeb.ui.menufragments.filterproductslocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentFilterProductsLocationBinding;
import com.selwantech.raheeb.enums.FilterProductResultsTypes;
import com.selwantech.raheeb.enums.SeekKMTypes;
import com.selwantech.raheeb.model.FilterLocation;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.seekbar.OnRangeChangedListener;
import com.selwantech.raheeb.seekbar.RangeSeekBar;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.utils.AppConstants;

public class FilterProductLocationViewModel extends BaseViewModel<FilterProductLocationNavigator, FragmentFilterProductsLocationBinding> {

    boolean isCanceled = true ;
    public <V extends ViewDataBinding, N extends BaseNavigator> FilterProductLocationViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (FilterProductLocationNavigator) navigation, (FragmentFilterProductsLocationBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
//        getViewBinding().seekbarKm.setTickMarkTextArray(getMyContext().getResources().getStringArray(R.array.KmArray));

        getViewBinding().toolbar.tvToolbarAction.setText(R.string.reset);
        getViewBinding().toolbar.tvToolbarAction.setVisibility(View.VISIBLE);
        getViewBinding().toolbar.tvToolbarAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewBinding().seekbarKm.setProgress(0);
                onSeekChanged(0);
            }
        });

        getViewBinding().seekbarKm.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                onSeekChanged(view.getLeftSeekBar().getProgress());
            }
        });
    }

    private void onSeekChanged(float progress) {
        SeekKMTypes whichView = SeekKMTypes.fromInt((int) progress);
        switch (whichView){
            case KM5:
                changeSelectedDisText(R.string.km5);
                break;
            case KM10:
                changeSelectedDisText(R.string.km10);
                break;
            case KM20:
                changeSelectedDisText(R.string.km20);
                break;
            case KM30:
                changeSelectedDisText(R.string.km30);
                break;
            case MAX:
                changeSelectedDisText(R.string.max);
                break;
        }
    }

    private void changeSelectedDisText(int text){
        getViewBinding().tvSelectedDis.setText(text);
    }

    public void onApplyClicked() {
        isCanceled = false ;
        popUp();
    }

    public void returnData() {
        if(!isCanceled) {
            Intent intent = new Intent();
            FilterLocation filterLocation = new FilterLocation("amman",0,0,
                    (int) getViewBinding().seekbarKm.getLeftSeekBar().getProgress());
            intent.putExtra(AppConstants.BundleData.FILTER_LOCATION, filterLocation);
            ((MainActivity) getBaseActivity()).onActivityResultFromFragment(
                    FilterProductResultsTypes.LOCATION.getValue(), Activity.RESULT_OK, intent);
        }
    }
}
