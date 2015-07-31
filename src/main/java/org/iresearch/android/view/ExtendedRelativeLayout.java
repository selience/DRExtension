package org.iresearch.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import org.iresearch.android.view.iface.IExtendedView;

public class ExtendedRelativeLayout extends RelativeLayout implements IExtendedView {

	private TouchInterceptor mTouchInterceptor;
	private OnSizeChangedListener mOnSizeChangedListener;

	public ExtendedRelativeLayout(final Context context) {
		super(context);
	}

	public ExtendedRelativeLayout(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public ExtendedRelativeLayout(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public final boolean dispatchTouchEvent(final MotionEvent event) {
		if (mTouchInterceptor != null) {
			final boolean ret = mTouchInterceptor.dispatchTouchEvent(this, event);
			if (ret) return true;
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public final boolean onInterceptTouchEvent(final MotionEvent event) {
		if (mTouchInterceptor != null) {
			final boolean ret = mTouchInterceptor.onInterceptTouchEvent(this, event);
			if (ret) return true;
		}
		return super.onInterceptTouchEvent(event);
	}

	@Override
	public final boolean onTouchEvent(final MotionEvent event) {
		if (mTouchInterceptor != null) {
			final boolean ret = mTouchInterceptor.onTouchEvent(this, event);
			if (ret) return true;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public final void setOnSizeChangedListener(final OnSizeChangedListener listener) {
		mOnSizeChangedListener = listener;
	}

	@Override
	public final void setTouchInterceptor(final TouchInterceptor listener) {
		mTouchInterceptor = listener;
	}

	@Override
	protected final void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mOnSizeChangedListener != null) {
			mOnSizeChangedListener.onSizeChanged(this, w, h, oldw, oldh);
		}
	}

}
