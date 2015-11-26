
package me.darkeet.android.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * Utilities for working with the {@link View} class
 */
public class ViewUtils {

    private ViewUtils() {
    }

    public static <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    public static <T extends View> T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    /**
     * Runs a piece of code after the next layout run
     *
     * @param view     The {@link View} used.
     * @param runnable The {@link Runnable} used after the next layout run
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void doGlobalLayout(final View view, final Runnable runnable) {
        final OnGlobalLayoutListener listener = new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /* Layout pass done, unregister for further events */
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                runnable.run();
            }
        };
        view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }
}
