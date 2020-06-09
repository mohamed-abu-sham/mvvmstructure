package com.selwantech.raheeb.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ButtomSheetCategoriesBinding;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Category;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.adapter.CategoryCirclesAdapter;

import java.util.ArrayList;

public class CategoriesFragmentDialog extends BottomSheetDialogFragment implements RecyclerClick<Category> {

    CategoriesCallBack categoriesCallBack;
    ButtomSheetCategoriesBinding buttomSheetCategoriesBinding;

    CategoryCirclesAdapter categoryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buttomSheetCategoriesBinding = DataBindingUtil.inflate(inflater, R.layout.buttom_sheet_categories, null, false);
        buttomSheetCategoriesBinding.setViewModel(this);
        setCancelable(false);
        setUp();
        return buttomSheetCategoriesBinding.getRoot();
    }

    private void setUp() {
        setUpRecycler();
    }

    private void setUpRecycler() {
        buttomSheetCategoriesBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        buttomSheetCategoriesBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryAdapter = new CategoryCirclesAdapter(getContext(), this);
        buttomSheetCategoriesBinding.recyclerView.setAdapter(categoryAdapter);
        getData();
    }

    private void getData() {
        DataManager.getInstance().getCategoryService().getAllCategories(getContext(), false, new APICallBack<ArrayList<Category>>() {
            @Override
            public void onSuccess(ArrayList<Category> response) {
                categoryAdapter.addItems(response);
                notifyAdapter();
                buttomSheetCategoriesBinding.progressBar.setVisibility(View.GONE);
                buttomSheetCategoriesBinding.recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String error, int errorCode) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                buttomSheetCategoriesBinding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public BottomSheetDialogFragment setMethodCallBack(CategoriesCallBack filterByCallBack) {
        this.categoriesCallBack = filterByCallBack;
        return this;
    }

    private void notifyAdapter() {
        buttomSheetCategoriesBinding.recyclerView.post(new Runnable() {
            @Override
            public void run() {
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(Category category, int position) {
        dismiss();
        categoriesCallBack.callBack(category);
    }

    public void onCancleClicked() {
        dismiss();
    }


    public interface CategoriesCallBack {
        void callBack(Category category);
    }

    public static class Builder {

        public CategoriesFragmentDialog build() {
            Bundle args = new Bundle();
            CategoriesFragmentDialog fragment = new CategoriesFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
