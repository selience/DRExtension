package me.darkeet.android.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import android.view.View;
import java.net.URLEncoder;
import android.view.Window;
import android.webkit.URLUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import java.io.UnsupportedEncodingException;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import me.darkeet.android.BuildConfig;

public final class Utils {

    private Utils() {
        throw new AssertionError("You are trying to create an instance for this utility class!");
    }
    
    public static boolean isDebugBuild() {
        return BuildConfig.DEBUG;
    }
    
    /**
     * 检查字符串是否存在值
     * 
     * @param str 待检验的字符串
     * @return 当 字符串不为 NULL或空字符， 就返回 true；否则false.
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }
    
    /**
     * 初始化AsyncTask异步任务类
     */
    public static void initializeAsyncTask() {
        // AsyncTask class needs to be loaded in UI thread.
        // So we load it here to comply the rule.
        try {
            Class.forName(AsyncTask.class.getName());
        } catch (final ClassNotFoundException e) {
        }
    }
    
    /**
     * 获取应用的缓存目录
     * 
     * @param context
     * @param cacheDirName
     * @return
     */
    public static File getBestCacheDir(final Context context, final String cacheDirName) {
        if (context == null) throw new NullPointerException();
        final File extCacheDir;
        try {
            extCacheDir = context.getExternalCacheDir();
        } catch (final Exception e) {
            return new File(context.getCacheDir(), cacheDirName);
        }
        if (extCacheDir != null && extCacheDir.isDirectory()) {
            final File cacheDir = new File(extCacheDir, cacheDirName);
            if (cacheDir.isDirectory() || cacheDir.mkdirs()) return cacheDir;
        }
        return new File(context.getCacheDir(), cacheDirName);
    }
    
    /**
     * 获取设备当前电量状态
     * 
     * @param context
     * @return
     */
    public static boolean isBatteryOkay(final Context context) {
        if (context == null) return false;
        final Context app = context.getApplicationContext();
        final IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        final Intent intent = app.registerReceiver(null, filter);
        if (intent == null) return false;
        final boolean plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) != 0;
        final float level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        final float scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        return plugged || level / scale > 0.15f;
    }
    
    /**
     * 判断URL的有效性
     * 
     * @param text
     * @return
     */
    public static boolean isValidUrl(final CharSequence text) {
        if (TextUtils.isEmpty(text)) return false;
        return URLUtil.isValidUrl(text.toString());
    }
    
    /**
     * 对参数进行UTF-8编码，并替换特殊字符
     * 
     * @param decString 待编码的参数字符串
     * @return 完成编码转换的字符串
     */
    public static String encodeQueryParams(String decString) {
        try {
            return URLEncoder.encode(decString, "UTF-8").replace("+", "%20")
                    .replace("*", "%2A").replace("%7E", "~").replace("#", "%23");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /**
     * 将 %XX 换为原符号，并进行UTF-8反编码
     * 
     * @param encString 待反编码的参数字符串
     * @return 未进行UTF-8编码和字符替换的字符串
     */
    public static String decodeQueryParams(String encString) {
        int nCount = 0;
        for (int i = 0; i < encString.length(); i++) {
            if (encString.charAt(i) == '%') {
                i += 2;
            }
            nCount++;
        }

        byte[] sb = new byte[nCount];

        for (int i = 0, index = 0; i < encString.length(); i++) {
            if (encString.charAt(i) != '%') {
                sb[index++] = (byte) encString.charAt(i);
            } else {
                StringBuilder sChar = new StringBuilder();
                sChar.append(encString.charAt(i + 1));
                sChar.append(encString.charAt(i + 2));
                sb[index++] = Integer.valueOf(sChar.toString(), 16).byteValue();
                i += 2;
            }
        }
        String decode = "";
        try {
            decode = new String(sb, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode;
    }
    
    /**
     * 设置全屏模式
     * @param activity
     * @param isFullScreen true 设置全拼; false 退出全屏；
     */
    public static void setFullScreen(Activity activity, boolean isFullScreen) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if (isFullScreen) {
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
    
    /**
     * Returns the location of the view on the screen. The screen includes the
     * 'notification area' (aka 'status bar').
     */
    public static Rect getLocationInScreen(View v) {
        int[] location = new int[2];
        v.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        int width = v.getWidth();
        int height = v.getHeight();
        Rect rectPick = new Rect(x, y, x + width, y + height);
        return rectPick;
    }

    /**
     * Returns the location of the view on its window. The window does not
     * include the 'notification area' (aka 'status bar').
     */
    public static Rect getLocationInWindow(View v) {
        // Height of status bar
        Rect rect = new Rect();
        ((Activity) v.getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        Rect res = getLocationInScreen(v);

        res.offset(0, -statusBarHeight);
        return res;
    }
    
    /**
     * 强制隐藏输入法窗口
     */
    public static void hideSoftInputFromWindow(View view) {
        // 实例化输入法控制对象，通过hideSoftInputFromWindow来控制
        final Context context=view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    /**
     * 平移动画处理
     */
    public static void moveFrontAnimation(View v, int startX, int toX, int startY, int toY) {
        TranslateAnimation anim = new TranslateAnimation(startX, toX, startY, toY);
        anim.setDuration(200);
        anim.setFillAfter(true);
        v.startAnimation(anim);
    }
    
    /**
     * 扫描指定文件到媒体库
     * 
     * @param context
     * @param paths
     * @param scanListener
     */
    public static void scanFile(Context context, String[] paths, OnScanCompletedListener scanListener) {
        MediaScannerConnection.scanFile(context, paths, null, scanListener);
    }
    
    /**
     * 获取方法调用状态信息
     * 
     * @param e
     * @return
     */
    public static String getStackMessageString(Throwable e) {
        StringBuilder message = new StringBuilder();
        StackTraceElement[] stack = e.getStackTrace();
        StackTraceElement stackLine = stack[(stack.length - 1)];
        message.append(stackLine.getFileName());
        message.append(":");
        message.append(stackLine.getLineNumber());
        message.append(":");
        message.append(stackLine.getMethodName());
        message.append(" ");
        message.append(e.getMessage());
        return message.toString();
    }

    /**
     * 获取程序异常日志信息，也可调用Log.getStackTraceString()方法；
     * 
     * @param tr
     * @return
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) return "";
        
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            tr.printStackTrace(pw);
            String error = sw.toString();
            sw.close();
            pw.close();
            return error;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
