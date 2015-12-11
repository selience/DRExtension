package me.darkeet.android.okhttp;

import com.squareup.okhttp.Request;

/**
 * Name: HttpGet
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/11 14:48
 * Desc:
 */
public class HttpGet extends BetterHttpRequest {

    HttpGet(Builder builder) {
        super(builder);
    }

    @Override
    public Request wrapRequest() {
        Request request = new Request.Builder()
                .url(builder.getUrl())
                .tag(builder.getTag())
                .headers(builder.getAllHeaders())
                .build();
        return request;
    }
}
