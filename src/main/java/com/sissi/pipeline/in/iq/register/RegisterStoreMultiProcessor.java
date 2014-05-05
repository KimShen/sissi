package com.sissi.pipeline.in.iq.register;

import com.sissi.field.Fields;
import com.sissi.field.impl.BeanFields;
import com.sissi.pipeline.Input;
import com.sissi.protocol.iq.data.XData;
import com.sissi.ucenter.register.RegisterContext;

/**
 * 复杂表单
 * 
 * @author kim 2013年12月5日
 */
public class RegisterStoreMultiProcessor extends RegisterStoreProcessor {

	public RegisterStoreMultiProcessor(RegisterContext registerContext, Input input) {
		super(registerContext, input);
	}

	@Override
	protected Fields process(Fields fields) {
		return new BeanFields(false, fields.findField(XData.NAME, XData.class).getFields());
	}
}