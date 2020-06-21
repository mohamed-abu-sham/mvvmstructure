
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.selwantech.raheeb.App;
import com.selwantech.raheeb.databinding.CellSettingItemBinding;
import com.selwantech.raheeb.enums.SettingsTypes;
import com.selwantech.raheeb.interfaces.RecyclerClickNoData;
import com.selwantech.raheeb.model.User;
import com.selwantech.raheeb.utils.LanguageUtils;

import androidx.databinding.BaseObservable;


public class ItemSettingViewModel extends BaseObservable {

    private final Context context;
    RecyclerClickNoData recyclerClick;
    private String text;
    private int position;
    CellSettingItemBinding cellSettingItemBinding;

    public ItemSettingViewModel(Context context, CellSettingItemBinding cellSettingItemBinding, String text, int position, RecyclerClickNoData recyclerClick) {
        this.context = context;
        this.cellSettingItemBinding = cellSettingItemBinding;
        this.text = text;
        this.position = position;
        this.recyclerClick = recyclerClick;
        setArrowVisible();
    }

    private void setArrowVisible() {
        if (position == SettingsTypes.TWITTER.getMode() && User.getInstance().isLoggedInWithTwitter()) {
            cellSettingItemBinding.imgArrow.setVisibility(View.GONE);
        }
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyChange();
    }

    public void onItemClick(View view) {
        recyclerClick.onClick(position);
    }

    public int getGravity() {
        return LanguageUtils.getLanguage(App.getInstance()).equals("ar")
                ? Gravity.RIGHT : Gravity.LEFT;
    }
}
