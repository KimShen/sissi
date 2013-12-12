package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月3日
 */
public class RegisterStoreSimpleProcessor extends RegisterStoreProcessor {

	public RegisterStoreSimpleProcessor(RegisterContext registerContext, IQTypeProcessor iqTypeProcessor) {
		super(registerContext, iqTypeProcessor);
	}

	@Override
	protected Fields filter(Fields vCardFields) {
		return vCardFields;
	}
}
