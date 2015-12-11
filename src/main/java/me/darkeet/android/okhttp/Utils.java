package me.darkeet.android.okhttp;

import com.squareup.okhttp.Response;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Name: Utils
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/11 17:59
 * Desc:
 */
public class Utils {

    /**
     * 存储文件
     *
     * @param response
     * @param storeFilePath
     */
    public static void storeFile(Response response, String storeFilePath) {
        if (storeFilePath == null || storeFilePath.length() == 0) {
            throw new IllegalArgumentException("The store file path must not be null");
        }

        if (new File(storeFilePath).isDirectory()) {
            throw new IllegalArgumentException("The store file path must not be directory");
        }

        BufferedSink bufferedSink = null;
        BufferedSource bufferedSource = null;

        try {

            bufferedSink = Okio.buffer(Okio.sink(new File(storeFilePath)));
            bufferedSource = Okio.buffer(Okio.source(response.body().byteStream()));

            Buffer source = null;
            long byteCount = 0;

            while (bufferedSource.read(source, byteCount) != -1) {
                bufferedSink.write(source, byteCount);
            }
            bufferedSink.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(bufferedSink);
            closeSilently(bufferedSource);
        }
    }

    public static void closeSilently(Closeable... toClose) {
        for (Closeable closeable : toClose) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
