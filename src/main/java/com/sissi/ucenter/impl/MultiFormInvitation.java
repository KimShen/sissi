package com.sissi.ucenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.sissi.protocol.iq.register.form.Form;
import com.sissi.ucenter.Field;
import com.sissi.ucenter.RegisterFieldsBuilder;
import com.sissi.ucenter.RegisterInvitation;

/**
 * @author kim 2013年12月5日
 */
public class MultiFormInvitation implements RegisterInvitation {

	private List<Field> fields;

	public MultiFormInvitation(String title, String instructions, RegisterFieldsBuilder builder) {
		super();
		Form form = new Form(title, instructions);
		for (Field each : builder.build()) {
			form.add(each);
		}
		this.fields = new ArrayList<Field>();
		this.fields.add(form);
	}

	@Override
	public List<Field> build() {
		return this.fields;
	}
}
