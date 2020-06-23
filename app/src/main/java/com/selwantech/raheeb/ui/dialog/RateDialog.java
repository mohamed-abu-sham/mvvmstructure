package com.selwantech.raheeb.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RatingBar;
import android.widget.Toast;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.DialogRateBinding;
import com.selwantech.raheeb.model.Rate;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class RateDialog extends Dialog {

    int rating = 0;
    DialogRateBinding dialogRateBinding;
    Dialog dialog;
    int userId;

    public RateDialog(@NonNull Context context, int userId) {
        super(context);
        this.userId = userId;
    }

    public void showRateDialog() {
        dialog = new Dialog(getContext());
        dialogRateBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_rate, null, false);
        dialog.setContentView(dialogRateBinding.getRoot());
        dialogRateBinding.setViewModel(this);
        dialogRateBinding.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating1, boolean fromUser) {
                rating = (int) rating1;
            }
        });

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 10);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
    }

    public void onRateClicked() {
        DataManager.getInstance().getUserService().rateUser(getContext(), true, userId, getRateObj(), new APICallBack<String>() {
            @Override
            public void onSuccess(String response) {
                onCanceledClick();
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.rated_successful), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error, int errorCode) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onCanceledClick() {
        dialog.dismiss();
    }

    private Rate getRateObj() {
        return new Rate(rating, dialogRateBinding.edComment.getText().toString());
    }


}