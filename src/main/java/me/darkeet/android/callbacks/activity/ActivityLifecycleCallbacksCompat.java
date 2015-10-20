
package me.darkeet.android.callbacks.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Name: ActivityLifecycleCallbacksCompat
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/16 14:54
 */
public interface ActivityLifecycleCallbacksCompat {

    void onActivityCreated(Activity activity, Bundle savedInstanceState);

    void onActivityStarted(Activity activity);

    void onActivityResumed(Activity activity);

    void onActivityPaused(Activity activity);

    void onActivityStopped(Activity activity);

    void onActivitySaveInstanceState(Activity activity, Bundle outState);

    void onActivityDestroyed(Activity activity);
}
