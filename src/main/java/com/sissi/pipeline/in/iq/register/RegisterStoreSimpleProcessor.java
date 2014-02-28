package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.iq.IQResponseProcessor;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.user.RegisterContext;

/**
 * @author kim 2013年12月3日
 */
public class RegisterStoreSimpleProcessor extends RegisterStoreProcessor {

	public RegisterStoreSimpleProcessor(RegisterContext registerContext, IQResponseProcessor processor) {
		super(registerContext, processor);
	}

	@Override
	protected Fields process(Fields field) {
		return field;
	}
}
