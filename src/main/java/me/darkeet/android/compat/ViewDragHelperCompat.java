package me.darkeet.android.compat;

import java.lang.reflect.Field;
import android.support.v4.widget.ViewDragHelper;

public class ViewDragHelperCompat {

    private ViewDragHelperCompat() {
        throw new AssertionError();
    }

    public static boolean setEdgeSize(final ViewDragHelper helper, final int edgeSize) {
        try {
            final Field f = helper.getClass().getField("mEdgeSize");
            f.setAccessible(true);
            f.setInt(helper, edgeSize);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
}

