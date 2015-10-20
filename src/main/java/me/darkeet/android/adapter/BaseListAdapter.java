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

    private List<Model> dataList;
    private LayoutInflater inflater;

    public BaseListAdapter(Context context) {
        dataList = new ArrayList<Model>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isEmpty() {
        return dataList.isEmpty();
    }

    @Override
    public Model getItem(int position) {
        if (dataList.size() > position) {
            return dataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public final void clearAll() {
        dataList.clear();
    }

    public final int getListSize() {
        return dataList.size();
    }

    public final List<Model> getItems() {
        return dataList;
    }

    public final void addItems(List<Model> items) {
        if (items!=null) {
            dataList.addAll(items);
        }
    }

    public final void setItems(List<Model> items) {
        if (items!=null) {
            dataList.clear();
            dataList.addAll(items);
        }
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        T viewHolder = null;
        int itemType = getItemViewType(position);

        if (convertView == null) {
            convertView = newView(inflater, parent, itemType);
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
