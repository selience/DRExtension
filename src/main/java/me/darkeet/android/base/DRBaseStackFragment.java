package me.darkeet.android.base;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import me.darkeet.android.log.DebugLog;

/**
 * Name: DRBaseStackFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: Fragment基类，增加对Fragment栈管理
 */
public abstract class DRBaseStackFragment extends DRBaseFragment {
    private static final String FRAGMENT_TITLE = "title";

    private CharSequence mTitle;

    /**
     * 设置界面标题
     *
     * @param resId 标题资源
     */
    public void setTitle(int resId) {
        setTitle(getText(resId));
    }

    /**
     * 设置Activity标题
     *
     * @param title 标题字符串
     */
    public void setTitle(CharSequence title) {
        this.mTitle = title;
        final Activity activity = getActivity();
        if (activity != null) activity.setTitle(title);
    }

    /**
     * 存储Fragment设置标题
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(FRAGMENT_TITLE, mTitle);
    }

    /**
     * 恢复Fragment设置标题
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            setTitle(savedInstanceState.getCharSequence(FRAGMENT_TITLE));
        }
    }

    /**
     * 装载Fragment到指定视图
     *
     * @param clazz Fragment描述类
     * @param tag   Fragment唯一标识，根据TAG可获取指定的Fragment
     * @param args  需要传递的参数，通过getArguments()获取参数值
     */
    public void replace(Class<? extends DRBaseStackFragment> clazz, String tag, Bundle args) {
        final Activity activity = getActivity();
        if (activity == null) return;
        if (activity instanceof DRBaseFragmentActivity) {
            ((DRBaseFragmentActivity) activity).replace(clazz, tag, args);
        }
    }

    /**
     * 弹出栈顶Fragment
     */
    public void popFragment() {
        final Activity activity = getActivity();
        if (activity == null) return;
        if (activity instanceof DRBaseFragmentActivity) {
            ((DRBaseFragmentActivity) activity).popFragment();
        }
    }


    public AppCompatActivity getAppCompatActivity() {
        final Activity activity = getActivity();
        if (activity == null) return null;
        if (activity instanceof AppCompatActivity) {
            return (AppCompatActivity) activity;
        }
        return null;
    }

    public void invalidateOptionsMenu() {
        final AppCompatActivity activity = getAppCompatActivity();
        if (activity != null) activity.supportInvalidateOptionsMenu();
    }

    public ActionBar getActionBar() {
        final AppCompatActivity activity = getAppCompatActivity();
        if (activity != null) return activity.getSupportActionBar();
        return null;
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        DebugLog.d(TAG, "startActionMode");
        final AppCompatActivity activity = getAppCompatActivity();
        if (activity != null) return activity.startSupportActionMode(callback);
        return null;
    }

    public void onActionModeStarted(ActionMode mode) {
        DebugLog.d(TAG, "onActionModeStarted");
    }


    public void onActionModeFinished(ActionMode mode) {
        DebugLog.d(TAG, "onActionModeFinished");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments == null) return;
        for (Fragment fragment : fragments) {
            if (fragment == null) continue;
            fragment.onActivityResult(requestCode, resultCode, data);
        }
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
