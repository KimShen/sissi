package com.sissi.ucenter.field;

/**
 * @author kim 2013年12月11日
 */
public interface Field<T> {

	public String getName();

	public T getValue();

	public Fields getChildren();

	public Boolean hasChild();
	
	public interface Fields extends Iterable<Field<?>> {

		public Boolean isEmbed();

		public Fields add(Field<?> field);

		public <T extends Field<?>> T findField(String name, Class<T> clazz);
	}

	public interface FieldParser<T> {

		public Field<?> read(T elememt);
	}
}
