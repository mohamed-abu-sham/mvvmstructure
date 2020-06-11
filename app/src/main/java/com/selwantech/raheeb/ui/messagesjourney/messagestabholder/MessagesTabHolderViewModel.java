package com.selwantech.raheeb.ui.messagesjourney.messagestabholder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentMessagesTabHolderBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;
import com.selwantech.raheeb.ui.messagesjourney.chats.ChatsFragment;
import com.selwantech.raheeb.ui.messagesjourney.notifications.NotificationsFragment;

public class MessagesTabHolderViewModel extends BaseViewModel<MessagesTabHolderNavigator, FragmentMessagesTabHolderBinding> {

    public <V extends ViewDataBinding, N extends BaseNavigator> MessagesTabHolderViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (MessagesTabHolderNavigator) navigation, (FragmentMessagesTabHolderBinding) viewDataBinding);
    }

    @Override
    protected void setUp() {
        setUpTablayout();
    }

    public void setUpTablayout() {

        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 0, true);
        getViewBinding().tabLayout.addTab(getViewBinding().tabLayout.newTab(), 1, false);
        getViewBinding().tabLayout.setSelectedTabIndicatorHeight(0);
        getViewBinding().tabLayout.setTabTextColors(Color.parseColor("#A8A8A8"), Color.parseColor("#55ACEE"));
        getViewBinding().tabLayout.getTabAt(0).setText(R.string.messages);
        getViewBinding().tabLayout.getTabAt(1).setText(R.string.notifications);

        FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager().getFragments().get(0).getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_messages_tab, getItem(0)).commit();
        getViewBinding().tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentManager.beginTransaction().replace(R.id.fragment_messages_tab, getItem(tab.getPosition())).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                chatsFragment.setArguments(bundle);
                return chatsFragment;
            default:
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                notificationsFragment.setArguments(bundle);
                return notificationsFragment;
        }
    }
}
