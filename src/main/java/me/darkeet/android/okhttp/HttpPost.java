package me.darkeet.android.okhttp;

import java.io.File;
import java.util.Map;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Name: HttpPost
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/11 15:41
 * Desc:
 */
public class HttpPost extends BetterHttpRequest {
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    HttpPost(Builder builder) {
        super(builder);
    }

    @Override
    Request wrapRequest() {
        RequestBody requestBody;

        if (!builder.hasUpload()) {
            requestBody = createRequestBody();
        } else {
            requestBody = createMultipartRequestBody();
        }

        Request request = new Request.Builder()
                .url(builder.getUrl())
                .tag(builder.getTag())
                .headers(builder.getAllHeaders())
                .post(requestBody)
                .build();
        return request;
    }

    private RequestBody createRequestBody() {
        final Map<String, String> params = builder.getParams();
        final FormEncodingBuilder builder = new FormEncodingBuilder();

        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.add(param.getKey(), param.getValue());
        }
        return builder.build();
    }

    private RequestBody createMultipartRequestBody() {
        final Map<String, String> params = builder.getParams();
        final Map<String, String> uploadFiles = builder.getUploadFiles();
        final MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

        for (Map.Entry<String, String> param : params.entrySet()) {
            multipartBuilder.addFormDataPart(param.getKey(), param.getValue());
        }

        for (String key : uploadFiles.keySet()) {
            File file = new File(uploadFiles.get(key));

            if (!file.exists()) {
                throw new IllegalArgumentException(String.format("File not found: %s", file.getAbsolutePath()));
            }

            if (file.isDirectory()) {
                throw new IllegalArgumentException(String.format("File is a directory: %s", file.getAbsolutePath()));
            }

            multipartBuilder.addFormDataPart(key, file.getName(), RequestBody.create(
                    MediaType.parse(DEFAULT_CONTENT_TYPE), file));
        }

        return new CountingRequestBody(multipartBuilder.build(), builder.getCallback());
    }
}
