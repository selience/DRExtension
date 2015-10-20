
package me.darkeet.android.callbacks;

import android.os.Bundle;
import java.util.ArrayList;
import android.app.Activity;

import me.darkeet.android.callbacks.activity.ActivityLifecycleCallbacksCompat;

public class ActivityLifecycleDispatcher implements ActivityLifecycleCallbacksCompat {
    private static final ActivityLifecycleDispatcher INSTANCE = new ActivityLifecycleDispatcher();

    public static ActivityLifecycleDispatcher get() {
        return INSTANCE;
    }

    private ActivityLifecycleDispatcher() {
    }

    private ArrayList<ActivityLifecycleCallbacksCompat> mActivityLifecycleCallbacks = new ArrayList<ActivityLifecycleCallbacksCompat>();

    /* package */void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback) {
        synchronized (mActivityLifecycleCallbacks) {
            mActivityLifecycleCallbacks.add(callback);
        }
    }

    /* package */void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat callback) {
        synchronized (mActivityLifecycleCallbacks) {
            mActivityLifecycleCallbacks.remove(callback);
        }
    }

    private Object[] collectActivityLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (mActivityLifecycleCallbacks) {
            if (mActivityLifecycleCallbacks.size() > 0) {
                callbacks = mActivityLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (!LifecycleDispatcherHelper.PRE_ICS) return;
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((ActivityLifecycleCallbacksCompat) callback).onActivityCreated(activity, savedInstanceState);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!LifecycleDispatcherHelper.PRE_ICS) return;
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((ActivityLifecycleCallbacksCompat) callback).onActivityStarted(activity);
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!LifecycleDispatcherHelper.PRE_ICS) return;
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((ActivityLifecycleCallbacksCompat) callback).onActivityResumed(activity);
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (!LifecycleDispatcherHelper.PRE_ICS) return;
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((ActivityLifecycleCallbacksCompat) callback).onActivityPaused(activity);
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (!LifecycleDispatcherHelper.PRE_ICS) return;
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((ActivityLifecycleCallbacksCompat) callback).onActivityStopped(activity);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (!LifecycleDispatcherHelper.PRE_ICS) return;
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((ActivityLifecycleCallbacksCompat) callback).onActivitySaveInstanceState(activity, outState);
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (!LifecycleDispatcherHelper.PRE_ICS) return;
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((ActivityLifecycleCallbacksCompat) callback).onActivityDestroyed(activity);
            }
        }
    }
}
