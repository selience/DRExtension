package me.darkeet.android.util;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import java.util.List;

/**
 * Name: DebugModeUtils
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/29 12:22
 * Desc: Facebook出品的一个强大的Android调试工具，文档：https://facebook.github.io/stetho/
 */
public class DebugModeUtils {

    public static void initForHttpClient(final OkHttpClient client) {
        final List<Interceptor> interceptors = client.networkInterceptors();
        interceptors.add(new StethoInterceptor());
    }

    public static void initForApplication(final Application application) {
        Stetho.initialize(Stetho.newInitializerBuilder(application)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(application))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
                .build());
    }
}
