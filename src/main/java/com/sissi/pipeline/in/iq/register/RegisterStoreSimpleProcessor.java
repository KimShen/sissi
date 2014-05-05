package com.sissi.pipeline.in.iq.register;

import com.sissi.field.Fields;
import com.sissi.pipeline.Input;
import com.sissi.ucenter.register.RegisterContext;

/**
 * 简易表单
 * 
 * @author kim 2013年12月3日
 */
public class RegisterStoreSimpleProcessor extends RegisterStoreProcessor {

	public RegisterStoreSimpleProcessor(RegisterContext registerContext, Input input) {
		super(registerContext, input);
	}

	@Override
	protected Fields process(Fields field) {
		return field;
	}
}
