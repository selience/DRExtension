package me.darkeet.android.okhttp;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Name: BetterHttpRequestBase
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/11 14:47
 * Desc: 封装OkHttp网络请求操作
 */
public abstract class BetterHttpRequest {
    protected Builder builder;
    private Listener callback;
    private BetterHttp betterHttp;

    BetterHttpRequest(Builder builder) {
        this.builder = builder;
        this.callback = builder.getCallback();
        this.betterHttp = BetterHttp.getInstance();
    }

    /**
     * 构造请求提requestBody
     */
    abstract Request wrapRequest();

    /**
     * 处理网络请求
     */
    public void send() {
        OkHttpClient httpClient = betterHttp.getHttpClient();
        if (builder.hasDownload()) {
            httpClient = betterHttp.wrapOkHttpClient(callback);
        }
        betterHttp.enqueue(httpClient, wrapRequest(), callback);
    }

    /**
     * 构造okhttp请求参数
     */
    public static class Builder {
        private String url;
        private Object tag;
        private String storeFilePath;
        private Map<String, String> params;
        private Map<String, String> headers;
        private Map<String, String> uploadFiles;
        private WeakReference<Listener> callback;

        public Builder() {
            this.params = new HashMap<String, String>();
            this.headers = new HashMap<String, String>();
            this.uploadFiles = new HashMap<String, String>();
        }

        public String getUrl() {
            return this.url;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Object getTag() {
            return this.tag;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Map<String, String> getParams() {
            return this.params;
        }

        public Builder addParams(String name, String value) {
            this.params.put(name, value);
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Headers getAllHeaders() {
            return (headers != null ? Headers.of(headers) :
                    Headers.of(new HashMap<String, String>()));
        }

        public Map<String, String> getHeaders() {
            return this.headers;
        }

        public Builder addHeader(String name, String value) {
            this.headers.put(name, value);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Map<String, String> getUploadFiles() {
            return this.uploadFiles;
        }

        public boolean hasUpload() {
            return (uploadFiles != null && uploadFiles.size() > 0);
        }

        public Builder upload(String name, String value) {
            this.uploadFiles.put(name, value);
            return this;
        }

        public Builder uploads(Map<String, String> uploadFiles) {
            this.uploadFiles = uploadFiles;
            return this;
        }

        public String getStorePath() {
            return this.storeFilePath;
        }

        public Builder storePath(String storePath) {
            this.storeFilePath = storePath;
            return this;
        }

        public boolean hasDownload() {
            return (storeFilePath != null && storeFilePath.length() > 0);
        }


        public Listener getCallback() {
            return (callback != null ? callback.get() : null);
        }

        public Builder callback(Listener callback) {
            this.callback = new WeakReference<Listener>(callback);
            return this;
        }

        public Builder callback(ResponseCallback callback) {
            ResponseListener listener = new ResponseListener();
            listener.setResponseCallback(callback);
            callback(listener);
            return this;
        }

        public void get() {
            new HttpGet(this).send();
        }

        public void post() {
            new HttpPost(this).send();
        }
    }
}
