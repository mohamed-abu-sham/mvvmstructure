package com.selwantech.raheeb.ui.mappicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentMapPickerBinding;
import com.selwantech.raheeb.helper.BackPressedHandler;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.interfaces.BackPressed;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class MapPickerFragment extends BaseFragment<FragmentMapPickerBinding, MapPickerViewModel>
        implements ActivityResultCallBack, BackPressed {

    private static final String TAG = MapPickerFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private MapPickerViewModel mMapPickerViewModel;
    private FragmentMapPickerBinding mViewBinding;

    BackPressedHandler backPressedHandler;

    public static MapPickerFragment newInstance() {
        Bundle args = new Bundle();
        MapPickerFragment fragment = new MapPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapPickerViewModel.returnData();
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
        return R.layout.fragment_map_picker;
    }

    @Override
    public MapPickerViewModel getViewModel() {
        mMapPickerViewModel = (MapPickerViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(MapPickerViewModel.class, getViewDataBinding(), new Intent().putExtras(getArguments()));
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
        mMapPickerViewModel.setSupportMapFragment((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.search_place_map));
        mMapPickerViewModel.setUp();
        setupOnBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupOnBackPressed();
    }

    public void setupOnBackPressed() {
        backPressedHandler = new BackPressedHandler(true, 1, this::onBackPressed);
        getBaseActivity().getOnBackPressedDispatcher().addCallback(this, backPressedHandler);
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
    public void onBackPressed(int position) {
        mMapPickerViewModel.popUp();
    }
}
