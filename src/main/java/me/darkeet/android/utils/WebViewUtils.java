
package me.darkeet.android.utils;

import android.webkit.WebView;
import android.content.res.Resources.NotFoundException;

public class WebViewUtils {
    /**
     * Load the contents of a raw resource into the given {@link WebView}.
     *
     * @param webView   The {@link WebView} to load data into.
     * @param pageResId The id of a resource in the {@code raw} folder.
     * @throws NotFoundException If the given ID does not exist.
     */
    public static void loadFromRaw(WebView webView, int pageResId) {
        String html = IoUtils.readFully(webView.getContext().getResources().openRawResource(pageResId));
        html = reworkForWebView(html);
        webView.loadData(html, "text/html", "utf-8");
    }

    private static String reworkForWebView(String s) {
        return s.replace("\n", " ");
    }
}
