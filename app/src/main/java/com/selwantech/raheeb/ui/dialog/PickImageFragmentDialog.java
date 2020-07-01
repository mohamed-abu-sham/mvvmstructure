package com.selwantech.raheeb.ui.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.ButtomSheetPickImageBinding;
import com.selwantech.raheeb.enums.PickImageTypes;
import com.selwantech.raheeb.interfaces.ImageCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PickImageFragmentDialog extends BottomSheetDialogFragment {

    private static final int PERMISSION_REQUEST_CODE = 1;
    ImageCallback imageCallback;
    methodClick methodClick;
    ButtomSheetPickImageBinding buttomSheetPickImageBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buttomSheetPickImageBinding = DataBindingUtil.inflate(inflater, R.layout.buttom_sheet_pick_image, null, false);
        bindViews();
        return buttomSheetPickImageBinding.getRoot();
    }

    private void bindViews() {
        buttomSheetPickImageBinding.linearCamera.setOnClickListener(v -> {
            methodClick.onMethodBack(PickImageTypes.CAMERA.getIntValue());
            dismiss();
        });
        buttomSheetPickImageBinding.linearGallery.setOnClickListener(v -> {
            methodClick.onMethodBack(PickImageTypes.GALLERY.getIntValue());
            dismiss();
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

    public BottomSheetDialogFragment setCallBack(ImageCallback dialogCallback) {
        this.imageCallback = dialogCallback;
        return this;
    }

    public BottomSheetDialogFragment setMethodCallBack(methodClick methodClick) {
        this.methodClick = methodClick;
        return this;
    }


    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(),
                        WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                || (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            requestPermission();
        }
    }

    public void requestPermission() {
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA}
                , PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (getContext() == null) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            || (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            || (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        dismiss();
                    }
                } else {
                    dismiss();
                }
                break;
        }
    }


    public interface methodClick {
        void onMethodBack(int type);
    }

    public static class Builder {
        public PickImageFragmentDialog build() {
            Bundle args = new Bundle();
            PickImageFragmentDialog fragment = new PickImageFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
