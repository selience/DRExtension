package me.darkeet.android.compat;

import android.os.Build;
import android.annotation.TargetApi;
import android.webkit.WebSettings;

public class WebSettingsCompat {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setAllowUniversalAccessFromFileURLs(final WebSettings settings, final boolean flag) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return;
        settings.setAllowUniversalAccessFromFileURLs(flag);
    }
}
