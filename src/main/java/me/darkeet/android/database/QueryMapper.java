package me.darkeet.android.database;

import android.database.Cursor;

public interface QueryMapper<T> {

	public T mapRow(Cursor cursor, int rowNum);
}
