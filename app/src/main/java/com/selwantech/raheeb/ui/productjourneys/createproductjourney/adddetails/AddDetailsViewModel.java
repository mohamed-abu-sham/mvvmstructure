package com.selwantech.raheeb.ui.productjourneys.createproductjourney.adddetails;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentAddProductDetailsBinding;
import com.selwantech.raheeb.model.Category;
import com.selwantech.raheeb.model.Condition;
import com.selwantech.raheeb.model.Post;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.dialog.CategoriesFragmentDialog;
import com.selwantech.raheeb.utils.SnackViewBulider;
import com.selwantech.raheeb.utils.conditionSeekbar.ConditionSeekBar;
import com.selwantech.raheeb.utils.conditionSeekbar.OnRangeChangedListener;

import java.util.ArrayList;

public class AddDetailsViewModel extends
        BaseViewModel<AddDetailsNavigator, FragmentAddProductDetailsBinding> {

    boolean isCanceled = true;
    Condition condition;
    Category category;

    public <V extends ViewDataBinding, N extends BaseNavigator> AddDetailsViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (AddDetailsNavigator) navigation, (FragmentAddProductDetailsBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        getViewBinding().seekbarKm.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(ConditionSeekBar view, float leftValue, float rightValue, boolean isFromUser) {

            }

            @Override
            public void onStartTrackingTouch(ConditionSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(ConditionSeekBar view, boolean isLeft, Condition condition) {
                onSeekChanged(condition);
            }
        });

        getData();
    }

    private void getData() {
        getDataManager().getAppService().getConditions(getMyContext(), true, new APICallBack<ArrayList<Condition>>() {
            @Override
            public void onSuccess(ArrayList<Condition> response) {
                getViewBinding().seekbarKm.setSteps(response.size() - 1);
                getViewBinding().seekbarKm.setTickMarkTextArray(response);
                getViewBinding().seekbarKm.setProgress(0);
                onSeekChanged(getViewBinding().seekbarKm.getTickMarkTextArray().get(0));
            }

            @Override
            public void onError(String error, int errorCode) {
                showSnackBar(getMyContext().getString(R.string.error),
                        error, getMyContext().getResources().getString(R.string.ok),
                        new SnackViewBulider.SnackbarCallback() {
                            @Override
                            public void onActionClick(Snackbar snackbar) {
                                snackbar.dismiss();
                            }
                        });
            }
        });
    }

    public void onCategoryClicked() {
        CategoriesFragmentDialog categoriesFragmentDialog = new CategoriesFragmentDialog.Builder().build();
        categoriesFragmentDialog.setMethodCallBack(new CategoriesFragmentDialog.CategoriesCallBack() {
            @Override
            public void callBack(Category category1) {
                category = category1;
            }
        });
        categoriesFragmentDialog.show(getBaseActivity().getSupportFragmentManager(), "picker");
    }

    private void onSeekChanged(Condition c) {
        changeSelectedDisText(c.toString());
        this.condition = c;
    }

    private void changeSelectedDisText(String text) {
        getViewBinding().tvSelectedCondition.setText(text);
    }

    public Post returnData() {
        if (isValid()) {
            getNavigator().getPost().setConditionId(condition.getId());
            getNavigator().getPost().setDescription(getViewBinding().edDescription.getText().toString());
            getNavigator().getPost().setCategoryId(category.getId());
            return getNavigator().getPost();
        }
        return null;
    }

    private boolean isValid() {
        int error = 0;
        if (category == null) {
            showSnackBar(getMyContext().getResources().getString(R.string.warning),
                    getMyContext().getResources().getString(R.string.please_choose_the_category),
                    getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
                        @Override
                        public void onActionClick(Snackbar snackbar) {
                            snackbar.dismiss();
                        }
                    });
            error++;
        }
        if (getViewBinding().edDescription.getText().toString().trim().isEmpty()) {
            getViewBinding().edDescription.setError(getMyContext().getResources().getString(R.string.this_fieled_is_required));
            error++;
        }
        if (getViewBinding().edDescription.getText().toString().trim().length() < 10) {
            getViewBinding().edDescription.setError(getMyContext().getResources().getString(R.string.description_must_be_at_least_10_letters));
            error++;
        }

        return error == 0;
    }

}
