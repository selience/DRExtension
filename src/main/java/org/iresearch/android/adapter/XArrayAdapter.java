package org.iresearch.android.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.os.Build;
import java.util.Collection;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.annotation.SuppressLint;

public class XArrayAdapter<T> extends ArrayAdapter<T> {

	public XArrayAdapter(Context context, int resource) {
		super(context, resource);
	}

    public XArrayAdapter(Context context, int resource, int textViewResourceId) {
    	super(context, resource, textViewResourceId, new ArrayList<T>());
    }

    public XArrayAdapter(Context context, int resource, T[] objects) {
    	super(context, resource, 0, Arrays.asList(objects));
    }

    public XArrayAdapter(Context context, int resource, int textViewResourceId, T[] objects) {
    	super(context, resource, textViewResourceId, Arrays.asList(objects));
    }

    public XArrayAdapter(Context context, int resource, List<T> objects) {
    	super(context, resource, 0, objects);
    }

    public XArrayAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
    	super(context, resource, textViewResourceId, objects);
    }

    
	/**
     * Add all elements in the collection to the end of the adapter.
     * 
     * @param list to add all elements
     */
	@Override
    @SuppressLint("NewApi")
    public void addAll(Collection<? extends T> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(list);
        } else {
            for (T element : list) {
            	super.add(element);
            }
        }
    }

    /**
     * Add all elements in the array to the end of the adapter.
     * 
     * @param array to add all elements
     */
    @Override
    @SuppressLint("NewApi")
    public void addAll(T... array) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(array);
        } else {
            for (T element : array) {
                super.add(element);
            }
        }
    }
}
