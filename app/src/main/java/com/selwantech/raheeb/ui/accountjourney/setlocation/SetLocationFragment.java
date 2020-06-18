package com.selwantech.raheeb.ui.accountjourney.setlocation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentSetLocationBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class SetLocationFragment extends BaseFragment<FragmentSetLocationBinding, SetLocationViewModel>
        implements SetLocationNavigator, ActivityResultCallBack {

    private static final String TAG = SetLocationFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private SetLocationViewModel mMapPickerViewModel;
    private FragmentSetLocationBinding mViewBinding;

    public static SetLocationFragment newInstance() {
        Bundle args = new Bundle();
        SetLocationFragment fragment = new SetLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }


    @Override
    public boolean hideBottomSheet() {
        return true;
    }

    @Override
    public boolean isNeedActivityResult() {
        return true;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return this::callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set_location;
    }

    @Override
    public SetLocationViewModel getViewModel() {
        mMapPickerViewModel = (SetLocationViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(SetLocationViewModel.class, getViewDataBinding(), this);
        return mMapPickerViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        setUpToolbar(mViewBinding.toolbar, TAG, R.string.select_location);
        mMapPickerViewModel.setUp();
    }

    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        if (requestCode == mMapPickerViewModel.PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {

            mMapPickerViewModel.onActivityResult(data);
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
        } else if (resultCode == RESULT_CANCELED) {
        }
    }

    @Override
    public SupportMapFragment getChildManager() {
        return (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.search_place_map);
    }

}
