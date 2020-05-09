package com.selwantech.raheeb.ui.filebox;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.ViewModelProviderFactory;
import com.selwantech.raheeb.databinding.ActivityFileBoxBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseActivity;

import java.io.File;

import javax.inject.Inject;

public class FileBoxActivity extends BaseActivity<ActivityFileBoxBinding, FileBoxViewModel>
        implements FileBoxNavigator {

    String url = "";
    @Inject
    ViewModelProviderFactory factory;
    private FileBoxViewModel mFileBoxViewModel;
    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            hideLoading();
            mFileBoxViewModel.openFile(new File(mFileBoxViewModel.getState()));
            mFileBoxViewModel.setmFilePath("");
            mFileBoxViewModel.setState("");
        }
    };
    private ActivityFileBoxBinding mViewBinding;

    public static Intent getStartIntent(Context context, String url) {
        Intent intent = new Intent(context, FileBoxActivity.class);
        intent.putExtra("url", url);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return com.selwantech.raheeb.BR.viewModel;
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_file_box;
    }

    @Override
    public FileBoxViewModel getViewModel() {
        mFileBoxViewModel = (FileBoxViewModel) new ViewModelProviderFactory(DataManager.getInstance(), getMyContext())
                .create(FileBoxViewModel.class, getViewDataBinding(), this);
        return mFileBoxViewModel;
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = getViewDataBinding();
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        url = getIntent().getStringExtra("url");
        mFileBoxViewModel.downloadFile(url);
    }
}