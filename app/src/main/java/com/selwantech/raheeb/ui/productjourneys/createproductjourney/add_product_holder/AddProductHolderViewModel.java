package com.selwantech.raheeb.ui.productjourneys.createproductjourney.add_product_holder;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductHolderBinding;
import com.selwantech.raheeb.model.CreatePostProgress;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.productjourneys.createproductjourney.adddetails.AddDetailsFragment;
import com.selwantech.raheeb.ui.productjourneys.createproductjourney.addimage.AddImageFragment;
import com.selwantech.raheeb.ui.productjourneys.createproductjourney.addprice.AddPriceFragment;
import com.selwantech.raheeb.utils.AppConstants;

public class AddProductHolderViewModel extends BaseViewModel<AddProductHolderNavigator, FragmentAddProductHolderBinding> {

    CreatePostProgress createPostProgress;

    AddImageFragment addImageFragment;
    AddDetailsFragment addDetailsFragment;
    AddPriceFragment addPriceFragment;
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
        addImageFragment = new AddImageFragment();
        addDetailsFragment = new AddDetailsFragment();
        addPriceFragment = new AddPriceFragment();
        fragmentManager = getBaseActivity().getSupportFragmentManager().getFragments().get(0).getChildFragmentManager();
        moveFragment(0);
    }

    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.BundleData.POST, post);
        switch (position) {
            case 0:
                addImageFragment.setArguments(bundle);
                return addImageFragment;
            case 1:
                addDetailsFragment.setArguments(bundle);
                return addDetailsFragment;
            case 2:
                addPriceFragment.setArguments(bundle);
                return addPriceFragment;
            default:

                return addImageFragment;
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
//                    moveFragment(3);
                }
                break;
            }
            case 3: {

                break;
            }
        }
    }

    private void moveFragment(int position) {
        fragmentManager.beginTransaction().replace(R.id.create_product_fragment, getItem(position)).commit();
    }
}
