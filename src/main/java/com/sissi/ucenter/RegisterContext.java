package com.sissi.ucenter;

import java.util.Collection;

/**
 * @author kim 2013年12月3日
 */
public interface RegisterContext {

	public Boolean register(Collection<Field> fields);
	
	public interface Field {

		public String getName();

		public String getText();
	}
}
