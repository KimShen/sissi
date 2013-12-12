package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.iq.IQProcessor;
import com.sissi.protocol.iq.register.form.Form;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Field.Fields;
import com.sissi.ucenter.vcard.ListVCardFields;

/**
 * @author kim 2013年12月5日
 */
public class RegisterStoreMultiProcessor extends RegisterStoreProcessor {

	public RegisterStoreMultiProcessor(RegisterContext registerContext, IQProcessor iqProcessor) {
		super(registerContext, iqProcessor);
	}

	@Override
	protected Fields filter(Fields fields) {
		return new ListVCardFields(false, fields.findField(Form.NAME, Form.class).getFields());
	}
}