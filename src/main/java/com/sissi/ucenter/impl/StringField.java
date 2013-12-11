package com.sissi.ucenter.impl;

import com.sissi.ucenter.Field;

/**
 * @author kim 2013年12月10日
 */
public class StringField implements Field {

	private String name;

	private Object value;

	public StringField(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public Boolean isEmpty() {
		return this.getValue() == null;
	}
}
