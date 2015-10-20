package me.darkeet.android.database;

import android.database.sqlite.SQLiteDatabase;

public interface QueryExecutor {

	public void run(SQLiteDatabase database);
}
