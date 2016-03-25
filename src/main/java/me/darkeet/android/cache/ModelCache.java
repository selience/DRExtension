package me.darkeet.android.cache;

import java.io.File;
import java.io.IOException;
import android.os.Parcelable;

/**
 * Allows caching Model objects using the features provided by {@link AbstractCache}. The key into
 * the cache will be based around the cached object's key, and the object will be able to save and
 * reload itself from the cache.
 */
public class ModelCache extends AbstractCache<String, Parcelable> {

    /**
     * Creates an {@link AbstractCache} with params provided and name 'ModelCache'.
     *
     * @see AbstractCache#AbstractCache(java.lang.String, int, long, int)
     */
    public ModelCache(int initialCapacity, long expirationInMinutes, int maxConcurrentThreads) {
        super("ModelCache", initialCapacity, expirationInMinutes, maxConcurrentThreads);
    }

    /**
     * Removes all cached objects with key prefix.
     *
     * @param prefix Prefix of all cached object keys to be removed
     */
    public synchronized void removeAllWithPrefix(String prefix) {
        CacheHelper.removeAllWithStringPrefix(this, prefix);
    }

    /**
     * @see AbstractCache#getFileNameForKey(java.lang.Object)
     */
    @Override
    public String getFileNameForKey(String url) {
        return CacheHelper.getFileNameFromUrl(url);
    }

    /**
     * @see AbstractCache#readValueFromDisk(java.io.File)
     */
    @Override
    protected Parcelable readValueFromDisk(File file) throws IOException {
        return CacheHelper.readValueFromDisk(file);
    }

    /**
     * @see AbstractCache#writeValueToDisk(java.io.File, java.lang.Object)
     */
    @Override
    protected void writeValueToDisk(File file, Parcelable data) throws IOException {
        CacheHelper.writeValueToDisk(file, data);
    }
}
