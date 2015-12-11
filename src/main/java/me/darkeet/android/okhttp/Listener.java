package me.darkeet.android.okhttp;

import com.squareup.okhttp.Request;

/**
 * Name: Linstener
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/8 19:07
 * Desc: 网络请求回调函数
 */
public abstract class Listener {

    public void onStart() {
    }

    public void onCancel() {
    }

    public void onFinish() {
    }

    public void onProgress(long transferred, long totalSize) {
    }

    public abstract void onSuccess(BetterHttpResponse response);


    public abstract void onFailure(Request request, Throwable throwable);
}
