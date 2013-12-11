package com.sissi.ucenter.impl;

import java.util.List;

import com.sissi.ucenter.Field;
import com.sissi.ucenter.RegisterFieldsBuilder;

/**
 * @author kim 2013年12月5日
 */
public class ConfigRegisterFieldsBuilder implements RegisterFieldsBuilder {

	private final List<Field> fields;

	public ConfigRegisterFieldsBuilder(List<Field> fields) {
		super();
		this.fields = fields;
	}

	@Override
	public List<Field> build() {
		return fields;
	}
}
