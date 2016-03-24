package me.darkeet.android.cache;

import android.os.Parcel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Allows caching Model objects using the features provided by {@link AbstractCache}. The key into
 * the cache will be based around the cached object's key, and the object will be able to save and
 * reload itself from the cache.
 * 
 * @author Michael England
 * 
 */
public class ModelCache extends AbstractCache<String, CachedModel> {

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
     * @param prefix
     *            Prefix of all cached object keys to be removed
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
    protected CachedModel readValueFromDisk(File file) throws IOException {
        FileInputStream istream = new FileInputStream(file);

        // Read file into byte array
        byte[] dataWritten = new byte[(int) file.length()];
        BufferedInputStream bistream = new BufferedInputStream(istream);
        bistream.read(dataWritten);
        bistream.close();

        // Create parcel with cached data
        Parcel parcelIn = Parcel.obtain();
        parcelIn.unmarshall(dataWritten, 0, dataWritten.length);
        parcelIn.setDataPosition(0);

        // Read class name from parcel and use the class loader to read parcel
        String className = parcelIn.readString();
        // In case this sometimes hits a null value
        if (className == null) {
            return null;
        }
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
            return parcelIn.readParcelable(clazz.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * @see AbstractCache#writeValueToDisk(java.io.File, java.lang.Object)
     */
    @Override
    protected void writeValueToDisk(File file, CachedModel data) throws IOException {
        // Write object into parcel
        Parcel parcelOut = Parcel.obtain();
        parcelOut.writeString(data.getClass().getCanonicalName());
        parcelOut.writeParcelable(data, 0);

        // Write byte data to file
        FileOutputStream ostream = new FileOutputStream(file);
        BufferedOutputStream bistream = new BufferedOutputStream(ostream);
        bistream.write(parcelOut.marshall());
        bistream.close();
    }

}
