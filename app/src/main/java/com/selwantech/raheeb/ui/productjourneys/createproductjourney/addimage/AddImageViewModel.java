package com.selwantech.raheeb.ui.productjourneys.createproductjourney.addimage;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductImagesBinding;
import com.selwantech.raheeb.enums.PickImageTypes;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.adapter.AddImagesAdapter;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.PickImageFragmentDialog;
import com.selwantech.raheeb.utils.ItemTouchCallBack;
import com.selwantech.raheeb.utils.PickImageUtility;
import com.selwantech.raheeb.utils.SnackViewBulider;

import java.util.ArrayList;

public class AddImageViewModel extends BaseViewModel<AddImageNavigator, FragmentAddProductImagesBinding>
        implements RecyclerClick<String> {

    AddImagesAdapter addImagesAdapter;

    public <V extends ViewDataBinding, N extends BaseNavigator> AddImageViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (AddImageNavigator) navigation, (FragmentAddProductImagesBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpRecycler();
    }

    private void setUpRecycler() {
        getViewBinding().recyclerViewAddImage.recyclerView.setLayoutManager(new LinearLayoutManager(getMyContext(), LinearLayoutManager.HORIZONTAL, false));
        getViewBinding().recyclerViewAddImage.recyclerView.setItemAnimator(new DefaultItemAnimator());
        addImagesAdapter = new AddImagesAdapter(getMyContext(), this);
        getViewBinding().recyclerViewAddImage.recyclerView.setAdapter(addImagesAdapter);
        addImagesAdapter.addItem(null);
        ItemTouchHelper ith = new ItemTouchHelper(new ItemTouchCallBack(addImagesAdapter, addImagesAdapter.getArrayList()));
        ith.attachToRecyclerView(getViewBinding().recyclerViewAddImage.recyclerView);

    }

    public void setImage(String image) {
        if (addImagesAdapter.getItemCount() == 1) {
            getViewBinding().imgNoImage.setVisibility(View.GONE);
        }
        GeneralFunction.loadImage(getMyContext(), image, getViewBinding().imgProduct);
        addImagesAdapter.remove(addImagesAdapter.getItemCount() - 1);
        addImagesAdapter.addItem(image);
        addImagesAdapter.addItem(null);
    }

    public void addImageClicked() {
        PickImageFragmentDialog pickImageFragmentDialog = new PickImageFragmentDialog.Builder().build();
        pickImageFragmentDialog.setMethodCallBack(new PickImageFragmentDialog.methodClick() {
            @Override
            public void onMethodBack(int type) {
                if (type == PickImageTypes.GALLERY.getIntValue()) {
                    PickImageUtility.selectImage(getBaseActivity());
                } else {
                    PickImageUtility.TakePictureIntent(getBaseActivity());
                }
            }
        });
        pickImageFragmentDialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }


    public Post returnData() {
        if (isValid()) {
            addImagesAdapter.remove(addImagesAdapter.getItemCount() - 1);
            getNavigator().getPost().setImages(new ArrayList<>(addImagesAdapter.getArrayList()));
            getNavigator().getPost().setTitle(getViewBinding().edTitle.getText().toString());
            return getNavigator().getPost();
        }
        return null;
    }

    private boolean isValid() {
        int error = 0;
        if (addImagesAdapter.getItemCount() == 1) {
            showSnackBar(getMyContext().getResources().getString(R.string.warning),
                    getMyContext().getResources().getString(R.string.please_add_product_images)
                    , getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                        @Override
                        public void onActionClick(Snackbar snackbar) {
                            snackbar.dismiss();
                        }
                    });
            error++;
        }

        if (getViewBinding().edTitle.getText().toString().trim().isEmpty()) {
            getViewBinding().edTitle.setError(getMyContext().getResources().getString(R.string.this_fieled_is_required));
            error++;
        }
        return error == 0;
    }

    @Override
    public void onClick(String image, int position) {
        if (image == null) {
            addImageClicked();
        } else {
            GeneralFunction.loadImage(getMyContext(), image, getViewBinding().imgProduct);
        }
    }
}
