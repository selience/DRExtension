package me.darkeet.android.view.empty;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * Name: EmptyView
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/11 16:38
 * Desc:
 */
public class EmptyViewManager {

    private EmptyViewLayout mEmptyViewLayout;

    public EmptyViewManager() {
    }

    public void bindActivity(Activity activity, EmptyViewListener listener) {
        ViewGroup parentView = (ViewGroup) activity.findViewById(android.R.id.content);
        bindView(parentView.getChildAt(0), listener);
    }

    public void bindFragment(Fragment fragment, EmptyViewListener listener) {
        ViewGroup parentView = (ViewGroup) fragment.getView();
        bindView(parentView.getChildAt(0), listener);
    }


    public void bindView(View view, EmptyViewListener listener) {
        ViewGroup containerView = (ViewGroup) view.getParent();

        int index = 0;
        for (int i = 0; i < containerView.getChildCount(); i++) {
            if (containerView.getChildAt(i) == view) {
                index = i;
                break;
            }
        }

        mEmptyViewLayout = new EmptyViewLayout(view.getContext());
        mEmptyViewLayout.setContentView(view);
        containerView.addView(mEmptyViewLayout, index, view.getLayoutParams());

        mEmptyViewLayout.setEmptyView(listener.generateEmptyView(mEmptyViewLayout));
        mEmptyViewLayout.setErrorView(listener.generateErrorView(mEmptyViewLayout));
        mEmptyViewLayout.setLoadingView(listener.generateLoadingView(mEmptyViewLayout));
    }

    public void showEmpty() {
        mEmptyViewLayout.showEmpty();
    }

    public void showLoading() {
        mEmptyViewLayout.showLoading();
    }

    public void showError() {
        mEmptyViewLayout.showError();
    }

    public void showContent() {
        mEmptyViewLayout.showContent();
    }

    public void setEmptyType(int emptyType) {
        mEmptyViewLayout.setEmptyType(emptyType);
    }
}
