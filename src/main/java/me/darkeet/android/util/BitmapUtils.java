package me.darkeet.android.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Name: DebugModeUtils
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/29 12:22
 * Desc: 图片相关操作
 */
public class BitmapUtils {

    private BitmapUtils() {
    }

    /**
     * Bitmap转换drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * Drawable转换Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Bitmap转换字节数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap,
                                           CompressFormat format) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            bitmap.compress(format, 100, bos);
            return bos.toByteArray();
        } finally {
            IoUtils.closeSilently(bos);
        }
    }

    public static void saveBitmapToDisk(Bitmap bitmap, File filePath) {
        if (filePath == null || bitmap == null) {
            return;
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(out);
        }
    }


    public static Bitmap decodeByteArray(byte[] data, int minWidth, int minHeight) {
        BitmapFactory.Options options = null;

        if (minWidth > 0 || minHeight > 0) {
            // First decode with inJustDecodeBounds=true to check dimensions
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, minWidth, minHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
        }

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }


    public static Bitmap decodeFile(String path, int minWidth, int minHeight) {
        BitmapFactory.Options options = null;

        if (minWidth > 0 || minHeight > 0) {
            // First decode with inJustDecodeBounds=true to check dimensions
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, minWidth, minHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
        }

        return BitmapFactory.decodeFile(path, options);
    }


    public static Bitmap decodeResources(Resources res, int resId, int minWidth,
                                         int minHeight) {
        BitmapFactory.Options options = null;

        if (minWidth > 0 || minHeight > 0) {
            // First decode with inJustDecodeBounds=true to check dimensions
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, minWidth, minHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
        }

        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options o, int minWidth, int minHeight) {
        // Raw height and width of image
        final int height = o.outHeight;
        final int width = o.outWidth;
        int inSampleSize = 1;

        if (height > minHeight || width > minWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > minHeight
                    && (halfWidth / inSampleSize) > minWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    private static void closeStream(Closeable is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 回收Bitmap
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
