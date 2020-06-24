package com.selwantech.raheeb.ui.productjourneys.createproductjourney.add_product_holder;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductHolderBinding;
import com.selwantech.raheeb.enums.DialogTypes;
import com.selwantech.raheeb.interfaces.BackPressed;
import com.selwantech.raheeb.model.CreatePostProgress;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.OnLineDialog;
import com.selwantech.raheeb.ui.productjourneys.createproductjourney.adddetails.AddDetailsFragment;
import com.selwantech.raheeb.ui.productjourneys.createproductjourney.addimage.AddImageFragment;
import com.selwantech.raheeb.ui.productjourneys.createproductjourney.addprice.AddPriceFragment;
import com.selwantech.raheeb.ui.productjourneys.createproductjourney.addshipping.AddShippingFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.utils.SnackViewBulider;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AddProductHolderViewModel extends BaseViewModel<AddProductHolderNavigator, FragmentAddProductHolderBinding>
        implements BackPressed {

    CreatePostProgress createPostProgress;
    AddImageFragment addImageFragment = new AddImageFragment();
    AddDetailsFragment addDetailsFragment = new AddDetailsFragment();
    AddPriceFragment addPriceFragment = new AddPriceFragment();
    AddShippingFragment addShippingFragment = new AddShippingFragment();
    int currentFragment = 0;

    Post post;
    FragmentManager fragmentManager;

    public <V extends ViewDataBinding, N extends BaseNavigator> AddProductHolderViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (AddProductHolderNavigator) navigation, (FragmentAddProductHolderBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        createPostProgress = new CreatePostProgress();
        getViewBinding().setProgress(createPostProgress);
        createPostProgress.setAddPhotos(true);
        post = new Post();
        fragmentManager = getBaseActivity().getSupportFragmentManager().getFragments().get(0).getChildFragmentManager();
        moveFragment(0);
    }

    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.BundleData.POST, post);
        switch (position) {
            case 0:
                addImageFragment.setBackPressed(this::onBackPressed);
                addImageFragment.setArguments(bundle);
                return addImageFragment;
            case 1:
                addDetailsFragment.setBackPressed(this::onBackPressed);
                addDetailsFragment.setArguments(bundle);
                return addDetailsFragment;
            case 2:
                addPriceFragment.setBackPressed(this::onBackPressed);
                addPriceFragment.setArguments(bundle);
                return addPriceFragment;
            default:
                addShippingFragment.setBackPressed(this::onBackPressed);
                addShippingFragment.setArguments(bundle);
                return addShippingFragment;
        }
    }

    public void onNextClick() {
        Post post;
        switch (currentFragment) {
            case 0: {
                post = addImageFragment.onNextClicked();
                if (post != null) {
                    this.post = post;
                    createPostProgress.setAddDetails(true);
                    currentFragment = 1;
                    moveFragment(1);
                }
                break;
            }
            case 1: {
                post = addDetailsFragment.onNextClicked();
                if (post != null) {
                    this.post = post;
                    createPostProgress.setAddPrice(true);
                    currentFragment = 2;
                    moveFragment(2);
                }
                break;
            }
            case 2: {
                post = addPriceFragment.onNextClicked();
                if (post != null) {
                    this.post = post;
                    createPostProgress.setFinish(true);
                    currentFragment = 3;
                    moveFragment(3);
                }
                break;
            }
            case 3: {
                post = addShippingFragment.onNextClicked();
                if (post != null) {
                    this.post = post;
                    createProduct(post);
                }
                break;
            }
        }
    }

    private void createProduct(Post post) {
        getDataManager().getProductService().createProduct(getMyContext(), true, post, new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                new OnLineDialog(getMyContext()) {
                    @Override
                    public void onPositiveButtonClicked() {
                        dismiss();
                        back();
                    }

                    @Override
                    public void onNegativeButtonClicked() {

                    }
                }.showConfirmationDialog(DialogTypes.OK, getMyContext().getResources().getString(R.string.success)
                        , getMyContext().getResources().getString(R.string.product_created_successful));
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getResources().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }

    private void moveFragment(int position) {
        fragmentManager.beginTransaction().replace(R.id.create_product_fragment, getItem(position)).commit();
    }


    @Override
    public void onBackPressed(int position) {
        if (position == 0) {
            back();
        } else {
            moveFragment(position - 1);
            currentFragment = position - 1;
            reverseProgress(position);
        }
    }

    private void back() {
        if (addImageFragment.isAdded())
            addImageFragment.disableBackPress();
        if (addDetailsFragment.isAdded())
            addDetailsFragment.disableBackPress();
        if (addPriceFragment.isAdded())
            addPriceFragment.disableBackPress();
        if (addShippingFragment.isAdded())
            addShippingFragment.disableBackPress();
        popUp();
    }

    private void reverseProgress(int position) {
        switch (position) {
            case 1:
                createPostProgress.setAddDetails(false);
                break;
            case 2:
                createPostProgress.setAddPrice(false);
                break;
            case 3:
                createPostProgress.setFinish(false);
                break;
        }
    }

    public void onResume() {
        switch (currentFragment) {
            case 3:
                addShippingFragment.onResume();
                break;
        }
    }
}
