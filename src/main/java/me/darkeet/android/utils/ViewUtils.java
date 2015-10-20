
package me.darkeet.android.utils;

import android.view.View;
import android.app.Activity;
import android.view.ViewGroup;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.annotation.SuppressLint;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * Utilities for working with the {@link View} class
 */
public class ViewUtils {

    private ViewUtils() {
    }

    public static View inflate(Context context, int resource, 
    		ViewGroup root, boolean attachToRoot) {
        LayoutInflater factory = LayoutInflater.from(context);
        return factory.inflate(resource, root, attachToRoot);
    } 
    
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View res = viewHolder.get(id);
        if (res == null) {
            res = view.findViewById(id);
            viewHolder.put(id, res);
        }
        return (T) res;
    }
    
   /**
    * Runs a piece of code after the next layout run
    *
    * @param view The {@link View} used.
    * @param runnable The {@link Runnable} used after the next layout run
    */
    @SuppressLint("NewApi")
    public static void doGlobalLayout(final View view, final Runnable runnable) {
        @SuppressWarnings("deprecation")
        final OnGlobalLayoutListener listener = new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /* Layout pass done, unregister for further events */
                if (DeviceUtils.hasJellyBean()) {
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
