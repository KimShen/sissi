package com.sissi.field;

/**
 * @author kim 2014年1月16日
 */
public interface Fields extends Iterable<Field<?>> {

	public boolean isEmbed();

	public boolean isEmpty();

	public Fields add(Field<?> field);

	/**
	 * 指定名称Field集合
	 * 
	 * @param name
	 * @return
	 */
	public Fields findFields(String name);

	/**
	 * 指定名称首个Field
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public <T extends Field<?>> T findField(String name, Class<T> clazz);
}