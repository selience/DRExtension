package me.darkeet.android.compat;

import java.io.File;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StatFs;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class UsableSpaceCompat {

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    public static long getUsableSpace(final File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            return path.getUsableSpace();

        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }


    /**
     * Returns the total size in bytes of the partition containing this path.
     * Returns 0 if this path does not exist.
     *
     * @param path
     * @return -1 means path is null, 0 means path is not exist.
     */
    public static long getTotalSpace(File path) {
        if (path == null) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getTotalSpace();
        }
        // implements getTotalSpace() in API lower than GINGERBREAD
        else {
            if (!path.exists()) {
                return 0;
            } else {
                final StatFs stats = new StatFs(path.getPath());
                // Using deprecated method in low API level system,
                // add @SuppressWarnings("description") to suppress the warning
                return (long) stats.getBlockSize() * (long) stats.getBlockCount();
            }
        }
    }

}
