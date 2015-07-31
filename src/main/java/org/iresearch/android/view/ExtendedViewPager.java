package org.iresearch.android.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

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
}
