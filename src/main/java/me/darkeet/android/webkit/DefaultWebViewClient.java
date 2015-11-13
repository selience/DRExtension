package me.darkeet.android.webkit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DefaultWebViewClient extends WebViewClient {

    private final Activity mActivity;

    public DefaultWebViewClient(final Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(final WebView view, final String url) {
        super.onPageFinished(view, url);
        mActivity.setTitle(view.getTitle());
    }

    @Override
    @TargetApi(Build.VERSION_CODES.FROYO)
    public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
        handler.proceed();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        view.stopLoading();
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        view.loadUrl(url);
        return true;
    }
}
