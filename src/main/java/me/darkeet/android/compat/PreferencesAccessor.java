package me.darkeet.android.compat;

import android.os.Build;
import android.annotation.TargetApi;
import android.content.SharedPreferences.Editor;

public class PreferencesAccessor {

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void commit(final Editor editor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }
        editor.commit();
    }
    
}
