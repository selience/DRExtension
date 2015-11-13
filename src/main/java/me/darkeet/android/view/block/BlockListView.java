package me.darkeet.android.view.block;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Name: BlockListView
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/4 19:16
 * Desc:
 */
public class BlockListView extends FrameLayout {

    private int mWidthSpace = 0;
    private int mHeightSpace = 0;

    private LayoutInflater mLnflater;
    private BlockListAdapter mBlockListAdapter;
    private OnItemClickListener mOnItemClickListener;


    public BlockListView(Context context) {
        this(context, null);
    }

    public BlockListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlockListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(BlockListAdapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("adapter should not be null");
        }
        mBlockListAdapter = adapter;
        //adapter.registerView(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (null != mBlockListAdapter) {
            //mBlockListAdapter.registerView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mBlockListAdapter) {
            //mBlockListAdapter.registerView(null);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View v, int position);
    }
}
