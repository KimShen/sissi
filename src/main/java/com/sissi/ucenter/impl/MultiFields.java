package com.sissi.ucenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.sissi.protocol.iq.register.form.Form;
import com.sissi.ucenter.RegisterContext.Field;
import com.sissi.ucenter.RegisterContext.FieldFinder;
import com.sissi.ucenter.RegisterContext.Fields;

/**
 * @author kim 2013年12月5日
 */
public class MultiFields extends ArrayList<Field> implements Fields {

	private static final long serialVersionUID = 1L;

	public MultiFields(FieldFinder finder) {
		Form form = finder.findField(Form.class);
		this.addAll(form.getField());
	}

	@Override
	public List<Field> getFields() {
		return this;
	}

}
