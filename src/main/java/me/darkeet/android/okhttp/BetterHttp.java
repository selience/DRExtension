package me.darkeet.android.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import me.darkeet.android.log.DebugLog;

/**
 * Name: BetterHttp
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/11 14:50
 * Desc:
 */
public class BetterHttp {
    private static final long TIME_OUT = 30;

    private OkHttpClient okHttpClient;

    private static class SingtonInstance {
        private static final BetterHttp instance = new BetterHttp();
    }

    public static BetterHttp getInstance() {
        return SingtonInstance.instance;
    }

    private BetterHttp() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(TIME_OUT, TimeUnit.SECONDS);
    }

    public void setOkHttpClient(OkHttpClient httpClient) {
        this.okHttpClient = httpClient;
    }

    public void cancel(Object tag) {
        okHttpClient.cancel(tag);
    }

    public OkHttpClient getHttpClient() {
        return okHttpClient;
    }

    public void enqueue(Request request, Listener callback) {
        enqueue(okHttpClient, request, callback);
    }

    public void enqueue(OkHttpClient httpClient, Request request, final Listener callback) {
        DebugLog.d("okhttp url: " + request.urlString());

        if (callback != null) {
            callback.onStart();
        }

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                if (callback != null) {
                    callback.onSuccess(new BetterHttpResponse(response));
                    callback.onFinish();
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                if (callback != null) {
                    callback.onFailure(request, e);
                    callback.onFinish();
                }
            }
        });
    }


    public void execute(final Request request, final Listener callback) {
        DebugLog.d("okhttp url: " + request.urlString());

        if (callback != null) {
            callback.onStart();
        }

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful() && callback != null) {
                callback.onSuccess(new BetterHttpResponse(response));
            }
        } catch (IOException e) {
            if (callback != null) {
                callback.onFailure(request, e);
            }
        } finally {
            if (callback != null) {
                callback.onFinish();
            }
        }
    }


    public OkHttpClient wrapOkHttpClient(final Listener callback) {
        final OkHttpClient httpClient = okHttpClient.clone();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                ResponseBody responseBody = new CountingResponseBody(response.body(), callback);
                return response.newBuilder().body(responseBody).build();
            }
        });

        return httpClient;
    }
}
