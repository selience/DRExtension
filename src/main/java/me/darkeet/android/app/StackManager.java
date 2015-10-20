package me.darkeet.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.util.Iterator;
import java.util.Stack;
import me.darkeet.android.base.DRBaseFragment;
import me.darkeet.android.base.DRBaseStackFragment;

/**
 * @author Jacky.Lee <loveselience@gmail.com>
 * @file StackManager.java
 * @create 2012-9-20 下午6:04:05
 * @description Fragment运行状态处理类
 */
public final class StackManager {
    private static final String STACK_TAG = "stack";
    private static final byte[] lock = new byte[1];

    private final int mResId;
    private final Stack<DRBaseStackFragment> mStack;
    private final FragmentManager mFragmentManager;
    private final FragmentActivity mFragmentActivity;
    private FragmentTransaction mTransaction;

    /**
     * 创建StackManager实例，当前实例用于管理Fragment运行的状态；
     *
     * @param activity Activity实例
     * @param fm       Fragment管理器
     * @param layoutId 挂载Fragment布局资源id
     */
    public static StackManager newInstance(FragmentActivity activity, FragmentManager fm, int layoutId) {
        return new StackManager(activity, fm, layoutId);
    }

    private StackManager(FragmentActivity activity, FragmentManager fm, int layoutId) {
        this.mResId = layoutId;
        this.mStack = new Stack<>();
        this.mFragmentManager = fm;
        this.mFragmentActivity = activity;
    }

    /**
     * 装载Fragment到指定界面
     *
     * @param clazz Fragment描述类
     * @param tag   Fragment唯一标识，根据TAG可获取指定的Fragment
     * @param args  需要传递的参数，通过getArguments()获取参数值
     */
    public void replace(Class<? extends DRBaseStackFragment> clazz, String tag, Bundle args) {
        DRBaseStackFragment f = (DRBaseStackFragment) mFragmentManager.findFragmentByTag(tag);
        if (!resetFragment(f, tag)) {
            f = getFragment(clazz, tag, args);
            // 清空栈顶Fragment
            cleanBackStack(f);
            // 挂载当前Fragment
            attachFragment(f, tag);
            // 提交Fragment事务
            commitTransactions();
            // 添加Fragment到栈
            addFragment(f);
        }
    }

    /**
     * 获取Fragment实例
     */
    private DRBaseStackFragment getFragment(Class<? extends DRBaseFragment> clazz, String tag, Bundle args) {
        DRBaseStackFragment f = (DRBaseStackFragment) mFragmentManager.findFragmentByTag(tag);
        if (f == null || !f.isSingleton()) {
            f = (DRBaseStackFragment) Fragment.instantiate(mFragmentActivity, clazz.getName(), args);
        }
        return f;
    }

    /**
     * 处理栈顶Fragment
     */
    private boolean resetFragment(DRBaseStackFragment f, String tag) {
        if (f != null && f.isSingleton() && mStack.size() > 0) {
            DRBaseFragment element = mStack.peek();
            // 当前Fragment位于栈顶直接返回
            if (tag.equals(element.getTag())) {
                return true;
            }

            // 当前Fragment位于栈底，则弹出上面Fragment
            if (mStack.contains(f)) {
                while (!tag.equals(mStack.peek().getTag())) {
                    synchronized (lock) {
                        mStack.pop();
                        mFragmentManager.popBackStack();
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 挂载Fragment到Activity
     */
    private void attachFragment(Fragment f, String tag) {
        if (f != null) {
            if (f.isDetached()) {
                ensureTransaction();
                mTransaction.attach(f);
                addToBackStack(tag);
            } else if (!f.isAdded()) {
                ensureTransaction();
                mTransaction.replace(mResId, f, tag);
                addToBackStack(tag);
            }
        }
    }

    /**
     * 添加FragmentManager后退栈
     */
    private void addToBackStack(String tag) {
        if (mStack.size() > 0) {
            mTransaction.addToBackStack(tag);
        }
    }

    /**
     * 清空栈顶Fragment
     */
    private void cleanBackStack(DRBaseStackFragment f) {
        if (f.isCleanStack()) {
            while (mStack.size() > 0) {
                synchronized (lock) {
                    mStack.pop();
                    mFragmentManager.popBackStack();
                }
            }
        }
    }

    /**
     * 移除Fragment
     */
    protected void detachFragment(Fragment f) {
        if (f != null && !f.isDetached()) {
            ensureTransaction();
            mTransaction.detach(f);
        }
    }

    /**
     * 提交Fragment事务
     */
    protected void commitTransactions() {
        if (mTransaction != null && !mTransaction.isEmpty()) {
            mTransaction.commitAllowingStateLoss();
            mTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    /**
     * 初始化Fragment事务
     */
    protected FragmentTransaction ensureTransaction() {
        if (mTransaction == null) {
            mTransaction = mFragmentManager.beginTransaction();
        }

        return mTransaction;
    }

    /**
     * 存储Fragment状态
     */
    public void savedState(Bundle outState) {
        int index = 0;
        String[] stackTags = new String[mStack.size()];
        Iterator<DRBaseStackFragment> iterator = mStack.iterator();
        while (iterator.hasNext()) {
            stackTags[index++] = iterator.next().getTag();
        }
        outState.putStringArray(STACK_TAG, stackTags);
    }

    /**
     * 恢复Fragment状态
     */
    public void restoreState(Bundle outState) {
        if (outState != null) {
            String[] stackTags = outState.getStringArray(STACK_TAG);
            for (String tag : stackTags) {
                mStack.add((DRBaseStackFragment) mFragmentManager.findFragmentByTag(tag));
            }
        }
    }

    /**
     * 获取栈顶Fragment个数
     */
    public int stackSize() {
        synchronized (lock) {
            return mStack.size();
        }
    }

    /**
     * 添加Fragment到栈顶
     */
    private void addFragment(DRBaseStackFragment f) {
        synchronized (lock) {
            mStack.add(f);
        }
    }

    /**
     * 获取栈顶Fragment
     */
    public DRBaseStackFragment peekFragment() {
        synchronized (lock) {
            return mStack.peek();
        }
    }

    /**
     * 弹出栈顶Fragment
     */
    public boolean popFragment() {
        if (mStack.size() > 1) {
            synchronized (lock) {
                mStack.pop();
                mFragmentManager.popBackStackImmediate();
            }
            return true;
        }
        return false;
    }
}
