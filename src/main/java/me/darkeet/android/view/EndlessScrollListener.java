package me.darkeet.android.view;

import android.widget.AbsListView;

/**
 * Name: EndlessScrollListener
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/16 18:10
 * Desc:扩展OnScrollListener，支持滚动加载更多数据；
 */
public class EndlessScrollListener implements AbsListView.OnScrollListener {

    private OnLoadMoreListener mOnLoadMoreListener;

    public EndlessScrollListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnLoadMoreListener != null && scrollState == SCROLL_STATE_IDLE
                && view.getLastVisiblePosition() == (view.getCount() - 1)) {
            // 执行加载更多操作
            if (mOnLoadMoreListener.hasNext(view.getCount())) {
                mOnLoadMoreListener.loadMoreData();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }


    public interface OnLoadMoreListener {

        boolean hasNext(int count);

        void loadMoreData();
    }
}

