package me.darkeet.android.okhttp;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Name: CountingResponseBody
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/9 14:41
 * Desc: 包装响应体内容，用于处理文件下载；
 */
public class CountingResponseBody extends ResponseBody {
    private Listener listener;
    private ResponseBody delegate;
    private CountingSource countingSource;

    public CountingResponseBody(ResponseBody delegate, Listener listener) {
        this.delegate = delegate;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return delegate.contentLength();
    }


    @Override
    public BufferedSource source() throws IOException {
        BufferedSource bufferedSource;

        countingSource = new CountingSource(delegate.source());
        bufferedSource = Okio.buffer(countingSource);

        return bufferedSource;
    }


    private final class CountingSource extends ForwardingSource {

        private long totalBytesRead = 0;

        public CountingSource(Source delegate) {
            super(delegate);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            totalBytesRead += (bytesRead != -1 ? bytesRead : 0);

            if (listener != null) {
                listener.onProgress(totalBytesRead, contentLength());
            }

            return bytesRead;
        }
    }
}