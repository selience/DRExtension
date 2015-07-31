
package org.iresearch.android.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.iresearch.android.app.compat.MainLifecycleDispatcher;
import org.iresearch.android.log.DebugLog;

/**
 * Activity基类
 *
 * @author Jacky.Lee
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        DebugLog.d(TAG, "onCreate");
        MainLifecycleDispatcher.get().onActivityCreated(this, savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        DebugLog.d(TAG, "onNewIntent");
    }

    @Override
    protected void onStart() {
        super.onStart();
        DebugLog.d(TAG, "onStart");
        MainLifecycleDispatcher.get().onActivityStarted(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DebugLog.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        DebugLog.d(TAG, "onResume");
        MainLifecycleDispatcher.get().onActivityResumed(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DebugLog.d(TAG, "onPause");
        MainLifecycleDispatcher.get().onActivityPaused(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DebugLog.d(TAG, "onStop");
        MainLifecycleDispatcher.get().onActivityStopped(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DebugLog.d(TAG, "onDestroy");
        MainLifecycleDispatcher.get().onActivityDestroyed(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        DebugLog.d(TAG, "onSaveInstanceState");
        MainLifecycleDispatcher.get().onActivitySaveInstanceState(this, outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DebugLog.d(TAG, "onConfigurationChanged");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DebugLog.d(TAG, "onBackPressed");
    }


    public void replaceFragment(int containerViewId, Class<? extends Fragment> clazz,
                                String tag, Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, clazz.getName(), args);
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(containerViewId, fragment, tag);
        transaction.commitAllowingStateLoss();
    }
}
