package me.darkeet.android.retrofit.logging;

import java.io.IOException;
import me.darkeet.android.log.DebugLog;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Name: LoggingInterceptor
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/15 13:44
 * Desc: 记录okhttp请求信息
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        DebugLog.d(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        DebugLog.d(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
