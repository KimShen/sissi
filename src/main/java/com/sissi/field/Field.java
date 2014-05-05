package com.sissi.field;

/**
 * @author kim 2013年12月11日
 */
public interface Field<T> {

	public String getName();

	public T getValue();

	/**
	 * Field子节点集合
	 * 
	 * @return
	 */
	public Fields getChildren();

	/**
	 * 是否存在Field子节点
	 * 
	 * @return
	 */
	public boolean hasChild();
}
