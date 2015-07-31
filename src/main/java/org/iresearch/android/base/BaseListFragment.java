package org.iresearch.android.base;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import org.iresearch.android.R;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class BaseListFragment extends BaseFragment implements OnScrollListener {
    final private Handler mHandler = new Handler();

    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mListView.focusableViewAvailable(mListView);
        }
    };
    
    final private AdapterView.OnItemClickListener mOnClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            onListItemClick((ListView)parent, v, position, id);
        }
    };
    
    private boolean mListShown;
    private ListView mListView;
    private TextView mEmptyView;
    private View mListContainer;
    private View mProgressContainer;
    private ListAdapter mAdapter;
    
    private boolean mReachedBottom, mNotReachedBottomBefore = true;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_content, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureList();
    }
    
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ListView lv = getListView();
        lv.setOnScrollListener(this);
    }
    
    private void ensureList() {
        if (mListView != null) return;
        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        
        if (root instanceof ListView) {
            mListView = (ListView)root;
        } else {
            mListContainer = root.findViewById(R.id.listContainer);
            mProgressContainer = root.findViewById(R.id.progressContainer);
            mListView = (ListView) root.findViewById(android.R.id.list);
            mEmptyView = (TextView) root.findViewById(android.R.id.empty);
            mListView.setEmptyView(mEmptyView);
        }
        
        mListShown = true;
        mListView.setOnItemClickListener(mOnClickListener);
        
        if (mAdapter != null) {
            ListAdapter adapter = mAdapter;
            mAdapter = null;
            setListAdapter(adapter);
        } else {
            if (mProgressContainer != null) {
                setListShown(false);
            }
        }
        mHandler.post(mRequestFocus);
    }
    
    @Override
    public void onDestroyView() {
        mListView = null;
        mListShown = false;
        mEmptyView = null;
        mHandler.removeCallbacks(mRequestFocus);
        mProgressContainer = mListContainer = null;
        super.onDestroyView();
    }
    
    public void setSelection(int position) {
        ensureList();
        mListView.setSelection(position);
    }
    
    public int getSelectedItemPosition() {
        ensureList();
        return mListView.getSelectedItemPosition();
    }
    
    public long getSelectedItemId() {
        ensureList();
        return mListView.getSelectedItemId();
    }
    
    public ListView getListView() {
        ensureList();
        return mListView;
    }
    
    public void setEmptyText(CharSequence text) {
        ensureList();
        mEmptyView.setText(text);
    }
    
    public ListAdapter getListAdapter() {
        return mAdapter;
    }
    
    public void onListItemClick(ListView l, View v, int position, long id) {
    }
    
    
    public void setListShown(boolean shown) {
        ensureList();
        if (mProgressContainer == null) {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
        if (mListShown == shown) {
            return;
        }
        mListShown = shown;
        if (shown) {
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            mProgressContainer.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.GONE);
        }
    }
    
    public void setListAdapter(ListAdapter adapter) {
        boolean hadAdapter = mAdapter != null;
        mAdapter = adapter;
        if (mListView != null) {
            mListView.setAdapter(adapter);
            if (!mListShown && !hadAdapter) {
                setListShown(true);
            }
        }
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
            final int totalItemCount) {
        final boolean reached = firstVisibleItem + visibleItemCount >= totalItemCount
                && totalItemCount >= visibleItemCount;

        if (mReachedBottom != reached) {
            mReachedBottom = reached;
            if (mReachedBottom && mNotReachedBottomBefore) {
                mNotReachedBottomBefore = false;
                return;
            }
            if (mReachedBottom && getListAdapter().getCount() > visibleItemCount) {
                onReachedBottom();
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
    
    
    public boolean isReachedBottom() {
        return mReachedBottom;
    }
    
    
    protected void onReachedBottom() {
    }
}
