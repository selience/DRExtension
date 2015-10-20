package me.darkeet.android.callbacks.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Name: FragmentLifecycleCallbacksAdapter
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/16 15:03
 */
public class FragmentLifecycleCallbacksAdapter implements FragmentLifecycleCallbacks {

    @Override
    public void onFragmentAttached(Fragment fragment, Activity activity) {
    }

    @Override
    public void onFragmentCreated(Fragment fragment, Bundle savedInstanceState) {
    }

    @Override
    public void onFragmentCreateView(Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    }

    @Override
    public void onFragmentViewCreated(Fragment fragment, View view, Bundle savedInstanceState) {
    }

    @Override
    public void onFragmentActivityCreated(Fragment fragment, Bundle savedInstanceState) {
    }

    @Override
    public void onFragmentStarted(Fragment fragment) {
    }

    @Override
    public void onFragmentResumed(Fragment fragment) {
    }

    @Override
    public void onFragmentPaused(Fragment fragment) {
    }

    @Override
    public void onFragmentStopped(Fragment fragment) {
    }

    @Override
    public void onFragmentSaveInstanceState(Fragment fragment, Bundle outState) {
    }

    @Override
    public void onFragmentDestroyView(Fragment fragment) {
    }

    @Override
    public void onFragmentDestroyed(Fragment fragment) {
    }

    @Override
    public void onFragmentDetached(Fragment fragment) {
    }
}
