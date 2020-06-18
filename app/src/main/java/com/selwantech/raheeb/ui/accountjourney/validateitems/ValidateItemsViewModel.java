package com.selwantech.raheeb.ui.accountjourney.validateitems;

import android.content.Context;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentValidateListBinding;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.model.ValidateItem;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.adapter.ValidateItemsAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;

public class ValidateItemsViewModel extends BaseViewModel<ValidateItemsNavigator, FragmentValidateListBinding> {

    ValidateItemsAdapter validateItemsAdapter;

    public <V extends ViewDataBinding, N extends BaseNavigator> ValidateItemsViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (ValidateItemsNavigator) navigation, (FragmentValidateListBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpRecycler();
        getData();
    }

    private void setUpRecycler() {
        getViewBinding().recyclerView.setLayoutManager(new GridLayoutManager(getMyContext(), 3));
        validateItemsAdapter = new ValidateItemsAdapter(getMyContext());
        getViewBinding().recyclerView.setAdapter(validateItemsAdapter);

    }

    private void getData() {
        ArrayList<ValidateItem> validateItems = new ArrayList<>();
        if (User.getInstance().isIs_valid()) {
            validateItems.add(new ValidateItem(R.drawable.ic_verified, R.color.color_dark_blue, R.string.trusted));
        }
        if (User.getInstance().getPhone() != null && !User.getInstance().getPhone().isEmpty()) {
            validateItems.add(new ValidateItem(R.drawable.ic_phone, R.color.color_green, R.string.phone));
        }
        if (User.getInstance().getAvatar() != null && !User.getInstance().getAvatar().isEmpty()) {
            validateItems.add(new ValidateItem(R.drawable.ic_camera_white, R.color.color_image_validate, R.string.image));
        }
        if (User.getInstance().getEmail() != null && !User.getInstance().getEmail().isEmpty()) {
            validateItems.add(new ValidateItem(R.drawable.ic_email, R.color.color_gray, R.string.email));
        }
        if (User.getInstance().getLogin_with().equals("twitter")) {
            validateItems.add(new ValidateItem(R.drawable.ic_twitter, R.color.colorPrimaryDark, R.string.twitter));
        }

        validateItemsAdapter.addItems(validateItems);
    }

}
