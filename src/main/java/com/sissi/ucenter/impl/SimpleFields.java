package com.sissi.ucenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.sissi.protocol.iq.register.Password;
import com.sissi.protocol.iq.register.Username;
import com.sissi.ucenter.Field;
import com.sissi.ucenter.Field.FieldFinder;
import com.sissi.ucenter.Field.Fields;

/**
 * @author kim 2013年12月5日
 */
public class SimpleFields extends ArrayList<Field> implements Fields {

	private static final long serialVersionUID = 1L;

	public SimpleFields(FieldFinder finder) {
		super();
		super.add(finder.findField(Username.class));
		super.add(finder.findField(Password.class));
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
