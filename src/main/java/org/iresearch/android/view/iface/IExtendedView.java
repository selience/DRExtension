package org.iresearch.android.view.iface;

import android.view.MotionEvent;
import android.view.View;

public interface IExtendedView {

	public void setOnSizeChangedListener(final OnSizeChangedListener listener);

	public void setTouchInterceptor(final TouchInterceptor listener);

	public static interface OnSizeChangedListener {
		void onSizeChanged(View view, int w, int h, int oldw, int oldh);
	}

	public static interface TouchInterceptor {

		boolean dispatchTouchEvent(View view, MotionEvent event);

		boolean onInterceptTouchEvent(View view, MotionEvent event);

		boolean onTouchEvent(View view, MotionEvent event);
	}
}
