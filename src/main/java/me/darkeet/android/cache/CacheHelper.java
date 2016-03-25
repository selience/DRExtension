package me.darkeet.android.cache;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Set;

public class CacheHelper {

    public static Parcelable readValueFromDisk(File file) {
        try {
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

            Class<?> clazz = Class.forName(className);
            return parcelIn.readParcelable(clazz.getClassLoader());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void writeValueToDisk(File file, Parcelable cachedModel) {
        try {
            // Write object into parcel
            Parcel parcelOut = Parcel.obtain();
            parcelOut.writeString(cachedModel.getClass().getCanonicalName());
            parcelOut.writeParcelable(cachedModel, 0);

            // Write byte data to file
            FileOutputStream ostream = new FileOutputStream(file);
            BufferedOutputStream bistream = new BufferedOutputStream(ostream);
            bistream.write(parcelOut.marshall());
            bistream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getFileNameFromUrl(String url) {
        // replace all special URI characters with a single + symbol
        return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
    }

    public static void removeAllWithStringPrefix(AbstractCache<String, ?> cache, String urlPrefix) {
        Set<String> keys = cache.keySet();

        for (String key : keys) {
            if (key.startsWith(urlPrefix)) {
                cache.remove(key);
            }
        }

        if (cache.isDiskCacheEnabled()) {
            removeExpiredCache(cache, urlPrefix);
        }
    }

    private static void removeExpiredCache(final AbstractCache<String, ?> cache,
                                           final String urlPrefix) {
        final File cacheDir = new File(cache.getDiskCacheDirectory());

        if (!cacheDir.exists()) {
            return;
        }

        File[] list = cacheDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return dir.equals(cacheDir)
                        && filename.startsWith(cache.getFileNameForKey(urlPrefix));
            }
        });

        if (list == null || list.length == 0) {
            return;
        }

        for (File file : list) {
            file.delete();
        }
    }

}
