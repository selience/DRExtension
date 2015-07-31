package org.iresearch.android.view;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	private boolean hasMore;
	private boolean isLoading;
	private int currentPage = 1;
	private int totalItemCount;
	private int lastVisibleItem;

	private View mLoadMoreView;
	private OnScrollListener mOnScrollListener;

	public EndlessScrollListener(View loadView) {
		this.mLoadMoreView = loadView;
	}

	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.mOnScrollListener = onScrollListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (lastVisibleItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {
			if (!isLoading && hasMore) {
				isLoading = true;
				mLoadMoreView.setVisibility(View.VISIBLE);
				loadMore(currentPage++, totalItemCount);
			}
		}

		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.totalItemCount = totalItemCount;
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;

		if (mOnScrollListener != null) {
			mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public void loadFinished() {
		isLoading = false;
		mLoadMoreView.setVisibility(View.GONE);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public abstract void loadMore(int page, int totalItemsCount);
}
