package com.selwantech.raheeb.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.SnackbarLayoutBinding;

public class SnackViewBulider {

    View view;
    int duration = 5000;
    int icon;
    String title;
    String body = "";
    String actionText;
    Activity activity;


    public SnackViewBulider(Activity activity, View view, int icon,
                            String title, String body,
                            String actionText) {

        this.activity = activity;
        this.view = view;
        this.icon = icon;
        this.title = title;
        this.body = body;
        this.actionText = actionText;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void showSnackbar(SnackbarCallback snackbarCallback) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar snackbar = Snackbar.make(view, "", duration);
                FrameLayout layout = (FrameLayout) snackbar.getView();
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                SnackbarLayoutBinding snackViewBinding = DataBindingUtil.inflate(inflater,
                        R.layout.snackbar_layout, null, false);
                snackViewBinding.imgSnackbarIcon.setImageResource(icon);
                snackViewBinding.tvSnackbarTitle.setText(title);
                snackViewBinding.tvSnackbarAction.setText(actionText);
                snackViewBinding.tvSnackbarBody.setText(body);
                if (body.isEmpty()) {
                    snackViewBinding.tvSnackbarBody.setVisibility(View.GONE);
                }
                snackViewBinding.tvSnackbarAction.setOnClickListener(v -> snackbarCallback.onActionClick(snackbar));

                snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
                final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();

                params.setMargins(params.leftMargin,
                        params.topMargin,
                        params.rightMargin,
                        params.bottomMargin + (int) ScreenUtils.pxFromDp(activity, 15));

                layout.setLayoutParams(params);
                ((ViewGroup) snackbar.getView()).removeAllViews();
                ((ViewGroup) snackbar.getView()).addView(snackViewBinding.getRoot());
                snackbar.show();
            }
        });

    }

    public interface SnackbarCallback {
        void onActionClick(Snackbar snackbar);
    }
}
