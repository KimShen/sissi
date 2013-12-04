package com.sissi.ucenter.impl;

import java.util.ArrayList;
import java.util.List;

import com.sissi.protocol.iq.register.form.Form;
import com.sissi.protocol.iq.register.form.Form.Type;
import com.sissi.protocol.iq.register.form.Input;
import com.sissi.protocol.iq.register.form.Option;
import com.sissi.protocol.iq.register.form.Required;
import com.sissi.protocol.iq.register.form.Select;
import com.sissi.ucenter.RegisterContext.Field;
import com.sissi.ucenter.RegisterInvitation;

/**
 * @author kim 2013年12月5日
 */
public class MultiFormInvitation implements RegisterInvitation {

	@Override
	public List<Field> build() {
		Form form = new Form(Type.FORM, "A", "B");
		form.add(new Input(Type.HIDDEN, "jabber:iq:register", null, "FORM_TYPE"));
		form.add(new Input(Type.TEXT_SINGLE, "Given Name", "first", Required.FIELD));
		form.add(new Input(Type.TEXT_SINGLE, "Family Name", "last", Required.FIELD));
		form.add(new Input(Type.TEXT_SINGLE, "Email", "email", Required.FIELD));
		form.add(new Select(Type.LIST_SINGLE, "Gender", "x-gender", Required.FIELD).add(new Option("Male", "M")).add(new Option("Female", "F")));
		List<Field> field = new ArrayList<Field>();
		field.add(form);
		return field;
	}
}
