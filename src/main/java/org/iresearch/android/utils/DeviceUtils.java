
package org.iresearch.android.utils;

import java.io.File;
import java.util.UUID;
import android.os.Build;
import android.util.TypedValue;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import java.io.UnsupportedEncodingException;

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
     * Build.VERSION_CODES.FROYO
     */
    public static boolean hasFroyo() {
        return sdkVersion() >= 8;
    }
    
    /**
     * Build.VERSION_CODES.GINGERBREAD
     */
    public static boolean hasGingerbread() {
        return sdkVersion() >= 9;
    }
    
   /**
    * Build.VERSION_CODES.HONEYCOMB
    */
    public static boolean hasHoneycomb() {
        return sdkVersion() >= 11;
    }

   /**
    * Build.VERSION_CODES.HONEYCOMB_MR1
    */
    public static boolean hasHoneycombMR1() {
        return sdkVersion() >= 12;
    }

    /**
     * Build.VERSION_CODES.ICE_CREAM_SANDWICH
     */
    public static boolean hasICS() {
        return sdkVersion() >= 14;
    }

   /**
    * Build.VERSION_CODES.JELLY_BEAN
    */
    public static final boolean hasJellyBean() {
        return sdkVersion() >= 16;
    }
    
    /**
     * Build.VERSION_CODES.JELLY_BEAN_MR1
     */
    public static final boolean hasJellyBeanMR1() {
        return sdkVersion() >= 17;
    }
    
    /**
     * Build.VERSION_CODES.JELLY_BEAN_MR2
     */
    public static final boolean hasJellyBeanMR2() {
        return sdkVersion() >= 18;
    }
    

    /**
     * 判断是否是模拟器
     */
    public static boolean isEmulator() {
        return Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
    }

   /**
    * Used to determine if the current device is a Google TV
    *
    * @param context The {@link Context} to use
    * @return True if the device has Google TV, false otherwise
    */
    public static boolean isGoogleTV(Context context) {
        return context.getPackageManager().hasSystemFeature("com.google.android.tv");
    }

   /**
    * Used to determine if the device is a tablet or not
    *
    * @param context The {@link Context} to use.
    * @return True if the device is a tablet, false otherwise.
    */
    public static boolean isTablet(final Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) 
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断是否是平板（官方用法）
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
     * 获取dimen值，如果是dp或sp，则乘以density，px不乘；功能与getDimensionPixelOffset类似；
     * 
     * @param context
     * @param resId
     * @return
     */
    public static float getDimension(Context context, int resId) {
    	return context.getResources().getDimension(resId);
    }
    
    /**
     * 获取dimen值，如果是dp或sp，则乘以density，px不乘；功能与getDimension类似；
     * 
     * @param context
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(Context context, int resId) {
    	return context.getResources().getDimensionPixelOffset(resId);
    }
    
    /**
     * 获取dimen值，所有的值都会乘以density；
     * 
     * @param context
     * @param resId
     * @return
     */
    public static int getDimensionPixelSize(Context context, int resId) {
    	return context.getResources().getDimensionPixelSize(resId);
    }
    
    /**
     * px单位转换dp单位
     * 
     * @param ctx
     * @param px
     * @return
     */
    public static float px2dp(Context context, float px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, getMetrics(context));
    }

    /**
     * dp单位转换px单位
     * 
     * @param ctx
     * @param dp
     * @return
     */
    public static float dp2Px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getMetrics(context));
    }

    /**
     * px单位转换sp单位
     * 
     * @param ctx
     * @param px
     * @return
     */
    public static float px2sp(Context context, float px) {
        float scaledDensity=getMetrics(context).scaledDensity;
        return (px / scaledDensity);
    }

    /**
     * sp单位转换px单位
     * 
     * @param ctx
     * @param px
     * @return
     */
    public static float sp2px(Context context, float sp) {
        float scaledDensity=getMetrics(context).scaledDensity;
        return sp * scaledDensity;
    }

    /**
     * 获取设备的密度大小
     */
    public static float getDensity(Context context) {
        return getMetrics(context).density;
    }

    /**
     * 获取设备的密度因子
     */
    public static int getDensitydpi(Context context) {
        return getMetrics(context).densityDpi;
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
     * 获取屏幕的宽高
     */
    public static Point display(Context context) {
        DisplayMetrics dm=getMetrics(context);
        Point outSize=new Point(dm.widthPixels, dm.heightPixels);
        return outSize;
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
        int width=getMetrics(context).widthPixels;
        int height=getMetrics(context).heightPixels;
        return String.format("%s*%s", width, height);
    }

    /**
     * 精确获取屏幕尺寸（例如：3.5、4.0、5.0寸屏幕）
     */
    public static double getDevicePhysicalSize(Context context) {
        DisplayMetrics dm=getMetrics(context);
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2)
                + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }
    
   /**
    * Used to determine if the device is currently in landscape mode
    *
    * @param context The {@link Context} to use.
    * @return True if the device is in landscape mode, false otherwise.
    */
    public static final boolean isLandscape(final Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Returns the ANDROID_ID unique device ID for the current device. Reading
     * that ID has changed between platform versions, so this method takes care
     * of attempting to read it in different ways, if one failed.
     * 
     * @param context the context
     * @return the device's ANDROID_ID, or null if it could not be determined
     * @see Secure#ANDROID_ID
     */
    @SuppressWarnings("deprecation")
    public static String getAndroidId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        if (androidId == null) {
            // this happens on 1.6 and older
            androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        }
        if (androidId == null) {
            androidId = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }
        return androidId;
    }

    /**
     * 获取设备唯一标识ID
     */
    public static String generateDeviceId(Context context) {
        UUID uuid = null;
        final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        try {
            // ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置
            // 它在Android <=2.1 or Android >=2.3的版本是可靠、稳定的，但在2.2的版本并不是100%可靠的
            // 在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
            if (androidId != null && !"9774d56d682e549c".equals(androidId)) {
                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("UTF-8"));
            } else {
                // 根据不同的手机设备返回IMEI，MEID或者ESN码
                // 非手机设备就没有这个DEVICE_ID，获取DEVICE_ID需要READ_PHONE_STATE权限
                final String deviceId = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                if (deviceId != null) {
                    uuid = UUID.nameUUIDFromBytes(deviceId.getBytes("UTF-8"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // UUID经常用来标识在某个应用中的唯一ID
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return uuid.toString().replace("-", "");
    }
}
