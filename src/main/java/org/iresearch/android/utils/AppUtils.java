
package org.iresearch.android.utils;

import android.os.Build;
import android.view.Menu;
import android.view.View;
import android.app.Activity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.content.Context;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.view.ViewConfiguration;
import android.support.v7.widget.PopupMenu;

public final class AppUtils {

    private AppUtils() {
    }

    
    /**
     * 在有 Menu按键的手机上面，ActionBar上的 overflow Menu默认不会出现，只有当点击了 Menu按键时才会显示
     */
    public static void forceShowActionBarOverflowMenu(Context context) {
        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ignored) { }
    }
    
    /**
     * 控制Overflow Menu菜单中icon的显示，默认溢出菜单只显示文字部分，通过反射控制图片显示；
     * @param menu
     */
    public static void showActionMenuIcon(Menu menu) {  
        try {  
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");  
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);  
            m.setAccessible(true);                
            m.invoke(menu, true);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    /**
     * 控制PopupMenu菜单显示图标，默认情况PopupMenu菜单只显示文字，只能通过反射方式控制图片显示；
     */
    public static void showPopupMenuIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Workaround for this issue: https://code.google.com/p/android/issues/detail?id=58280
     * See also: http://stackoverflow.com/questions/17945785/what-happened-to-windowcontentoverlay-in-android-api-18
     */
    public static void fixWindowContentOverlay(Activity activity) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // Get the content view
            View contentView = activity.findViewById(android.R.id.content);
            // Make sure it's a valid instance of a FrameLayout
            if (contentView instanceof FrameLayout) {
                TypedValue tv = new TypedValue();
                
                // Get the windowContentOverlay value of the current theme
                if (activity.getTheme().resolveAttribute(android.R.attr.windowContentOverlay, tv, true)) {
                    // If it's a valid resource, set it as the foreground drawable for the content view
                    if (tv.resourceId != 0) {
                        ((FrameLayout) contentView).setForeground(activity.getResources().getDrawable(tv.resourceId));
                    }
                }
            }
        }
    }
}
