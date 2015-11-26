package me.darkeet.android.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

/**
 * Name: DebugModeUtils
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/29 12:22
 * Desc: 图片相关操作
 */
public class BitmapUtils {

    private BitmapUtils() {
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }


    public static Bitmap getBitmapForAssest(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        InputStream ips = null;
        Bitmap bitmap = null;
        try {
            ips = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(ips);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
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

    private static void closeStream(Closeable is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
