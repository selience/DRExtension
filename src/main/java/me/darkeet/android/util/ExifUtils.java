package me.darkeet.android.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import java.io.IOException;

/**
 * Name: ExifUtils
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/26 15:23
 * Desc: 根据Exif数据信息调整Bitmap位置
 */
public class ExifUtils {

    private ExifUtils() {
    }

    public static Bitmap rotateBitmap(Uri fileUri, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        try {
            ExifInterface exif = new ExifInterface(fileUri.getPath());
            int rotation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static int exifToDegrees(int exifOrientation) {
        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
        }
        return 0;
    }
}
