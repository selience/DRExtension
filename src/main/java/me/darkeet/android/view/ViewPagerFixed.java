package me.darkeet.android.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Name: ExtendedViewPager
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: ViewPager扩展类，增加设置是否滚动，自动适应子视图高度；
 */
public class ViewPagerFixed extends ViewPager {

    public ViewPagerFixed(final Context context) {
        this(context, null);
    }

    public ViewPagerFixed(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        try {
            if (!isEnabled()) return false;
            return super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        try {
            if (!isEnabled()) return false;
            return super.onTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
