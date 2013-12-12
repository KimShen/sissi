package com.sissi.ucenter.field;


/**
 * @author kim 2013年12月12日
 */
public interface FieldParser<T> {

	public Field<?> read(T elememt);
}
