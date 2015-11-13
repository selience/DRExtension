package me.darkeet.android.view.empty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Name: EmptyViewLayout
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/11 16:48
 * Desc: View数据为空，提供加载重试操作；
 */
public class EmptyViewLayout extends FrameLayout {

    public final static int TYPE_EMPTY = 1;
    public final static int TYPE_LOADING = 2;
    public final static int TYPE_ERROR = 3;
    public final static int TYPE_COMPLETE = 4;

    private int mEmptyType = TYPE_LOADING;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mContentView;

    public EmptyViewLayout(Context context) {
        this(context, null);
    }

    public EmptyViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void changeViewState() {
        switch (mEmptyType) {
            case TYPE_EMPTY:
                this.setVisibility(View.VISIBLE);
                mErrorView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mContentView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
                break;
            case TYPE_ERROR:
                this.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mContentView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                break;
            case TYPE_LOADING:
                this.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                mContentView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.VISIBLE);
                break;
            case TYPE_COMPLETE:
                this.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mContentView.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setEmptyType(int emptyType) {
        this.mEmptyType = emptyType;
        changeViewState();
    }

    public void showEmpty() {
        this.mEmptyType = TYPE_EMPTY;
        changeViewState();
    }

    public void showLoading() {
        this.mEmptyType = TYPE_LOADING;
        changeViewState();
    }

    public void showError() {
        this.mEmptyType = TYPE_ERROR;
        changeViewState();
    }

    public void showContent() {
        this.mEmptyType = TYPE_COMPLETE;
        changeViewState();
    }

    public void setLoadingView(View mLoadingView) {
        if (this.mLoadingView != null) {
            removeView(this.mLoadingView);
        }
        addView(mLoadingView);
        this.mLoadingView = mLoadingView;
    }

    public void setEmptyView(View mEmptyView) {
        if (this.mEmptyView != null) {
            removeView(this.mEmptyView);
        }
        addView(mEmptyView);
        this.mEmptyView = mEmptyView;
    }

    public void setErrorView(View mErrorView) {
        if (this.mErrorView != null) {
            removeView(this.mErrorView);
        }
        addView(mErrorView);
        this.mErrorView = mErrorView;
    }

    public void setContentView(View mContentView) {
        this.mContentView = mContentView;
    }
}
