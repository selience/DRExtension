
package me.darkeet.android.callbacks;

import android.os.Build;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import me.darkeet.android.callbacks.fragment.FragmentLifecycleCallbacks;
import me.darkeet.android.callbacks.activity.ActivityLifecycleCallbacksCompat;
import me.darkeet.android.callbacks.activity.ActivityLifecycleCallbacksWrapper;

/**
 * Name: ApplicationHelper
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 用于监听应用中所有Activity和Fragment生命运行周期情况
 */
public class LifecycleDispatcherHelper {

    public static final boolean PRE_ICS = Integer.valueOf(Build.VERSION.SDK_INT) < Build.VERSION_CODES.ICE_CREAM_SANDWICH;

    /**
     * Registers a callback to be called following the life cycle of the
     * application's {@link Activity activities}.
     *
     * @param application The application with which to register the callback.
     * @param callback    The callback to register.
     */
    public static void registerActivityLifecycleCallbacks(Application application,
                                                          ActivityLifecycleCallbacksCompat callback) {
        if (PRE_ICS) {
            preIcsRegisterActivityLifecycleCallbacks(callback);
        } else {
            postIcsRegisterActivityLifecycleCallbacks(application, callback);
        }
    }

    private static void preIcsRegisterActivityLifecycleCallbacks(
            ActivityLifecycleCallbacksCompat callback) {
        ActivityLifecycleDispatcher.get().registerActivityLifecycleCallbacks(callback);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void postIcsRegisterActivityLifecycleCallbacks(Application application,
                                                                  ActivityLifecycleCallbacksCompat callback) {
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksWrapper(callback));
    }

    /**
     * Unregisters a previously registered callback.
     *
     * @param application The application with which to unregister the callback.
     * @param callback    The callback to unregister.
     */
    public static void unregisterActivityLifecycleCallbacks(Application application,
                                                            ActivityLifecycleCallbacksCompat callback) {
        if (PRE_ICS) {
            preIcsUnregisterActivityLifecycleCallbacks(callback);
        } else {
            postIcsUnregisterActivityLifecycleCallbacks(application, callback);
        }
    }

    private static void preIcsUnregisterActivityLifecycleCallbacks(
            ActivityLifecycleCallbacksCompat callback) {
        ActivityLifecycleDispatcher.get().unregisterActivityLifecycleCallbacks(callback);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void postIcsUnregisterActivityLifecycleCallbacks(Application application,
                                                                    ActivityLifecycleCallbacksCompat callback) {
        application.unregisterActivityLifecycleCallbacks(new ActivityLifecycleCallbacksWrapper(callback));
    }


    /**
     * Registers a callback to be called following the life cycle of the fragments.
     *
     * @param application The application with which to register the callback.
     * @param callback    The callback to register.
     */
    public static void registerFragmentLifecycleCallbacks(Application application,
                                                          FragmentLifecycleCallbacks callback) {
        FragmentLifecycleDispatcher.get().registerFragmentLifecycleCallbacks(callback);
    }

    /**
     * Unregisters a previously registered callback.
     *
     * @param application The application with which to unregister the callback.
     * @param callback    The callback to unregister.
     */
    public static void unregisterFragmentLifecycleCallbacks(Application application,
                                                            FragmentLifecycleCallbacks callback) {
        FragmentLifecycleDispatcher.get().unregisterFragmentLifecycleCallbacks(callback);
    }

}
