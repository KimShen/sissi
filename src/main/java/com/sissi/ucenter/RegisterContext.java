package com.sissi.ucenter;

import java.util.Collection;
import java.util.List;

/**
 * @author kim 2013年12月3日
 */
public interface RegisterContext {

	public Boolean register(Fields fields);

	public interface Field {

		public String getName();

		public String getText();
	}

	public interface Fields extends Collection<Field> {

		public List<Field> getFields();
	}

	public interface FieldFinder {

		public <T> T findField(Class<T> field);
	}
}
