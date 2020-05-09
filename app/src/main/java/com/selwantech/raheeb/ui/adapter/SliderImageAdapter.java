package com.selwantech.raheeb.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.selwantech.raheeb.databinding.CellSliderImageBinding;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.model.Slider;

import java.util.ArrayList;

public class SliderImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Slider> sliderArrayList;


    public SliderImageAdapter(Context context, ArrayList<Slider> sliderArrayList) {
        mContext = context;
        this.sliderArrayList = sliderArrayList;
    }

    @Override
    public int getCount() {
        return sliderArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        CellSliderImageBinding cellSliderImageBinding = CellSliderImageBinding
                .inflate(inflater, container, false);
        Slider slider = sliderArrayList.get(position);
        GeneralFunction.loadImage(mContext, slider.getImage(), cellSliderImageBinding.imgPhoto);
        container.addView(cellSliderImageBinding.getRoot(), 0);
        return cellSliderImageBinding.getRoot();

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
