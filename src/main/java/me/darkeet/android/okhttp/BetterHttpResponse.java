package me.darkeet.android.okhttp;

import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Name: BetterHttpResponseImpl
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/11 14:46
 * Desc:
 */
public class BetterHttpResponse {
    private static final String CONTENT_TYPE = "Content-Type";

    private Response response;

    public BetterHttpResponse(Response response) {
        this.response = response;
    }

    public Response networkResponse() {
        return response;
    }

    public InputStream getResponseBody() throws IOException {
        return response.body().byteStream();
    }

    public byte[] getResponseBodyAsBytes() throws IOException {
        return response.body().bytes();
    }

    public String getResponseBodyAsString() throws IOException {
        return new String(getResponseBodyAsBytes(), charsetName("UTF-8"));
    }

    public int getStatusCode() {
        return response.code();
    }

    public Map<String, List<String>> getHeaders() {
        return response.headers().toMultimap();
    }

    public List<String> getHeaders(String header) {
        return response.headers(header);
    }

    public String getHeader(String header) {
        return response.header(header);
    }

    private String charsetName(String defaultValue) {
        String contentType = response.header(CONTENT_TYPE);
        if (contentType != null && contentType.length() > 0) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return defaultValue;
    }
}
