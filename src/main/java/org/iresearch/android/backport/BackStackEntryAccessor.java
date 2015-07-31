package org.iresearch.android.backport;

import android.app.Fragment;
import java.lang.reflect.Field;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;

public class BackStackEntryAccessor {

	public static Fragment getFragmentInBackStackRecord(final FragmentManager.BackStackEntry entry) {
		try {
			final Field mHeadField = BackStackEntry.class.getField("mHead");
			final Object mHead = mHeadField.get(entry);
			final Field fragmentField = mHead.getClass().getField("fragment");
			final Object fragment = fragmentField.get(mHead);
			if (fragment instanceof Fragment) return (Fragment) fragment;
		} catch (final NoSuchFieldException e) {
		} catch (final IllegalArgumentException e) {
		} catch (final IllegalAccessException e) {
		}
		return null;
	}
}
