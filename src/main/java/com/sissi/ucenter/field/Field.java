package com.sissi.ucenter.field;

/**
 * @author kim 2013年12月11日
 */
public interface Field<T> {

	public String getName();

	public T getValue();

	public Fields getChildren();

	public Boolean hasChild();
}
