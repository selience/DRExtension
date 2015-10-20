package me.darkeet.android.compat;

import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.annotation.TargetApi;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;

public class ViewAccessor {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setLayerType(final View view, final int layerType, final Paint paint) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) return;
		view.setLayerType(layerType, paint);
	}
	
	public static void enableHwAccelIfNecessary(final View view) {
        if (ViewCompat.getLayerType(view) != ViewCompat.LAYER_TYPE_HARDWARE) {
            ViewCompat.setLayerType(view, ViewCompat.LAYER_TYPE_HARDWARE, null);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackground(final View view, final Drawable background) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(background);
        } else {
            view.setBackground(background);
        }
    }
	
    /**
     * refrence "ViewCompat.setAlpha(view, value)"
     * 
     * @param view
     * @param alpha
     */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setAlpha(final View view, final float alpha) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
	        final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
	        animation.setDuration(0);
	        animation.setFillAfter(true);
	        view.startAnimation(animation);
	    } else {
	        view.setAlpha(alpha);
	    }
	}
}
