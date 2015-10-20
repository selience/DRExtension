package me.darkeet.android.callbacks.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Name: FragmentLifecycleCallbacks
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/16 14:54
 */
public interface FragmentLifecycleCallbacks {

    void onFragmentAttached(Fragment fragment, Activity activity);

    void onFragmentCreated(Fragment fragment, Bundle savedInstanceState);

    void onFragmentCreateView(Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void onFragmentViewCreated(Fragment fragment, View view, Bundle savedInstanceState);

    void onFragmentActivityCreated(Fragment fragment, Bundle savedInstanceState);

    void onFragmentStarted(Fragment fragment);

    void onFragmentResumed(Fragment fragment);

    void onFragmentPaused(Fragment fragment);

    void onFragmentStopped(Fragment fragment);

    void onFragmentSaveInstanceState(Fragment fragment, Bundle outState);

    void onFragmentDestroyView(Fragment fragment);

    void onFragmentDestroyed(Fragment fragment);

    void onFragmentDetached(Fragment fragment);
}
