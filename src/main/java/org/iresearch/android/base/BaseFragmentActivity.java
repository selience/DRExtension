
package org.iresearch.android.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.ActionMode;
import org.iresearch.android.app.StackManager;
import org.iresearch.android.log.DebugLog;

public abstract class BaseFragmentActivity extends BaseActivity {

    private StackManager mStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager.enableDebugLogging(DebugLog.isDebug());

        if (mStack != null) mStack.restoreState(savedInstanceState);
    }

    public final void initStackManager(int containerId) {
        if (mStack == null) {
            FragmentManager fm = getSupportFragmentManager();
            mStack = StackManager.newInstance(this, fm, containerId);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mStack.savedState(outState);
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        if (mStack == null) return;
        XBaseFragment f = mStack.peekFragment();
        if (f != null) f.onActionModeStarted(mode);
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        if (mStack == null) return;
        XBaseFragment f = mStack.peekFragment();
        if (f != null) f.onActionModeFinished(mode);
    }

    @Override
    public final boolean onSupportNavigateUp() {
        DebugLog.d(TAG, "onSupportNavigateUp");
        if (mStack != null && mStack.stackSize() > 1) {
            if (mStack.peekFragment().onNavigateUp()) {
                return true;
            } else {
                mStack.popFragment();
                return true;
            }
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (mStack != null && mStack.stackSize() > 1) {
            if (mStack.peekFragment().onBackPressed()) {
                return;
            } else {
                mStack.popFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    /**
     * 弹出栈顶Fragment
     */
    public void popFragment() {
        if (mStack != null) {
            mStack.popFragment();
        }
    }

    /**
     * 装载Fragment到指定界面
     *
     * @param clazz Fragment描述类
     * @param tag   Fragment唯一标识，根据TAG可获取指定的Fragment
     * @param args  需要传递的参数，通过getArguments()获取参数值
     */
    public void replace(Class<? extends XBaseFragment> clazz, String tag, Bundle args) {
        if (mStack != null) {
            mStack.replace(clazz, tag, args);
        }
    }
}
