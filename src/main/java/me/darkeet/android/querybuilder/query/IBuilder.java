package me.darkeet.android.querybuilder.query;

import me.darkeet.android.querybuilder.SQLLang;

public interface IBuilder<T extends SQLLang> {

	public T build();

	/**
	 * Equivalent to {@link #build()}.{@link #SQLLang.getSQL()}
	 * 
	 * @return
	 */
	public String buildSQL();

}
