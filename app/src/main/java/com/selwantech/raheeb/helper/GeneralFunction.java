package com.selwantech.raheeb.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.selwantech.raheeb.App;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.utils.ProgressRequestBody;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.BindingAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.selwantech.raheeb.utils.ImageUtils.blurRenderScript;

public class GeneralFunction {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_loading);
        requestOptions.error(R.color.navigation_gray);
        Glide.with(imageView.getContext()).applyDefaultRequestOptions(requestOptions).load(url).into(imageView);
    }

    @BindingAdapter("cardviewBackgroundColor")
    public static void setCardBackgroundColor(CardView cardView, int color) {
        cardView.setCardBackgroundColor(App.getInstance().getResources().getColor(color));
    }

    @BindingAdapter("circleImageUrl")
    public static void setCircleImageUrl(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_loading);
        requestOptions.error(R.color.navigation_gray);
        Glide.with(imageView.getContext()).applyDefaultRequestOptions(requestOptions).load(url).circleCrop().into(imageView);
    }

    @BindingAdapter("imageResources")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("textResources")
    public static void setTextResource(TextView textView, int resource) {
        textView.setText(resource);
    }

    public static void loadImage(Context mContext, String imgUrl, ImageView imageView) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_loading);
        requestOptions.error(R.color.navigation_gray);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(imgUrl).into(imageView);

    }

    public static void loadImage(Context mContext, Uri imgUrl, ImageView imageView) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_loading);
        requestOptions.error(R.mipmap.ic_launcher);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(imgUrl).into(imageView);

    }

    public static String textMultiColor(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @BindingAdapter("textviewColor")
    public static void textColor(TextView textView, int color) {
        textView.setTextColor(color);
    }
    @BindingAdapter("textviewBackground")
    public static void textBackground(TextView textView, int res) {
        textView.setBackgroundResource(res);
    }

    public static void tintImageView(Context mContext, ImageView imageView, int color) {
        imageView.setColorFilter(ContextCompat.getColor(mContext,
                color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @BindingAdapter("tintButton")
    public static void tintButton(TextView button, int color) {
        Drawable buttonDrawable = button.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable, color);
        button.setBackground(buttonDrawable);
    }

    public static void enableDisableViewHolder(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableViewHolder(group.getChildAt(idx), enabled);
            }
        }
    }

    public static void loadImage(Context mContext, int img, ImageView imageView) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_loading);
        requestOptions.error(R.mipmap.ic_launcher);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(img).into(imageView);

    }
    public static void tintViewBackgroundTint(View view, int color) {
        Drawable buttonDrawable = view.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        DrawableCompat.setTint(buttonDrawable, color);
        view.setBackground(buttonDrawable);
    }

    public static void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    public static void loadImageWithBlure(Context mContext, String imgUrl, ImageView imageView) {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.ic_loading);
//        requestOptions.error(R.color.navigation_gray);
//        Glide.with(mContext).applyDefaultRequestOptions(requestOptions)
//                .asBitmap()
//                .load(imgUrl)
//                .into(new CustomTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
////                    imageView.setImageBitmap(blurRenderScript(mContext, resource, 1));
//
//                    Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
//                        @Override
//                        public void onGenerated(Palette palette) {
//                            // Here's your generated palette
//                            Palette.Swatch swatch = palette.getDarkVibrantSwatch();
//                            int color = palette.getDarkVibrantColor(swatch.getTitleTextColor());
//                        }
//                    });
//                }
//
//                @Override
//                public void onLoadCleared(@Nullable Drawable placeholder) {
//                }
//        });


//        Bitmap blurred = blurRenderScript(getApplicationContext(), bitmap, 10);

        if (!imgUrl.isEmpty()) {
            Picasso.with(mContext).load(imgUrl).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    // loaded bitmap is here (bitmap)
                    imageView.setImageBitmap(blurRenderScript(mContext, bitmap, 1));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    imageView.setImageResource(R.color.navigation_gray);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        } else {
            imageView.setImageResource(R.color.navigation_gray);
        }
    }

    public static MultipartBody.Part getImageMultipart(String path, String name) {
        File file = new File(path);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(name, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        return filePart;
    }

    public static MultipartBody.Part getImageMultiPartWithProgress(String path, String name, ProgressRequestBody.UploadCallbacks uploadCallbacks) {
        File file = new File(path);
        ProgressRequestBody fileBody = new ProgressRequestBody(file, "image", uploadCallbacks);
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData(name, file.getName(), fileBody);
        return filePart;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public static String generateRandomPassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
