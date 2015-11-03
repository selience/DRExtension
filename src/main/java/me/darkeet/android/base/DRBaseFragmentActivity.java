
package me.darkeet.android.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.ActionMode;
import me.darkeet.android.app.StackManager;

/**
 * Name: DRBaseStackActivity
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 基于栈管理Activity基类
 */
public class DRBaseFragmentActivity extends DRBaseActivity {

    private StackManager mStackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mStackManager != null) {
            mStackManager.restoreState(savedInstanceState);
        }
    }

    protected final void setupFragmentContent(int containerId) {
        if (mStackManager == null) {
            FragmentManager fm = getSupportFragmentManager();
            mStackManager = StackManager.newInstance(this, fm, containerId);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mStackManager != null) {
            mStackManager.savedState(outState);
        }
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        if (mStackManager != null) {
            DRBaseStackFragment f = mStackManager.peekFragment();
            if (f != null) f.onActionModeStarted(mode);
        }
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        if (mStackManager != null) {
            DRBaseStackFragment f = mStackManager.peekFragment();
            if (f != null) f.onActionModeFinished(mode);
        }
    }

    @Override
    public final boolean onSupportNavigateUp() {
        if (mStackManager != null && mStackManager.stackSize() > 1) {
            if (mStackManager.peekFragment().onNavigateUp()) {
                return true;
            } else {
                mStackManager.popFragment();
                return true;
            }
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (mStackManager != null && mStackManager.stackSize() > 1) {
            if (mStackManager.peekFragment().onBackPressed()) {
                return;
            } else {
                mStackManager.popFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    /**
     * 弹出栈顶Fragment
     */
    public void popFragment() {
        if (mStackManager != null) {
            mStackManager.popFragment();
        }
    }

    /**
     * 装载Fragment到指定视图
     *
     * @param clazz Fragment描述类
     * @param tag   Fragment唯一标识，根据tag可获取指定的Fragment
     * @param args  需要传递的参数，通过getArguments()获取参数值
     */
    public void replace(Class<? extends DRBaseStackFragment> clazz, String tag, Bundle args) {
        if (mStackManager != null) {
            mStackManager.replace(clazz, tag, args);
        }
    }

    /**
     * 装载Fragment到指定视图
     *
     * @param containerViewId Fragment需要挂载容器标识id
     * @param clazz           Fragment描述类
     * @param tag             Fragment唯一标识，根据tag可获取指定的Fragment
     * @param args            需要传递的参数，通过getArguments()获取参数值
     */
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
