package me.darkeet.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: BaseRecyclerAdapter
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: RecyclerView适配器基类
 */
public abstract class BaseRecyclerAdapter<Model, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<Model> mDataList;
    private LayoutInflater mInflater;

    public BaseRecyclerAdapter(Context context) {
        this.mDataList = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
    }

    public void addDataList(Model item) {
        if (item != null) {
            mDataList.add(item);
        }
    }

    public void addDataList(int location, Model item) {
        if (item != null) {
            mDataList.add(location, item);
        }
    }

    public void addDataList(List<Model> items) {
        if (items != null) {
            mDataList.addAll(items);
        }
    }

    public void setDataList(List<Model> items) {
        if (items != null) {
            mDataList.clear();
            mDataList.addAll(items);
        }
    }

    public void removeItem(Model item) {
        if (item != null) {
            mDataList.remove(item);
        }
    }

    public void removeItem(int location) {
        if (location >= 0 && location < mDataList.size()) {
            mDataList.remove(location);
        }
    }

    public void clearAll() {
        mDataList.clear();
    }

    public List<Model> getDataList() {
        return mDataList;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH viewHolder = onCreateViewHolder(mInflater, parent, viewType);
        bindItemViewClickListener(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder.itemView, holder, position, getItemViewType(position));
    }

    public abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);


    public abstract void onBindViewHolder(View convertView, VH viewHolder, int position, int viewType);


    public void onListItemClick(View view, VH viewHolder) {
    }


    private void bindItemViewClickListener(final VH viewHolder) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onListItemClick(view, viewHolder);
            }
        });
    }
}
