
package me.darkeet.android.base;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.view.ActionMode;
import me.darkeet.android.app.StackManager;
import me.darkeet.android.log.DebugLog;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null) return;
        for (Fragment fragment : fragments) {
            if (fragment == null) continue;
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        DebugLog.d(TAG, "onSupportActionModeStarted");
        if (mStackManager != null) {
            DRBaseStackFragment f = mStackManager.peekFragment();
            if (f != null) f.onActionModeStarted(mode);
        }
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        DebugLog.d(TAG, "onSupportActionModeFinished");
        if (mStackManager != null) {
            DRBaseStackFragment f = mStackManager.peekFragment();
            if (f != null) f.onActionModeFinished(mode);
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        DebugLog.d(TAG, "getSupportParentActivityIntent");
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
        DebugLog.d(TAG, "onCreateSupportNavigateUpTaskStack");
    }

    @Override
    public final boolean onSupportNavigateUp() {
        DebugLog.d(TAG, "onSupportNavigateUp");
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
        DebugLog.d(TAG, "onBackPressed");
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
