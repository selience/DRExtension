package me.darkeet.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: BaseListAdapter
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/15 18:10
 * Desc: 封装常用adpater操作
 */
public abstract class BaseListAdapter<Model, T> extends BaseAdapter {
    private List<Model> mDatList;
    private LayoutInflater mInflater;

    public BaseListAdapter(Context context) {
        mDatList = new ArrayList<Model>();
        mInflater = LayoutInflater.from(context);
    }

    public void addDataList(Model item) {
        if (item != null) {
            mDatList.add(item);
        }
    }

    public void addDataList(int location, Model item) {
        if (item != null) {
            mDatList.add(location, item);
        }
    }

    public void addDataList(List<Model> items) {
        if (items != null) {
            mDatList.addAll(items);
        }
    }

    public void setDataList(List<Model> items) {
        if (items != null) {
            mDatList.clear();
            mDatList.addAll(items);
        }
    }

    public void removeItem(Model item) {
        if (item != null) {
            mDatList.remove(item);
        }
    }

    public void removeItem(int location) {
        if (location >= 0 && location < mDatList.size()) {
            mDatList.remove(location);
        }
    }

    public void clearAll() {
        mDatList.clear();
    }

    public List<Model> getDataList() {
        return mDatList;
    }

    @Override
    public int getCount() {
        return mDatList.size();
    }

    @Override
    public boolean isEmpty() {
        return mDatList.isEmpty();
    }

    @Override
    public Model getItem(int position) {
        if (mDatList.size() > position) {
            return mDatList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        T viewHolder = null;
        int itemType = getItemViewType(position);

        if (convertView == null) {
            convertView = newView(mInflater, parent, itemType);
            viewHolder = onCreateViewHolder(convertView, itemType);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (T) convertView.getTag();
        }

        onBindViewHolder(convertView, viewHolder, position, itemType);

        return convertView;
    }

    public abstract T onCreateViewHolder(View convertView, int viewType);

    public abstract View newView(LayoutInflater inflater, ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(View convertView, T viewHolder, int position, int viewType);

}
