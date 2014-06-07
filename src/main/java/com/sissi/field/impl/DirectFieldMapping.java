package com.sissi.field.impl;

import com.sissi.field.Field;
import com.sissi.field.FieldMapping;

/**
 * 直接返回Field.name
 * 
 * @author kim 2014年6月7日
 */
public class DirectFieldMapping implements FieldMapping {

	public final static FieldMapping MAPPING = new DirectFieldMapping();

	private DirectFieldMapping() {

	}

	@Override
	public String mapping(Field<?> field) {
		return field.getName();
	}
}
