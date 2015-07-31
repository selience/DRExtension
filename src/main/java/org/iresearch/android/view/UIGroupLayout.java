
package org.iresearch.android.view;

import java.util.List;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class UIGroupLayout extends RelativeLayout {
	protected int itemSpace;
    protected int itemWidth;
    protected int itemHeight;
    protected int itemSize = 4;

    public UIGroupLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        itemWidth = (getMeasuredWidth() - (itemSize - 1) * itemSpace) / itemSize;
        updateItemLayoutParams();
    }

    public <T> void setChildItems(List<T> items) {
        if (items == null || items.isEmpty()) {
            setVisibility(View.GONE);
            return;
        }

        removeAllViews();
        LayoutParams params = null;
        for (int i = 0; i < items.size(); i++) {
            View childView = createView(items.get(i));
            if (childView == null)
                continue;
            params = new LayoutParams(itemWidth, itemHeight);
            params.leftMargin = (itemWidth + itemSpace) * (i % itemSize);
            params.topMargin = (itemSpace + itemHeight) * (i / itemSize);
            addView(childView, params);
        }
    }

    private void updateItemLayoutParams() {
        LayoutParams params = null;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            params = (LayoutParams) view.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(itemWidth, itemHeight);
            }
            params.width = itemWidth;
            params.leftMargin = (itemWidth + itemSpace) * (i % itemSize);
            params.topMargin = (itemHeight + itemSpace) * (i / itemSize);
            view.setLayoutParams(params);
        }
    }

    protected View createView(Object subItem) {
        return null;
    }
}
