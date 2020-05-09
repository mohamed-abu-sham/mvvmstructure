package com.selwantech.raheeb.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.ui.filebox.FileBoxActivity;

public class ViewImageFragmentDialog extends BottomSheetDialogFragment {

    ImageView imgPicture;
    Button btnDownload;
    boolean isDownloadable;
    private String image;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buttom_sheet_view_image, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    private void bindViews(View rootView) {
        imgPicture = rootView.findViewById(R.id.imgPicture);
        btnDownload = rootView.findViewById(R.id.btnDownload);
        image = getArguments().getString("url");
        isDownloadable = getArguments().getBoolean("isDownloadable", false);
        GeneralFunction.loadImage(getContext(), image, imgPicture);
        if (!isDownloadable) {
            btnDownload.setVisibility(View.GONE);
        }
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(FileBoxActivity.getStartIntent(getContext(), image));
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
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


    public static class Builder {

        String url = "";
        boolean isDownloadable;

        public Builder(String url) {
            this.url = url;
        }

        public ViewImageFragmentDialog build() {
            Bundle args = new Bundle();
            args.putString("url", url);
            args.putBoolean("isDownloadable", isDownloadable);
            ViewImageFragmentDialog fragment = new ViewImageFragmentDialog();
            fragment.setArguments(args);
            return fragment;
        }

        public Builder setDownloadable(boolean isDownloadable) {
            this.isDownloadable = isDownloadable;
            return this;
        }
    }


}
