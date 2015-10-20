package me.darkeet.android.base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import me.darkeet.android.log.DebugLog;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import me.darkeet.android.callbacks.FragmentLifecycleDispatcher;

/**
 * Name: DRBaseFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: Fragment基类
 */
public class DRBaseFragment extends Fragment {
    protected Activity mActivity;
    protected String TAG = "BaseFragment";

    /**
     * 必须有此构造方法，Layout中的静态Fragment会调用,旋转屏幕也会调用 ;
     */
    public DRBaseFragment() {
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onAttach(Activity activity) {
        DebugLog.d(TAG, "onAttach");
        super.onAttach(activity);
        this.mActivity = activity;
        FragmentLifecycleDispatcher.get().onFragmentAttached(this, activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DebugLog.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        FragmentLifecycleDispatcher.get().onFragmentCreated(this, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DebugLog.d(TAG, "onCreateView");
        FragmentLifecycleDispatcher.get().onFragmentCreateView(this, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        DebugLog.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        FragmentLifecycleDispatcher.get().onFragmentViewCreated(this, view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        DebugLog.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        FragmentLifecycleDispatcher.get().onFragmentActivityCreated(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        DebugLog.d(TAG, "onStart");
        super.onStart();
        FragmentLifecycleDispatcher.get().onFragmentStarted(this);
    }

    @Override
    public void onResume() {
        DebugLog.d(TAG, "onResume");
        super.onResume();
        FragmentLifecycleDispatcher.get().onFragmentResumed(this);
    }

    @Override
    public void onPause() {
        DebugLog.d(TAG, "onPause");
        super.onPause();
        FragmentLifecycleDispatcher.get().onFragmentPaused(this);
    }

    @Override
    public void onStop() {
        DebugLog.d(TAG, "onStop");
        super.onStop();
        FragmentLifecycleDispatcher.get().onFragmentStopped(this);
    }

    @Override
    public void onDestroyView() {
        DebugLog.d(TAG, "onDestroyView");
        super.onDestroyView();
        FragmentLifecycleDispatcher.get().onFragmentDestroyView(this);
    }

    @Override
    public void onDestroy() {
        DebugLog.d(TAG, "onDestroy");
        super.onDestroy();
        FragmentLifecycleDispatcher.get().onFragmentDestroyed(this);
    }

    @Override
    public void onDetach() {
        DebugLog.d(TAG, "onDetach");
        mActivity = null;
        super.onDetach();
        FragmentLifecycleDispatcher.get().onFragmentDetached(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        DebugLog.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        FragmentLifecycleDispatcher.get().onFragmentSaveInstanceState(this, outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        DebugLog.d(TAG, "setUserVisibleHint:" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
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
