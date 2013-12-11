package com.sissi.ucenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.sissi.protocol.iq.register.form.Form;
import com.sissi.ucenter.Field;
import com.sissi.ucenter.Field.FieldFinder;
import com.sissi.ucenter.Field.Fields;

/**
 * @author kim 2013年12月5日
 */
public class MultiFields extends ArrayList<Field> implements Fields {

	private static final long serialVersionUID = 1L;

	public MultiFields(FieldFinder finder) {
		this.addAll(finder.findField(Form.class).getField());
	}

	public Fields addField(String key, Object value) {
		super.add(new StringField(key, value));
		return this;
	}

	@Override
	public List<Field> getFields() {
		return this;
	}

}
