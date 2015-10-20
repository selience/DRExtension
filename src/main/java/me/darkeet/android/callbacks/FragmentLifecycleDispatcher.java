package me.darkeet.android.callbacks;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import me.darkeet.android.callbacks.fragment.FragmentLifecycleCallbacks;

/**
 * Name: MainLifecycleDispatcher
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/16 15:05
 * Desc:
 */
public class FragmentLifecycleDispatcher implements FragmentLifecycleCallbacks {
    private static final FragmentLifecycleDispatcher INSTANCE = new FragmentLifecycleDispatcher();

    public static FragmentLifecycleDispatcher get() {
        return INSTANCE;
    }

    private FragmentLifecycleDispatcher() {
    }

    private ArrayList<FragmentLifecycleCallbacks> mFragmentLifecycleCallbacks = new ArrayList<FragmentLifecycleCallbacks>();

    /* package */void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks callback) {
        synchronized (mFragmentLifecycleCallbacks) {
            mFragmentLifecycleCallbacks.add(callback);
        }
    }

    /* package */void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks callback) {
        synchronized (mFragmentLifecycleCallbacks) {
            mFragmentLifecycleCallbacks.remove(callback);
        }
    }

    private Object[] collectFragmentLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (mFragmentLifecycleCallbacks) {
            if (mFragmentLifecycleCallbacks.size() > 0) {
                callbacks = mFragmentLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
    }

    @Override
    public void onFragmentAttached(Fragment fragment, Activity activity) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentAttached(fragment, activity);
            }
        }
    }

    @Override
    public void onFragmentCreated(Fragment fragment, Bundle savedInstanceState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentCreated(fragment, savedInstanceState);
            }
        }
    }

    @Override
    public void onFragmentCreateView(Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentCreateView(fragment, inflater, container, savedInstanceState);
            }
        }
    }

    @Override
    public void onFragmentViewCreated(Fragment fragment, View view, Bundle savedInstanceState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentViewCreated(fragment, view, savedInstanceState);
            }
        }
    }

    @Override
    public void onFragmentActivityCreated(Fragment fragment, Bundle savedInstanceState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentActivityCreated(fragment, savedInstanceState);
            }
        }
    }

    @Override
    public void onFragmentStarted(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentStarted(fragment);
            }
        }
    }

    @Override
    public void onFragmentResumed(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentResumed(fragment);
            }
        }
    }

    @Override
    public void onFragmentPaused(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentPaused(fragment);
            }
        }
    }

    @Override
    public void onFragmentStopped(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentStopped(fragment);
            }
        }
    }

    @Override
    public void onFragmentSaveInstanceState(Fragment fragment, Bundle outState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentSaveInstanceState(fragment, outState);
            }
        }
    }

    @Override
    public void onFragmentDestroyView(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentDestroyView(fragment);
            }
        }
    }

    @Override
    public void onFragmentDestroyed(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentDestroyed(fragment);
            }
        }
    }

    @Override
    public void onFragmentDetached(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (int i = 0; i < callbacks.length; i++) {
                ((FragmentLifecycleCallbacks) callbacks[i]).onFragmentDetached(fragment);
            }
        }
    }
}
