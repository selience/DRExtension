package me.darkeet.android.okhttp;

import android.os.Handler;
import android.os.Looper;
import com.squareup.okhttp.Request;

/**
 * Name: ResponseCallback
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/9 16:05
 * Desc: 封装监听函数，回调结果到主线程；
 */
public final class ResponseListener<Result> extends Listener {
    private Handler mHandler;
    private ResponseCallback<Result> mResponseCallback;

    public ResponseListener() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onStart() {
        if (mResponseCallback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onPreExecute();
                }
            });
        }
    }

    @Override
    public void onSuccess(final BetterHttpResponse response) {
        if (mResponseCallback != null) {
            final Result result = mResponseCallback.doInBackground(response);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onPostExecute(result);
                }
            });
        }
    }

    @Override
    public void onFailure(Request request, final Throwable throwable) {
        if (mResponseCallback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onError(throwable);
                }
            });
        }
    }

    @Override
    public void onCancel() {
        if (mResponseCallback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onCancel();
                }
            });
        }
    }

    @Override
    public void onFinish() {
        if (mResponseCallback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onFinish();
                }
            });
        }
    }

    @Override
    public void onProgress(final long transferred, final long totalSize) {
        if (mResponseCallback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onProgressUpdate(transferred, totalSize);
                }
            });
        }
    }

    public void setResponseCallback(ResponseCallback<Result> responseCallback) {
        mResponseCallback = responseCallback;
    }
}
