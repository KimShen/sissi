package com.sissi.ucenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.sissi.protocol.iq.register.form.Form;
import com.sissi.ucenter.RegisterContext.Field;
import com.sissi.ucenter.RegisterInvitation;
import com.sissi.ucenter.RegisterInvitationFieldsBuilder;

/**
 * @author kim 2013年12月5日
 */
public class MultiFormInvitation implements RegisterInvitation {

	private List<Field> fields;

	public MultiFormInvitation(String title, String instructions, RegisterInvitationFieldsBuilder builder) {
		super();
		Form form = new Form(title, instructions);
		this.fields = new ArrayList<Field>();
		this.fields.add(form);
		for (Field each : builder.build()) {
			form.add(each);
		}
	}

	@Override
	public List<Field> build() {
		return this.fields;
	}
}
