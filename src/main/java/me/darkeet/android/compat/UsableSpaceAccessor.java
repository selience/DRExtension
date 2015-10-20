package me.darkeet.android.compat;

import java.io.File;
import android.os.Build;
import android.os.StatFs;

public class UsableSpaceAccessor {

	/**
	 * Check how much usable space is available at a given path.
	 * 
	 * @param path The path to check
	 * @return The space available in bytes
	 */
	@SuppressWarnings("deprecation")
	public static long getUsableSpace(final File path) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) 
			return path.getUsableSpace();
		
		final StatFs stats = new StatFs(path.getPath());
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}
	
}
