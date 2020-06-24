package com.selwantech.raheeb.ui.accountjourney.payment.pay;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.selwantech.raheeb.databinding.FragmentPaymentWebviewBinding;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.ui.base.BaseNavigator;
import com.selwantech.raheeb.ui.base.BaseViewModel;

import androidx.databinding.ViewDataBinding;

public class PayViewModel extends BaseViewModel<PayNavigator, FragmentPaymentWebviewBinding> {

    final String SUCCESS_URL = "https://moyasar.com/docs/payments/mobile-apps-integration/#step-3-add-the";
    final String FAILED_URL = "https://moyasar.com/docs/payments/mobile-apps-integration/#step-3-add-the-webview";
    WebSettings webSettings;

    public <V extends ViewDataBinding, N extends BaseNavigator> PayViewModel(Context mContext, DataManager dataManager, V viewDataBinding, N navigation) {
        super(mContext, dataManager, (PayNavigator) navigation, (FragmentPaymentWebviewBinding) viewDataBinding);

    }

    @Override
    protected void setUp() {
        setupWebView();
    }

    private void setupWebView() {

        webSettings = getViewBinding().webview.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);

        getViewBinding().webview.setWebViewClient(new Browser_home());
        getViewBinding().webview.setWebChromeClient(new MyChrome());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        final String url = "https://moyasar.com/docs/payments/mobile-apps-integration/#step-3-add-the-webview";
        getViewBinding().webview.post(new Runnable() {
            @Override
            public void run() {
                getViewBinding().webview.loadUrl(url);
            }
        });

    }

    public void updateEmail() {
//        getDataManager().getAuthService().updateEmail(getMyContext(), true, getViewBinding().edEmail.getText().toString(), new APICallBack<User>() {
//            @Override
//            public void onSuccess(User response) {
//                response.setToken(User.getInstance().getToken());
//                User.getInstance().setObjUser(response);
//                SessionManager.createUserLoginSession();
//                popUp();
//            }
//
//            @Override
//            public void onError(String error, int errorCode) {
//                showSnackBar(getMyContext().getResources().getString(R.string.error),
//                        error, getMyContext().getResources().getString(R.string.ok), new SnackViewBulider.SnackbarCallback() {
//                            @Override
//                            public void onActionClick(Snackbar snackbar) {
//                                snackbar.dismiss();
//                            }
//                        });
//            }
//        });
    }

    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (url.equals(SUCCESS_URL)) {
                Toast.makeText(getMyContext(), "Success! ",
                        Toast.LENGTH_SHORT).show();
                popUp();
            } else if (url.equals(FAILED_URL)) {
                Toast.makeText(getMyContext(), "Something Wrong! ",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class MyChrome extends WebChromeClient {

        MyChrome() {
        }

    }

}
