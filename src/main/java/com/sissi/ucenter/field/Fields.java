package com.sissi.ucenter.field;


/**
 * @author kim 2014年1月16日
 */
public interface Fields extends Iterable<Field<?>> {

	public boolean isEmbed();

	public Fields add(Field<?> field);

	public <T extends Field<?>> T findField(String name, Class<T> clazz);
}