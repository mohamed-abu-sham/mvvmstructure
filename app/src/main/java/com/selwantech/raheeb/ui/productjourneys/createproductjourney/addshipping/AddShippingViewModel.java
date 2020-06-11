package com.selwantech.raheeb.ui.productjourneys.createproductjourney.addshipping;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductShippingBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.helper.GeoCoderAddress;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.BoxSize;
import com.selwantech.raheeb.model.FilterLocation;
import com.selwantech.raheeb.model.GeoAddress;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.ShippingBoxAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.io.IOException;
import java.util.ArrayList;

public class AddShippingViewModel extends BaseViewModel<AddShippingNavigator, FragmentAddProductShippingBinding>
        implements RecyclerClick<BoxSize> {


    ShippingBoxAdapter shippingBoxAdapter;
    GeoAddress geoAddress;
    FilterLocation filterLocation;

    public <V extends ViewDataBinding, N extends BaseNavigator> AddShippingViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (AddShippingNavigator) navigation, (FragmentAddProductShippingBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        setTermsOfServiceText();
        setUpRecycler();
        getShippingBoxes();
        getViewBinding().switchShipping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    getViewBinding().linearShippingInfo.setVisibility(View.VISIBLE);
                else
                    getViewBinding().linearShippingInfo.setVisibility(View.GONE);
            }
        });
    }

    private void setUpRecycler() {
        getViewBinding().recyclerViewShippingBox.recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.HORIZONTAL, false));
        getViewBinding().recyclerViewShippingBox.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
//        getViewBinding().recyclerViewShippingBox.recyclerView.addItemDecoration(decoration);
        shippingBoxAdapter = new ShippingBoxAdapter(getMyContext(), this);
        getViewBinding().recyclerViewShippingBox.recyclerView.setAdapter(shippingBoxAdapter);

    }

    private void setTermsOfServiceText() {
        StringBuilder terms = new StringBuilder();
        terms.append(getMyContext().getResources().getString(R.string.by_posting_nationwide));
        terms.append(" ");
        terms.append(GeneralFunction.textMultiColor(getMyContext().getResources().getString(R.string.terms_of_service), "#55ACEE"));
        getViewBinding().tvTermsOfService.setText(Html.fromHtml(terms.toString()));
    }

    private void getShippingBoxes() {
        getDataManager().getCategoryService().getCategoryBoxSize(getMyContext(), true, 1, new APICallBack<ArrayList<BoxSize>>() {
            @Override
            public void onSuccess(ArrayList<BoxSize> response) {
                response.get(0).setSelected(true);
                shippingBoxAdapter.addItems(response);
                notifyAdapter();
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

    private void notifyAdapter() {
        getViewBinding().recyclerViewShippingBox.recyclerView.post(new Runnable() {
            @Override
            public void run() {
                shippingBoxAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onEditLocationClicked() {
        Navigation.findNavController(getBaseActivity(), R.id.nav_host_fragment)
                .navigate(R.id.mapPickerFragment);
    }

    public Post returnData() {
        if (isValid()) {
            getNavigator().getPost().setLat(filterLocation.getLat());
            getNavigator().getPost().setLon(filterLocation.getLon());
            getNavigator().getPost().setIs_ship_nationwide(getViewBinding().switchShipping.isChecked() ? 1 : 0);
            if (getViewBinding().switchShipping.isChecked()) {
                getNavigator().getPost().setCategory_box_size_id(shippingBoxAdapter.getSelectedItem().getId());
            }
            return getNavigator().getPost();
        }
        return null;
    }

    private boolean isValid() {
        int error = 0;
        if (filterLocation == null) {
            showSnackBar(getMyContext().getResources().getString(R.string.warning),
                    getMyContext().getResources().getString(R.string.you_must_mark_the_address),
                    getMyContext().getResources().getString(R.string.mark), new SnackViewBulider.SnackbarCallback() {
                        @Override
                        public void onActionClick(Snackbar snackbar) {
                            onEditLocationClicked();
                            snackbar.dismiss();
                        }
                    });
            error++;
        }
        return error == 0;
    }

    @Override
    public void onClick(BoxSize boxSize, int position) {

    }

    public void setLocation(LatLng latLng) {
        try {
            if (latLng != null) {
                geoAddress = GeoCoderAddress.getInstance().getAddress(latLng);
                filterLocation = new FilterLocation(geoAddress.getAddress(), latLng.latitude, latLng.longitude, 0);
                getViewBinding().tvLocation.setText(geoAddress.getAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
