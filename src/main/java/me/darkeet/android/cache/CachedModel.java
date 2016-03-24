package me.darkeet.android.cache;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Superclass of all objects to be stored in {@link ModelCache}.
 * <p/>
 * To save an object to the cache use {@link #save(ModelCache, String)},
 * when wanting to find an object from the cache, use
 * {@link #find(ModelCache, String)}.
 */
public abstract class CachedModel implements Parcelable {

    /**
     * Simple parameter-less constructor. <b>Must</b> also have parameter-less constructor in
     * subclasses in order for parceling to work.
     */
    public CachedModel() {
    }

    /**
     * Constructor setting variables from parcel. Same as using a blank constructor and calling
     * readFromParcel.
     *
     * @param source Parcel to be read from.
     */
    protected CachedModel(Parcel source) {
        readFromParcel(source);
    }

    /**
     * Create a new instance of the Parcelable class, instantiating it
     * from the given Parcel whose data had previously been written by
     * {@link Parcelable#writeToParcel Parcelable.writeToParcel()}.
     *
     * @param source The Parcel to read the object's data from.
     * @return Returns a new instance of the Parcelable class.
     */
    public abstract void readFromParcel(Parcel source);

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    public abstract void writeToParcel(Parcel dest, int flags);

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Attempts to save the object in the cache using a given key. Generally only used for saving
     * subclasses over the top of separate superclass cache stores.
     *
     * @param modelCache Cache to save to.
     * @param saveKey    Key to be saved under.
     * @return Whether or not saving to the cache was successful.
     */
    public boolean save(ModelCache modelCache, String saveKey) {
        if ((modelCache != null) && (saveKey != null)) {
            modelCache.put(saveKey, this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to reload any new data from cache.
     *
     * @param saveKey the cache key
     * @return Whether or not newer data was found in the cache.
     */
    public CachedModel find(ModelCache modelCache, String saveKey) {
        if ((modelCache != null) && (saveKey != null)) {
            return modelCache.get(saveKey);
        }
        return null;
    }
}
