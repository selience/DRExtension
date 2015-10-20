package me.darkeet.android.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Name: ExtendedViewPager
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: ViewPager扩展类，增加设置是否滚动，自动适应子视图高度；
 */
public class ExtendedViewPager extends ViewPager {

    public ExtendedViewPager(final Context context) {
        this(context, null);
    }

    public ExtendedViewPager(final Context context, final AttributeSet attrs) {
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int childHeight = childView.getMeasuredHeight();
            // 取值最大高度
            heightSize = Math.max(heightSize, childHeight);
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
    }
}
