
package me.darkeet.android.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.File;

/**
 * Mostly general and UI helpers.
 */
public final class DeviceUtils {

    private DeviceUtils() {
    }

    public static int sdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断是否是模拟器
     */
    public static boolean isEmulator() {
        return Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
    }

    /**
     * 判断是否为平板设备（官方用法）
     */
    public static boolean isTablet(final Context context) {
        return (context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断设备当前方向是否为横向
     */
    public static final boolean isLandscape(final Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否获取root权限
     */
    public static boolean isRoot() {
        try {
            return (!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * dp转换px
     */
    public static float dp2px(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getMetrics(context));
    }

    /**
     * px转换dp
     */
    public static float px2dp(Context context, float pxValue) {
        final float scale = getMetrics(context).density;
        return (pxValue / scale + 0.5f);
    }

    /**
     * sp转换px
     */
    public static float sp2px(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getMetrics(context));
    }

    /**
     * px转换dp
     */
    public static float px2sp(Context context, float value) {
        float scaledDensity = getMetrics(context).scaledDensity;
        return (value / scaledDensity + 0.5f);
    }

    /**
     * 获取设备的Metrics对象
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 获取屏幕的宽度
     */
    public static float getScreenWidth(Context context) {
        return getMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕的高度
     */
    public static float getScreenHeight(Context context) {
        return getMetrics(context).heightPixels;
    }

    /**
     * 屏幕分辨率，字符串显示 (例如：640*960)
     */
    public static String getDeviceForResolution(Context context) {
        int width = getMetrics(context).widthPixels;
        int height = getMetrics(context).heightPixels;
        return String.format("%s*%s", width, height);
    }

    /**
     * 精确获取屏幕尺寸（例如：3.5、4.0、5.0寸屏幕）
     */
    public static double getDevicePhysicalSize(Context context) {
        DisplayMetrics dm = getMetrics(context);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2)
                + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }

    /**
     * 获取设备唯一标识id
     */
    public static String getAndroidId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        if (androidId == null) {
            // this happens on 1.6 and older
            androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        }
        if (androidId == null) {
            androidId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }
        return androidId;
    }
}
