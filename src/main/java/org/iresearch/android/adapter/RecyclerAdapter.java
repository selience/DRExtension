package org.iresearch.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView适配器基类
 * <p/>
 * Created by yi on 2015/7/21.
 */
public abstract class RecyclerAdapter<Model, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    private List<Model> mDataList;
    protected LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerAdapter(Context context) {
        this.mContext = context;
        this.mDataList = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
    }

    public void clearList() {
        mDataList.clear();
    }

    public boolean isEmpty() {
        if (mDataList != null) {
            return mDataList.isEmpty();
        }
        return false;
    }

    public List<Model> getDataList() {
        return mDataList;
    }

    public void addDataList(List<Model> dataList) {
        if (dataList != null) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void setDataList(List<Model> dataList) {
        if (dataList != null) {
            mDataList.clear();
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public boolean remove(Model model) {
        return mDataList.remove(model);
    }

    public Model remove(int location) {
        return mDataList.remove(location);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    protected void bindItemViewClickListener(final VH holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, holder.getPosition());
                }
            }
        });
    }


    public interface OnItemClickListener {

        /**
         * RecyclerView中Item点击事件
         *
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);
    }
}
