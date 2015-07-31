package org.iresearch.android.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.text.TextUtils;

import org.iresearch.android.log.DebugLog;

/**
 * @author Jacky.Lee <loveselience@gmail.com>
 * @file XBaseFragment.java
 * @create 2012-8-20 上午11:23:16
 * @description Fragment基类，对Fragment栈的管理
 */
public abstract class XBaseFragment extends BaseFragment {
    private static final String STATE_TITLE = "_title";

    // Fragment显示标题
    private String mTitle;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            setTitle(savedInstanceState.getString(STATE_TITLE));
        }
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, mTitle);
    }

    /**
     * 设置Fragment标题
     *
     * @param textId 标题资源id
     */
    public void setTitle(int textId) {
        setTitle(getString(textId));
    }

    /**
     * 设置Fragment标题
     *
     * @param title 标题名称
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            this.mTitle = title;
            mActivity.setTitle(title);
        }
    }

    /**
     * 弹出栈顶Fragment
     */
    public void popFragment() {
        final Activity activity = getActivity();
        if (activity == null) return;
        if (activity instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) activity).popFragment();
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
        final Activity activity = getActivity();
        if (activity == null) return;
        if (activity instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) activity).replace(clazz, tag, args);
        }
    }


    public ActionBarActivity getActionBarActivity() {
        final Activity activity = getActivity();
        if (activity == null) return null;
        if (activity instanceof ActionBarActivity) {
            return (ActionBarActivity) activity;
        }
        return null;
    }

    public void invalidateOptionsMenu() {
        final ActionBarActivity activity = getActionBarActivity();
        if (activity != null) activity.supportInvalidateOptionsMenu();
    }

    public ActionBar getActionBar() {
        final ActionBarActivity activity = getActionBarActivity();
        if (activity != null) return activity.getSupportActionBar();
        return null;
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        DebugLog.d(TAG, "startActionMode");
        final ActionBarActivity activity = getActionBarActivity();
        if (activity != null) return activity.startSupportActionMode(callback);
        return null;
    }

    public void onActionModeStarted(ActionMode mode) {
        DebugLog.d(TAG, "onActionModeStarted");
    }


    public void onActionModeFinished(ActionMode mode) {
        DebugLog.d(TAG, "onActionModeFinished");
    }


    /**
     * 是否为单例模式
     *
     * @return true 单例；false 非单例
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * 是否清空后退栈
     *
     * @return true 清空；false 不清空
     */
    public boolean isCleanStack() {
        return false;
    }

    /**
     * 子类back键处理方法，如需特殊处理，请覆写该方法
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * 监听UP按钮事件
     */
    public boolean onNavigateUp() {
        return false;
    }
}
