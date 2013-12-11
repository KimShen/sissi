package com.sissi.ucenter;

import java.util.List;

/**
 * @author kim 2013年12月10日
 */
public interface Field {

	public String getName();

	public Object getValue();

	public Boolean isEmpty();

	public interface FieldFinder {

		public <T> T findField(Class<T> field);
	}

	public interface Fields {

		public Fields addField(String key, Object value);

		public List<Field> getFields();
	}
}
