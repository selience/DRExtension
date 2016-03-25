package me.darkeet.android.cache;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Superclass of all list objects to be stored in {@link ModelCache}.
 * <p/>
 * Operates just as standard cached object, and contains an array list of objects.
 * <p/>
 * <b>Must</b> be initialized with the class of the objects stored, as this is used in
 * parcelling/unparcelling.
 * <p/>
 * In order to ensure thread-safe use of list (such as iteration), use the {@link #getList()}
 * method, creating a copy of the list in its current state.
 *
 * @param <CO> Type of cached models to be stored in list
 */
public class CachedList<CO extends Parcelable> implements Parcelable {

    /**
     * List of objects.
     */
    private ArrayList<CO> list;

    /**
     * Class type of object list
     */
    private Class<? extends Parcelable> clazz;

    /**
     * Constructor setting variables from parcel. Same as using a blank constructor and calling
     * readFromParcel.
     *
     * @param source Parcel to be read from.
     */
    public CachedList(Parcel source) {
        readFromParcel(source);
    }

    /**
     * Constructor initializing class of objects stored.
     *
     * @param clazz Required for parcelling and unparcelling of list
     */
    public CachedList(Class<? extends Parcelable> clazz) {
        this.clazz = clazz;
        list = new ArrayList<CO>();
    }

    /**
     * Constructor initializing class of objects stored as well as initial length of list.
     *
     * @param clazz         Required for parcelling and unparcelling of list
     * @param initialLength Initial length of list
     */
    public CachedList(Class<? extends Parcelable> clazz, int initialLength) {
        this.clazz = clazz;
        list = new ArrayList<CO>(initialLength);
    }

    /**
     * Synchronized method to get a copy of the list in its current state. This should be used when
     * iterating over the list in order to avoid thread-unsafe operations.
     *
     * @return Copy of list in its current state
     */
    public synchronized ArrayList<CO> getList() {
        return new ArrayList<CO>(list);
    }

    /**
     * Synchronized method used to append an object to the list.
     *
     * @param cachedObject Object to add to list
     */
    public synchronized void add(CO cachedObject) {
        list.add(cachedObject);
    }

    /**
     * Synchronized method used to append an object to the list.
     *
     * @param cachedList List to add to list
     */
    public synchronized void addAll(List<CO> cachedList) {
        list.addAll(cachedList);
    }

    /**
     * Synchronized method used to append an object to the list.
     *
     * @param cachedList List to add to list
     */
    public synchronized void addAll(int index, List<CO> cachedList) {
        list.addAll(index, cachedList);
    }

    /**
     * Synchronized method used to set an object at a location in the list.
     *
     * @param index        Index of item to set
     * @param cachedObject Object to set in list
     */
    public synchronized void set(int index, CO cachedObject) {
        list.set(index, cachedObject);
    }

    /**
     * Synchronized method used to get an object from the live list.
     *
     * @param index Index of item in list
     * @return Item in list
     */
    public synchronized CO get(int index) {
        return list.get(index);
    }

    /**
     * Synchronized method used to return size of list.
     *
     * @return Size of list
     */
    public synchronized int size() {
        return list.size();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public synchronized boolean equals(Object o) {
        if (!(o instanceof CachedList)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        CachedList that = (CachedList) o;
        return clazz.equals(that.clazz) && list.equals(that.list);
    }

    /**
     * @see Parcelable#describeContents()
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write class name to parcel before object, so can be loaded correctly back in
        dest.writeString(clazz.getCanonicalName());
        dest.writeTypedList(list);
    }


    /**
     * @see android.os.Parcelable.Creator#createFromParcel(android.os.Parcel)
     */
    private void readFromParcel(Parcel source) {
        // Read class from parcel, then load class and use creator to generate new object from data
        try {
            String className = source.readString();
            clazz = (Class<? extends Parcelable>) Class.forName(className);
            list = source.createTypedArrayList((Creator) clazz.getField("CREATOR").get(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see android.os.Parcelable.Creator
     */
    public static final Creator<CachedList> CREATOR = new Creator<CachedList>() {
        @Override
        public CachedList createFromParcel(Parcel in) {
            return new CachedList(in);
        }

        @Override
        public CachedList[] newArray(int size) {
            return new CachedList[size];
        }
    };
}
