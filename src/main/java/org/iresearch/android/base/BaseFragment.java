package org.iresearch.android.base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.iresearch.android.log.DebugLog;

/**
 * @author Jacky.Lee <loveselience@gmail.com>
 * @file BaseFragment.java
 * @create 2012-8-20 上午11:23:16
 * @description Fragment基类，对Fragment栈的管理
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected String TAG = "BaseFragment";

    /**
     * 必须有此构造方法，Layout中的静态Fragment会调用,旋转屏幕也会调用 ;
     */
    public BaseFragment() {
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        DebugLog.d(TAG, "onInflate");
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        DebugLog.d(TAG, "onAttach");
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DebugLog.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DebugLog.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DebugLog.d(TAG, "onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DebugLog.d(TAG, "onActivityCreated");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        DebugLog.d(TAG, "setUserVisibleHint:" + isVisibleToUser);
    }

    @Override
    public void onStart() {
        super.onStart();
        DebugLog.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugLog.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        DebugLog.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        DebugLog.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DebugLog.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        DebugLog.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        DebugLog.d(TAG, "onDetach");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        DebugLog.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }


    public Application getApplication() {
        final Activity activity = getActivity();
        if (activity != null) return activity.getApplication();
        return null;
    }

    public ContentResolver getContentResolver() {
        final Activity activity = getActivity();
        if (activity != null) return activity.getContentResolver();
        return null;
    }

    public SharedPreferences getSharedPreferences(final String name, final int mode) {
        final Activity activity = getActivity();
        if (activity != null) return activity.getSharedPreferences(name, mode);
        return null;
    }

    public Object getSystemService(final String name) {
        final Activity activity = getActivity();
        if (activity != null) return activity.getSystemService(name);
        return null;
    }

    public void registerReceiver(final BroadcastReceiver receiver, final IntentFilter filter) {
        final Activity activity = getActivity();
        if (activity == null) return;
        LocalBroadcastManager.getInstance(activity).registerReceiver(receiver, filter);
    }

    public void unregisterReceiver(final BroadcastReceiver receiver) {
        final Activity activity = getActivity();
        if (activity == null) return;
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(receiver);
    }

    public void startService(Intent service) {
        final Activity activity = getActivity();
        if (activity == null) return;
        activity.startService(service);
    }

    public void stopService(Intent name) {
        final Activity activity = getActivity();
        if (activity == null) return;
        activity.stopService(name);
    }

    public void setProgressBarIndeterminateVisibility(final boolean visible) {
        final Activity activity = getActivity();
        if (activity == null) return;
        activity.setProgressBarIndeterminateVisibility(visible);
    }
}
