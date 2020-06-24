package com.selwantech.raheeb.ui.messagesjourney.chats;

import android.content.Context;
import android.content.Intent;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.databinding.FragmentChatsBinding;
import com.selwantech.raheeb.interfaces.ActivityResultCallBack;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseFragment;
import com.selwantech.raheeb.utils.AppConstants;
import com.selwantech.raheeb.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;


public class ChatsFragment extends BaseFragment<FragmentChatsBinding, ChatsViewModel>
        implements ChatsNavigator, ActivityResultCallBack {

    private static final String TAG = ChatsFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private ChatsViewModel mHomeViewModel;
    private FragmentChatsBinding mViewBinding;


    @Override
    public void onResume() {
        super.onResume();
//        if(mHomeViewModel!=null && !mHomeViewModel.isFirstIn){
//            mHomeViewModel.reloadData();
//        }
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }


    @Override
    public boolean hideBottomSheet() {
        return false;
    }

    @Override
    public boolean isNeedActivityResult() {
        return true;
    }

    @Override
    public ActivityResultCallBack activityResultCallBack() {
        return this::callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chats;
    }

    @Override
    public ChatsViewModel getViewModel() {
        mHomeViewModel = (ChatsViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(ChatsViewModel.class, getViewDataBinding(), this);
        return mHomeViewModel;
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }


    @Override
    protected void setUp() {
        mViewBinding = getViewDataBinding();
        mHomeViewModel.setUp();
    }

    @Override
    public void callBack(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 222) {
            mHomeViewModel.notifyItem(data.getIntExtra(AppConstants.BundleData.CHAT_POSITION, -1));
        }
    }
}
