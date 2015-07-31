package org.iresearch.android.querybuilder.query;

import org.iresearch.android.querybuilder.SQLLang;

public interface IBuilder<T extends SQLLang> {

	public T build();

	/**
	 * Equivalent to {@link #build()}.{@link #SQLLang.getSQL()}
	 * 
	 * @return
	 */
	public String buildSQL();

}
