package me.darkeet.android.compat;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

public class BitmapSizeCompat {

	/**
	 * Get the size in bytes of a bitmap.
	 * 
	 * @param bitmap
	 * @return size in bytes
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public static int getBitmapSize(final Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) 
			return bitmap.getByteCount();
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}
