package org.iresearch.android.querybuilder.query;

import org.iresearch.android.querybuilder.SQLLang;

public class SQLDropViewQuery implements SQLLang {

	private final boolean dropIfExists;
	private final String table;

	public SQLDropViewQuery(final boolean dropIfExists, final String table) {
		if (table == null) throw new NullPointerException();
		this.dropIfExists = dropIfExists;
		this.table = table;
	}

	@Override
	public String getSQL() {
		if (dropIfExists) return String.format("DROP VIEW IF EXISTS %s", table);
		return String.format("DROP VIEW %s", table);
	}

}
