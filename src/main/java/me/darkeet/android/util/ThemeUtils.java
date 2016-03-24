package me.darkeet.android.util;

import android.app.Activity;
import android.content.res.TypedArray;

public class ThemeUtils {

    private static final int[] ANIM_OPEN_STYLE_ATTRS = { android.R.attr.activityOpenEnterAnimation,
        android.R.attr.activityOpenExitAnimation };
    
    private static final int[] ANIM_CLOSE_STYLE_ATTRS = { android.R.attr.activityCloseEnterAnimation,
        android.R.attr.activityCloseExitAnimation };


    private ThemeUtils() {
        throw new AssertionError();
    }
    
    public static void overrideActivityCloseAnimation(final Activity activity) {
        TypedArray a = activity.obtainStyledAttributes(new int[] { android.R.attr.windowAnimationStyle });
        final int windowAnimationStyleResId = a.getResourceId(0, 0);
        a.recycle();
        // Now retrieve the resource ids of the actual animations used in the
        // animation style pointed to by
        // the window animation resource id.
        a = activity.obtainStyledAttributes(windowAnimationStyleResId, ANIM_CLOSE_STYLE_ATTRS);
        final int activityCloseEnterAnimation = a.getResourceId(0, 0);
        final int activityCloseExitAnimation = a.getResourceId(1, 0);
        a.recycle();
        activity.overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    public static void overrideActivityOpenAnimation(final Activity activity) {

        TypedArray a = activity.obtainStyledAttributes(new int[] { android.R.attr.windowAnimationStyle });
        final int windowAnimationStyleResId = a.getResourceId(0, 0);
        a.recycle();
        // Now retrieve the resource ids of the actual animations used in the
        // animation style pointed to by
        // the window animation resource id.
        a = activity.obtainStyledAttributes(windowAnimationStyleResId, ANIM_OPEN_STYLE_ATTRS);
        final int activityOpenEnterAnimation = a.getResourceId(0, 0);
        final int activityOpenExitAnimation = a.getResourceId(1, 0);
        a.recycle();
        activity.overridePendingTransition(activityOpenEnterAnimation, activityOpenExitAnimation);
    }
}
