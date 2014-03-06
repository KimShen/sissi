package com.sissi.ucenter.field;

/**
 * @author kim 2014年1月16日
 */
public interface FieldParser<T> {

	public Field<?> read(T elememt);
	
	public String support();
}