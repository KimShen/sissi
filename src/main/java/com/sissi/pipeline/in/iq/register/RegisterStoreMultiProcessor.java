package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.protocol.iq.register.form.Form;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Field.Fields;
import com.sissi.ucenter.vcard.ListVCardFields;

/**
 * @author kim 2013年12月5日
 */
public class RegisterStoreMultiProcessor extends RegisterStoreProcessor {

	public RegisterStoreMultiProcessor(RegisterContext registerContext, IQTypeProcessor iqTypeProcessor) {
		super(registerContext, iqTypeProcessor);
	}

	@Override
	protected Fields filter(Fields vCardFields) {
		return new ListVCardFields(false, vCardFields.findField(Form.NAME, Form.class).getFields());
	}
}